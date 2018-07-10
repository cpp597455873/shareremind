package com.cpp.shareremind.controller;

import com.cpp.shareremind.ApiResonse;
import com.cpp.shareremind.dao.ShareHoldDao;
import com.cpp.shareremind.dao.ShareValueDao;
import com.cpp.shareremind.model.ShareHold;
import com.cpp.shareremind.model.ShareHoldNow;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api
@RestController
public class ShareController {

    @Autowired
    ShareHoldDao shareHoldDao;

    @Autowired
    ShareValueDao shareValueDao;


    @GetMapping(path = "/getCurrentState")
    public Object getCurrentState() {
        List<ShareHold> all = shareHoldDao.findAll();
        Double costTotalAll = 0d;
        Double nowTotalAll = 0d;
        Double profitTotalAll = 0d;
        List<ShareHoldNow> latestPrice = shareValueDao.getLatestPrice();
        if (CollectionUtils.isEmpty(latestPrice)) {
            return null;
        }
        for (ShareHoldNow shareHoldNow : latestPrice) {
            Double costTotal = shareHoldNow.getCostTotal();
            if (costTotal != null) {
                costTotalAll += costTotal;
            }

            Double nowTotal = shareHoldNow.getNowTotal();
            if (nowTotal != null) {
                nowTotalAll += nowTotal;
            }

            Double profitTotal = shareHoldNow.getProfitTotal();
            if (profitTotal != null) {
                profitTotalAll += profitTotal;
            }
        }

        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("costTotalAll", costTotalAll);
        resultMap.put("nowTotalAll", nowTotalAll);
        resultMap.put("profitTotalAll", profitTotalAll);
        resultMap.put("myHoldList", latestPrice);
        return resultMap;
    }

    @GetMapping("/getHolds")
    public Object getHolds() {
        return shareHoldDao.findAll();
    }

    @GetMapping("/deleteHoldByCode")
    public Object deleteHoldByCode(String code) {
        shareHoldDao.deleteByCode(code);
        return ApiResonse.success();
    }

    @GetMapping("/deleteAllHolds")
    public Object deleteAllHolds() {
        shareHoldDao.deleteAll();
        return ApiResonse.success();
    }

    @PostMapping("/saveHold")
    public Object addOneHold(@RequestBody ShareHold shareHold) {
        if (shareHold.getId() == null) {
            shareHold.setBuyDate(new Date());
            shareHoldDao.save(shareHold);
        } else {
            ShareHold shareHoldOld = shareHoldDao.findById(shareHold.getId()).get();
            shareHoldOld.setCost(shareHold.getCost());
            shareHoldOld.setShare(shareHold.getShare());
            shareHoldOld.setExpectPrice(shareHold.getExpectPrice());
            shareHoldOld.setExpectTotal(shareHold.getExpectTotal());
            shareHoldOld.setExpectProfit(shareHold.getExpectProfit());
            shareHoldDao.save(shareHoldOld);
        }
        return ApiResonse.success();
    }
}
