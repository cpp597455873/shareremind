package com.cpp.shareremind.model;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "share_hold")
public class ShareHold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String code;
    private Double cost;
    private Double share;
    private Date buyDate;
    private Double expectPrice;
    private Double expectTotal;
    private Double expectProfit;
    private Double nowPrice;
    private Date lastNotifyTime;


    public ShareHold(String name, String code, Double cost, Double share, Date buyDate, Double expectPrice, Double expectTotal) {
        this.name = name;
        this.code = code;
        this.cost = cost;
        this.share = share;
        this.buyDate = buyDate;
        this.expectPrice = expectPrice;
        this.expectTotal = expectTotal;
    }

    public ShareHold(String name, String code, Double cost, Double share, Date buyDate) {
        this.name = name;
        this.code = code;
        this.cost = cost;
        this.share = share;
        this.buyDate = buyDate;
    }

    public ShareHold() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getShare() {
        return share;
    }

    public void setShare(Double share) {
        this.share = share;
    }

    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
    }

    public Double getExpectTotal() {
        return expectTotal;
    }

    public void setExpectTotal(Double expectTotal) {
        this.expectTotal = expectTotal;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(Double expectProfit) {
        this.expectProfit = expectProfit;
    }
}
