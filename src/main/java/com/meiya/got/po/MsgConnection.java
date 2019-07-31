package com.meiya.got.po;

import java.io.Serializable;

public class MsgConnection implements Serializable {



    private  Long order_id;
    private  Integer store_mid;
    private Integer user_mid;
    private Long store_id;
    private Long user_id;

    /*public MsgConnection(Long order_id, Integer store_mid, Integer user_mid) {
        this.order_id = order_id;
        this.store_mid = store_mid;
        this.user_mid = user_mid;
    }*/

    public MsgConnection() {
    }

    public MsgConnection(Long order_id, Integer store_mid, Integer user_mid, Long store_id, Long user_id) {
        this.order_id = order_id;
        this.store_mid = store_mid;
        this.user_mid = user_mid;
        this.store_id = store_id;
        this.user_id = user_id;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Integer getStore_mid() {
        return store_mid;
    }

    public void setStore_mid(Integer store_mid) {
        this.store_mid = store_mid;
    }

    public Integer getUser_mid() {
        return user_mid;
    }

    public void setUser_mid(Integer user_mid) {
        this.user_mid = user_mid;
    }
}
