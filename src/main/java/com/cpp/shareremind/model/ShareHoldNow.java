package com.cpp.shareremind.model;


import javax.persistence.*;
import java.util.Date;

public class ShareHoldNow extends ShareHold {
    private Double value;
    private Date shareTime;
    private Double costTotal;
    private Double nowTotal;
    private Double profitTotal;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Double costTotal) {
        this.costTotal = costTotal;
    }

    public Double getNowTotal() {
        return nowTotal;
    }

    public void setNowTotal(Double nowTotal) {
        this.nowTotal = nowTotal;
    }

    public Double getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(Double profitTotal) {
        this.profitTotal = profitTotal;
    }
}
