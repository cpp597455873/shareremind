package com.cpp.shareremind.controller;

import com.cpp.shareremind.model.ShareHold;
import com.cpp.shareremind.model.ShareHoldNow;
import com.cpp.shareremind.model.ShareValue;
import com.cpp.shareremind.dao.ShareHoldDao;
import com.cpp.shareremind.dao.ShareValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ShareController {

    @Autowired
    ShareHoldDao shareHoldDao;

    @Autowired
    ShareValueDao shareValueDao;


    @GetMapping("/getCurrentState")
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
        return "ok";
    }

    @GetMapping("/deleteAllHolds")
    public Object deleteAllHolds() {
        shareHoldDao.deleteAll();
        return "ok";
    }

    @PostMapping("/saveHold")
    public Object addOneHold(@RequestBody ShareHold shareHold) {
        shareHoldDao.save(shareHold);
        return "ok";
    }


}
