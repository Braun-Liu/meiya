package com.meiya.got.dao;

import com.meiya.got.po.Events;
import com.meiya.got.po.Seckill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Mapper
public interface SeckillDao {
    String SECKILL_NAME="my_seckill";
    String SECKILL_FIELDS=" goods_id,seckill_price,sid,start_date,end_date,events_id,status,counts";

    String EVENTS_NAME="my_events";



    @Insert({"insert into ",SECKILL_NAME," (",SECKILL_FIELDS,") values (#{goods_id},#{seckill_price},#{sid},#{start_date},#{end_date},#{events_id},#{status},#{counts})"})
    Integer addSeckillGoods(Seckill seckill);

    @Select({"select * from ",EVENTS_NAME," where status=1 order by start_time"})
    List<Events> selectAllEvents();

    @Select({"select * from ",EVENTS_NAME,"where id=#{id}"})
    Events selectEventsById(@RequestParam("id") Long id);

    //查找是否参加了活动
    @Select({"select count(*) from ",SECKILL_NAME," where events_id=#{events_id} and sid=#{sid}"})
    Integer judgeMyStatus(@RequestParam("events_id") Long events_id,@RequestParam("sid") Long sid);


    @Select({"select * from ",SECKILL_NAME," where events_id=#{events_id} and sid=#{sid}"})
    Seckill findTheEvents(@RequestParam("events_id") Long events_id,@RequestParam("sid") Long sid);


    @Update({"update ",SECKILL_NAME," set goods_id=#{goods_id},seckill_price=#{seckill_price},counts=#{counts} where id=#{id} "})
    Integer updateSeckill(Seckill seckill);


    @Update({"update ",SECKILL_NAME," set status=#{status} where id=#{id}"})
    void SetGoodsStatus(@RequestParam("id") Long id,@RequestParam("status")Integer status);




}
