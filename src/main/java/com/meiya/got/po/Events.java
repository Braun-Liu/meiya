package com.meiya.got.po;

import java.util.Date;

public class Events {
    private  Long  id;
    private  String name;
    private String description;
    private Date start_time;
    private Date create_time;
    private Integer counts;
    private String photo_url;
    private Integer status;
    private Date end_time;
    private Integer myStatus;

    public Integer getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(Integer myStatus) {
        this.myStatus = myStatus;
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start_time=" + start_time +
                ", create_time=" + create_time +
                ", counts=" + counts +
                ", photo_url='" + photo_url + '\'' +
                ", status=" + status +
                ", end_time=" + end_time +
                ", myStatus=" + myStatus +
                '}';
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Events() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
