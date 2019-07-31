package com.meiya.got.dao;

import com.meiya.got.po.FoodCate;
import com.meiya.got.po.Foods;
import com.meiya.got.po.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodDao {
    String TABLE_NAME = "my_foods";
    String FOODCATE_NAME="my_foodcate";

    String INSET_FIELDS = " sid,name, photo_url,photo_sub, cate_id,description,stock,price,status,create_time,update_time";
    String SELECT_FIELDS = " id, sid,name, photo,photo_sub, cate_id,description,stock,price,status,create_time,update_time";


    //  TODO 食物销量增加



    //添加食物
    @Insert({"insert into",TABLE_NAME,"(",INSET_FIELDS, " ) values (#{sid},#{name},#{photo_url},#{photo_sub},#{cate_id},#{description},#{stock},#{price},#{status},#{create_time},#{update_time})"})
    int addfoods(Foods foods);



    @Update({"update ", TABLE_NAME, " set photo_url=#{photo_url}  where id=#{id}"})
    int updatePhoto(Foods foods);

    @Update({"update ", TABLE_NAME, " set photo_sub=#{photo_sub}  where id=#{id}"})
    int updatePhoto_sub(Foods foods);
    //修改类别
    @Update({"update ", TABLE_NAME, " set cate_id=#{cate_id}  where id=#{id}"})
    int updateCate_id_sub(Foods foods);
    //修改库存
    @Update({"update ", TABLE_NAME, " set stock=#{stock}  where id=#{id}"})
    int updateStock(Foods foods);
    //修改价格
    @Update({"update ", TABLE_NAME, " set price=#{price}  where id=#{id}"})
    int updatePrice(Foods foods);
    //修改更新时间
    @Update({"update ", TABLE_NAME, " set update_time=#{update_time}  where id=#{id}"})
    int updateTime(Foods foods);
    //修改状态
    @Update({"update ", TABLE_NAME, " set status=#{status}  where id=#{id}"})
    int updateStatus(Foods foods);

    @Update({"update ", TABLE_NAME, " set name=#{name}  where id=#{id}"})
    int updateName(Foods foods);

    @Update({"update ", TABLE_NAME, " set description=#{description}  where id=#{id}"})
    int updateDescription(Foods foods);

    @Update({"update ", TABLE_NAME, " set photo_url=#{photo_url} where id=#{id}"})
    int updateImgById(Long id,String photo_url);

    @Select({"select * from ",FOODCATE_NAME})
    List<FoodCate> findAllFoodCate();
    /*
    查询
     */
    //查询某个分类的店铺

    @Select({"select store_id from ",TABLE_NAME,"where cate_id=#{cate_id}"})
    List<Integer> selectStoreByCateId(@Param("cate_id") int cate_id);


    //查询全部食物
    @Select({"select * from",TABLE_NAME,"order by update_time desc"})
    List<Foods> seletAllFoods();

    //查询某个店铺的食物
    @Select({"select * from",TABLE_NAME,"where sid=#{sid} order by cate_id "})
    List<Foods> selectAll(@Param("sid") Long sid);
    //跟据菜品分类查询
    @Select({"select * from",TABLE_NAME," where sid=#{sid} and cate_id=#{cate_id} "})
    List<Foods> selectFoodsByCate(@Param("sid") Long sid,@Param("cate_id")Integer cate_id);


    @Select({"select * from " ,TABLE_NAME,"where sid=#{sid} order by cate_id "})
    Foods selectBySId(@Param("sid") Long sid);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    Foods selectById(@Param("id")Long id);

    @Select({"select store_id from ", TABLE_NAME, " where id=#{id}"})
    Long selectStoreById(@Param("id")Long id);


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where like % #{name} % order by price "})
    Foods selectByName(@Param("name")String name);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where cate_id=#{cate_id}"})
    Foods selectByCate(@Param("cate_id")long cate_id);







    //删除食物
    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    int deleteById(@Param("id")Long id);


    @Delete({"delete from",TABLE_NAME,"where sid=#{sid}"})
    int deleteByStoreId(@Param("sid") Long sid);



    //增加食品销量
    @Update({"update ",TABLE_NAME," set sales=sales+#{count} where id=#{id}"})
    Integer addFoodSales(@Param("count")Integer count,@Param("id")Long id);


}
