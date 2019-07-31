package com.meiya.got.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private Long id;
    private Integer roel;
    private Long character_id;
    private BigDecimal account;
    private Integer integral;
    private Date update_time;
    private Integer status;
    private Integer level;
    private Date create_time;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", roel=" + roel +
                ", character_id=" + character_id +
                ", account=" + account +
                ", integral=" + integral +
                ", update_time=" + update_time +
                ", status=" + status +
                ", level=" + level +
                ", create_time=" + create_time +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoel() {
        return roel;
    }

    public void setRoel(Integer roel) {
        this.roel = roel;
    }

    public Long getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(Long character_id) {
        this.character_id = character_id;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
