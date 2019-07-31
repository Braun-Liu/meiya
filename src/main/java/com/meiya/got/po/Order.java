package com.meiya.got.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    private Long id;
    private Long store_id;
    private Long user_id;
    private Long shipping_id;
    private BigDecimal payment;
    private Integer payment_type;
    private Integer status;
    private String qr_code;
    private Date payment_time;
    private Date send_time;
    private Date end_time;
    private Date close_time;
    private Date create_time;
    private Date update_time;
    private String note;
    private String address;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", store_id=" + store_id +
                ", user_id=" + user_id +
                ", shipping_id=" + shipping_id +
                ", payment=" + payment +
                ", payment_type=" + payment_type +
                ", status=" + status +
                ", qr_code='" + qr_code + '\'' +
                ", payment_time=" + payment_time +
                ", send_time=" + send_time +
                ", end_time=" + end_time +
                ", close_time=" + close_time +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", note='" + note + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(Long shipping_id) {
        this.shipping_id = shipping_id;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public Date getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Date payment_time) {
        this.payment_time = payment_time;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getClose_time() {
        return close_time;
    }

    public void setClose_time(Date close_time) {
        this.close_time = close_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
