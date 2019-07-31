package com.meiya.got.po;

import java.util.Date;

/**
 * Created by zlovae on 2019/7/7
 * */
public class Admin {
    private Integer id;
    private String name;
    private String phone;
    private String password;
    private String salt;
    private Integer role;
    private Date time;

    public Admin(Integer id, String name, String phone, String password, String salt, Integer role, Date time) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
