package com.meiya.got.dao;

import com.meiya.got.po.Category;
import com.meiya.got.po.License;
import com.meiya.got.po.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreDAO {
    String LICENSE_NAME="my_license";
    String LICENSE_FIELD="store_id,license,realname,id_card,id_number,update_time,status";

    String TABLE_NAME = "my_store";
    String CATEGORY_NAME = "my_category";
    String INSET_FIELDS = " storename, password, salt,phone,category_id, address,description,status,photo_url,create_time,update_time,update_ip,operational";
    String SELECT_FIELDS = " id,name, password, salt,phone, address,description,status,photo_url,create_time,update_time,update_ip";

    //添加商家
    @Insert({"insert into ", TABLE_NAME, "(",INSET_FIELDS," ) values (#{storename},#{password},#{salt},#{phone},#{category_id},#{address},#{description},#{status},#{photo_url},#{create_time},#{update_time},#{update_ip},#{operational})"})
    void addStore(Store store);


    //添加商家认证信息
    @Insert({"insert into",LICENSE_NAME,"(",LICENSE_FIELD,") values (#{store_id},#{license},#{realname},#{id_card},#{id_number},#{update_time},#{status})"})
    Integer addlicense(License license);

    //更新商家认证信息
    @Update({"update ",LICENSE_NAME," set status=#{status},license=#{license},realname=#{realname},id_card=#{id_card},id_number=#{id_number},update_time=#{update_time}  where store_id=#{store_id}"})
    void updatelicense(License license);


    //查找商家认证信息
    @Select({"select * from ",LICENSE_NAME," where store_id=#{store_id}"})
    License findLicenseBySid(@Param("store_id")Long store_id);

    @Select({"select id from ",TABLE_NAME," where phone=#{phone}"})
    Long selectIdByPhone(String phone);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    Store selectById(@Param("id") Long store_id);

    @Select({"select * from ", TABLE_NAME, " where storename=#{storename}"})
    Store selectByName(String name);

    @Select({"select * from ", TABLE_NAME, " where phone=#{phone}"})
    Store findByPhone(String phone);

    @Select({"select count(1) from ", TABLE_NAME, " where phone=#{phone}"})
    int selectByPhone(String phone);


    @Select({"select id from",TABLE_NAME,"where phone=#{phone}"})
    Long findIdByPhone(String phone);

    @Select({"select * from ",CATEGORY_NAME})
    List<Category> findAllStoreCate();


    @Select({"select * from ", TABLE_NAME, " where phone=#{phone} and password=#{password}"})
    Store selectLogin(@Param("phone") String phone, @Param("password")String md5password);

    /**
     * 更新商户信息
     * @param store
     * @return
     */
    @Update({"update ", TABLE_NAME, " set status=#{status}  where id=#{id}"})
    int updateStatus(Store store);

    @Update({"update ", TABLE_NAME, " set storename=#{storename}  where id=#{id}"})
    int updateName(Store store);

    @Update({"update ", TABLE_NAME, " set password=#{password}  where id=#{id}"})
    int updatePassword(Store store);

    @Update({"update ", TABLE_NAME, " set phone=#{phone}  where id=#{id}"})
    int updatePhone(Store store);

    @Update({"update ", TABLE_NAME, " set address=#{address}  where id=#{id}"})
    int updateAddress(Store store);

    @Update({"update ", TABLE_NAME, " set description=#{description}  where id=#{id}"})
    int updateDescription(Store store);

    @Update({"update ", TABLE_NAME, " set photo_url=#{photo_url}  where id=#{id}"})
    int updatePhoto_url(Store store);

    @Update({"update ", TABLE_NAME, " set update_time=#{update_time}  where id=#{id}"})
    int updateUpdate_time(Store store);

    @Update({"update ", TABLE_NAME, " set update_ip=#{update_ip}  where id=#{id}"})
    int updateUpdate_ip(Store store);

    @Update({"update ", TABLE_NAME, " set operational=#{operational}  where id=#{id}"})
    int updateOperational(Store store);

    //关店
    @Update({"update ",TABLE_NAME," set operational=#{operational} where id=#{id}"})
    int CloseById(@Param("id") Long id,@Param("operational") Integer operational);

    //自动接单开启
    @Select({"select autorecieve from ",TABLE_NAME," where id=#{id}"})
    Integer findMyAutorecieveStatus(@Param("id")Long sid);

    @Update({"update ",TABLE_NAME,"set autorecieve=#{autorecieve} where id=#{id}"})
    Integer autoRecieve(@Param("autorecieve")Integer autorecieve,@Param("id")Long sid);

    //注销
    @Delete({"delete from ",TABLE_NAME,"where phone=#{phone}"})
    int deleteStore(@Param("phone") String phone);

    @Insert({"insert into",TABLE_NAME,"(",INSET_FIELDS," )"," values (#{storename},#{password},#{salt},#{phone},#{address},#{description},#{status},#{photo_url})"})
    int Register(@Param("storename")String storename,@Param("phone") String phone, @Param("password") String password,@Param("salt")String salt,@Param("address") String address,@Param("description")String describe,@Param("status")int status,@Param("photo_url")String photo_url);


    //商家销量增加
    @Update({"update ",TABLE_NAME," set sales=sales+#{count} where id=#{id}"})
    Integer addStoreSalesById(@Param("count")Integer count,@Param("id")Long sid);



}
