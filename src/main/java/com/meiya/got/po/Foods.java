package com.meiya.got.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Foods implements Serializable {
    private Long id;
    private Long sid;
    private String name;
    private String photo_url;
    private String photo_sub;
    private Integer cate_id;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private Integer status;
    private Date create_time;
    private Date update_time;
    private Integer sales;

    public Foods() {
    }

    @Override
    public String toString() {
        return "Foods{" +
                "id=" + id +
                ", sid=" + sid +
                ", name='" + name + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", photo_sub='" + photo_sub + '\'' +
                ", cate_id=" + cate_id +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", status=" + status +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", sales=" + sales +
                '}';
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto_sub() {
        return photo_sub;
    }

    public void setPhoto_sub(String photo_sub) {
        this.photo_sub = photo_sub;
    }

    public Integer getCate_id() {
        return cate_id;
    }

    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
