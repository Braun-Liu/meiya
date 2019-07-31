package com.meiya.got.po;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class storeOrder {
    private Long order_id;
    private String username;
    private String address;
    private List<FoodsOrder> foodsOrders;
    private BigDecimal total_price;
    private String note;
    private Date create_time;
    private Integer status;
    private Integer payment_type;


    public storeOrder(Long order_id, String username, String address, List<FoodsOrder> foodsOrders, BigDecimal total_price, String note, Date create_time, Integer status, Integer payment_type) {
        this.order_id = order_id;
        this.username = username;
        this.address = address;
        this.foodsOrders = foodsOrders;
        this.total_price = total_price;
        this.note = note;
        this.create_time = create_time;
        this.status = status;
        this.payment_type = payment_type;
    }

    public storeOrder() {
    }

    @Override
    public String toString() {
        return "storeOrder{" +
                "order_id=" + order_id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", foodsOrders=" + foodsOrders +
                ", total_price=" + total_price +
                ", note='" + note + '\'' +
                ", create_time=" + create_time +
                ", status=" + status +
                ", payment_type=" + payment_type +
                '}';
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FoodsOrder> getFoodsOrders() {
        return foodsOrders;
    }

    public void setFoodsOrders(List<FoodsOrder> foodsOrders) {
        this.foodsOrders = foodsOrders;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }
}
