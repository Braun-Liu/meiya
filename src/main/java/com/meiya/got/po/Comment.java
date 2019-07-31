package com.meiya.got.po;

import java.util.Date;

public class Comment {
    private Long id;
    private Long entity_id;
    private Long user_id;
    private Integer score;
    private String comment;
    private Long sid;
    private Date create_time;
    private String username;
    private String photo_url;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", entity_id=" + entity_id +
                ", user_id=" + user_id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", sid=" + sid +
                ", create_time=" + create_time +
                ", username='" + username + '\'' +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(Long entity_id) {
        this.entity_id = entity_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
