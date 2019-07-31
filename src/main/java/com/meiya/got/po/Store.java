package com.meiya.got.po;

import java.io.Serializable;
import java.util.Date;

public class Store implements Serializable {
    private Long id;
    private String storename;
    private String photo_url;
    private String phone;
    private String address;
    private String password;
    private String salt;
    private String description;
    private String update_ip;
    private Date create_time;
    private Date update_time;
    private Integer status;
    private Integer category_id;
    private Integer operational;
    private Integer sales;
    private Integer autorecieve;


    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storename='" + storename + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", description='" + description + '\'' +
                ", update_ip='" + update_ip + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", status=" + status +
                ", category_id=" + category_id +
                ", operational=" + operational +
                ", sales=" + sales +
                ", autorecieve=" + autorecieve +
                '}';
    }

    public Integer getAutorecieve() {
        return autorecieve;
    }

    public void setAutorecieve(Integer autorecieve) {
        this.autorecieve = autorecieve;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public int getOperational() {
        return operational;
    }


    public void setOperational(int operational) {
        this.operational = operational;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setOperational(Integer operational) {
        this.operational = operational;
    }

    public Store(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdate_ip() {
        return update_ip;
    }

    public void setUpdate_ip(String update_ip) {
        this.update_ip = update_ip;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
