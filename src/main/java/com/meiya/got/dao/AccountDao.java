package com.meiya.got.dao;

import com.meiya.got.po.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountDao {
    String TABLE_NAME="my_account";
    String SELECT_FIELD="role,character,account,integral,update_time,status,level,create_time";

    @Insert({"insert into ",TABLE_NAME,"(",SELECT_FIELD,")  values(#{role},#{character},#{account},#{integral},#{update_time},#{status}，#{level}，#{create_time})" })
    Integer addAccount(Account account);

 /*   @Update({"update ",TABLE_NAME," set account=#{account} "})*/




}
