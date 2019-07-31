package com.meiya.got.po;

import lombok.Data;

import java.math.BigDecimal;

public class Esales {
    private BigDecimal revenue;
    private String date;
    private Integer sales;


    public Esales(BigDecimal revenue, String date) {
        this.revenue = revenue;
        this.date = date;
    }

    public Esales(String date, Integer sales) {
        this.date = date;
        this.sales = sales;
    }



    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Esales{" +
                "revenue=" + revenue +
                ", date='" + date + '\'' +
                ", sales=" + sales +
                '}';
    }
}
