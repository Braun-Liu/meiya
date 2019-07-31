package com.meiya.got.dao;

import com.meiya.got.po.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

//@Repository
@Mapper
public interface AdminDAO {
    String TABLE_NAME = "my_admin";
    String INSET_FIELDS = "name, phone, password,salt,role";
    String SELECT_FIELDS = "id, name, phone, password,salt,role, create_time";

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where phone=#{phone}"})
    int checkUsername(String phone);


    Admin selectLogin(@Param("phone") String phone, @Param("password")String password);

    @Update({"update my_store set photo_url=#{photo_url} where id=#{id}"})
    int updateImgById(Long id,String photo_url);
}
