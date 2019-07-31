package com.meiya.got.po;

import java.util.Date;

public class License {
    private Long id;
    private Long store_id;
    private Long id_number;
    private String id_card;
    private String realname;
    private String license;
    private Date update_time;
    private Integer status;


    public License() {
    }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", store_id=" + store_id +
                ", id_number=" + id_number +
                ", id_card='" + id_card + '\'' +
                ", realname='" + realname + '\'' +
                ", license='" + license + '\'' +
                ", update_time=" + update_time +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getId_number() {
        return id_number;
    }

    public void setId_number(Long id_number) {
        this.id_number = id_number;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
