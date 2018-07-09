package com.cpp.shareremind;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.cpp.shareremind.dao.ShareHoldDao;
import com.cpp.shareremind.dao.ShareValueDao;
import com.cpp.shareremind.model.ShareHold;
import com.cpp.shareremind.model.ShareValue;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GetShareTimmer {

    @Autowired
    private OkHttpClient okHttpClient;


    @Autowired
    ShareValueDao shareValueDao;

    @Autowired
    ShareHoldDao shareHoldDao;

    private JPushClient jpushClient;
    private SimpleDateFormat dateFormat;
    private Gson gson = new Gson();


    @PostConstruct
    public void init() {
        jpushClient = new JPushClient("82df7160317edfa2271edd9b", "640c29b3fbadf5b32a862c67", null, ClientConfig.getInstance());
        dateFormat = new SimpleDateFormat("hhmm");
    }


    //    @Scheduled(fixedRate = 10000)
    public void insertData() {
        List<ShareHold> shares = shareHoldDao.findAll();
        customMessage(ACTION_PRICE, gson.toJson(shares));
    }

    public static final String ACTION_PRICE = "action_price";


//    @Scheduled(cron = "*/10 * 9,10,11,13,14 * * MON-FRI")
//    public void pushPrice() {
//    }


    @Scheduled(cron = "*/10 * 9,10,11,13,14 * * MON-FRI")
    public void getNowPrice() {
        Integer now = Integer.valueOf(dateFormat.format(new Date()));
        if (now > 1130 && now < 1300) {
            return;
        }
        double total = 0d;
        List<ShareHold> shares = shareHoldDao.findAll();
        for (ShareHold share : shares) {
            total += getOneShare(share);
        }
        customMessage(ACTION_PRICE, gson.toJson(shares));
    }

    private void notifyMe(String title, String content, Object data) {
        try {
            JPushClient jpushClient = new JPushClient("82df7160317edfa2271edd9b", "640c29b3fbadf5b32a862c67", null, ClientConfig.getInstance());
            PushPayload pushPayload = buildPushObject_android_tag_alertWithTitle(title, content, data);
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            System.out.println(pushResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getOneShare(ShareHold share) {
        try {
            String code = share.getCode();
            String url = "https://hq.finance.ifeng.com/q.php?l=" + code;
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String data = response.body().string();
            data = data.replace("var json_q=", "");
            data = data.replace(";", "");
            Map map = new Gson().fromJson(data, Map.class);
            List<Double> resultList = (List<Double>) map.get(code);
            Double open = resultList.get(1);
            Double now = resultList.get(0);
            double rate = (now / open - 1) * 100;
            double earn = (now - share.getCost()) * share.getShare();
            System.out.println(String.format("code %s rate:%.2f%% now:%.2f open:%.2f earn:%.2f", code, rate, now, open, earn));
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
            Double max = shareValueDao.getTodayMax(code, today);
            if (max != null && now > max) {
                notifyMe("今日最高价", share);
            } else if ((share.getExpectPrice() != null && share.getExpectPrice() <= now)) {
                notifyMe("到达目标价", share);
            } else if ((share.getExpectProfit() != null && share.getExpectProfit() <= earn)) {
                notifyMe("到达目标利润", share);
            } else if ((share.getExpectTotal() != null && share.getExpectTotal() <= now * share.getShare())) {
                notifyMe("到达目标总价", share);
            }
            shareValueDao.insert(new ShareValue(code, now, new Date()));
            shareHoldDao.updateShareHoldNowPrice(code, now);
            return earn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0d;
    }

    private void notifyMe(String title, ShareHold share) {
        String content = new Gson().toJson(share);
        notifyMe(title, share.getName() + " Expect:" + share.getExpectPrice() + " Now:" + share.getNowPrice() + " Earn:" +
                (share.getNowPrice() - share.getCost()) * share.getShare(), share);
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle(String title, String content, Object data) {
        HashMap<String, String> extras = new HashMap<>();
        if (data != null) {
            String s = new Gson().toJson(data);
            extras.put("data", s);
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(title, content, extras))
                .build();
    }


    public void customMessage(String action, String data) {
        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder()
                        .setMsgContent("custom_message")
                        .addExtra("action", action)
                        .addExtra("data", data)
                        .build())
                .build();
        try {
            jpushClient.sendPush(pushPayload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
}
