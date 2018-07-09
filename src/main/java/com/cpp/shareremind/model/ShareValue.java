package com.cpp.shareremind.model;

import java.util.Date;

public class ShareValue {
    private String shareCode;
    private Double value;
    private Date shareTime;


    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

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

    public ShareValue(String shareCode, Double value) {
        this.shareCode = shareCode;
        this.value = value;
    }

    public ShareValue(String shareCode, Double value, Date shareTime) {
        this.shareCode = shareCode;
        this.value = value;
        this.shareTime = shareTime;
    }

    public ShareValue() {
    }
}
