package com.meiya.got.dao;

import com.meiya.got.po.Foods;
import com.meiya.got.po.Order;
import com.meiya.got.po.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StatisticalDao {
    String FOODS_NAME = "my_foods";
    String STORE_NAME = "my_store";
    String ORDER_NAME = "my_order";

//    select * from 表名 where to_days(时间字段名) = to_days(now());

    @Select({"select sales from ",STORE_NAME," where  id=#{store_id}"})
    Integer selectStoreAllSales(@Param("store_id") Long store_id);

    @Select({"select category_id from ", STORE_NAME ," where id=#{id}"})
    Long findStoreCategoryId(@Param("id")Long sid);

    //今日销售额
    @Select({"select sum(payment) from ",ORDER_NAME," where  to_days(payment_time) = to_days(now()) and  store_id=#{store_id}"})
    BigDecimal selectStoreTodayPayment(@Param("store_id") Long sid);

    //本月销售额
    @Select({"select sum(payment) from ",ORDER_NAME," WHERE DATE_FORMAT(payment_time,'%Y%m') = DATE_FORMAT( CURDATE(),'%Y%m' ) and store_id=#{store_id}"})
    BigDecimal selectStoreMonthPayment(@Param("store_id")Long sid);

    //本月订单数
    @Select({"select count(*) from ",ORDER_NAME," WHERE DATE_FORMAT(payment_time,'%Y%m') = DATE_FORMAT( CURDATE(),'%Y%m' ) and store_id=#{store_id}"})
    Integer selectStoreMonthSales(@Param("store_id")Long sid);


    //昨日销售额 select fullName,addedTime from t_user where to_days(NOW()) - TO_DAYS(addedTime) <= 1;
    //SELECT * FROM 表名 WHERE DATEDIFF(字段,NOW())=-1;
    //昨天 1  前天 2  大前天 3
  /*  @Select({"select sum(payment) from ",ORDER_NAME," where  store_id=#{store_id} and  to_days(NOW()) - TO_DAYS(payment_time) <= #{pastdays}"})
    BigDecimal selectStorePastDayPayment(@Param("store_id")Long sid, @Param("pastdays")Integer days);*/

    @Select({"select sum(payment) from ",ORDER_NAME," where store_id=#{store_id} and DATEDIFF(payment_time,NOW()) = -#{pastdays}"})
    BigDecimal selectStorePastDayPayment(@Param("store_id")Long sid, @Param("pastdays")Integer days);

    @Select({"select count(*) from ",ORDER_NAME," where store_id=#{store_id} and DATEDIFF(payment_time,NOW()) = -#{pastdays}"})
    Integer selectStorePastDaySales(@Param("store_id")Long sid, @Param("pastdays")Integer days);




    //本日订单数
    @Select({"select count(*) from ",ORDER_NAME," where  to_days(payment_time) = to_days(now()) and  store_id=#{store_id}"})
    Integer selectStoreTodaySales(@Param("store_id") Long sid);

    //最近七天总订单数
    @Select({"select count(*) from ",ORDER_NAME," where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) and store_id=#{store_id}"})
    Integer selectLatestSevenDaysSales(@Param("store_id") Long sid);

    //特定食品总销量
    @Select({"select sales from ",FOODS_NAME," where id=#{id}"})
    Integer selectTheFooodSales(@Param("id")Long foods_id);


    @Select({"select * from ",FOODS_NAME," where sid=#{store_id} order by sales desc"})
    List<Foods> selectAllFoodsBySales(@Param("store_id")Long sid);


    //订单状态统计
    @Select({"select count(*) from ",ORDER_NAME," where store_id=#{sid} and status=#{status}"})
    Integer selectUntreatedOrder(@Param("sid")Long sid,@Param("status") Integer status);

    //两个时间点内的订单数
    @Select({"select YEAR(payment_time) as year,MONTH(payment_time) as month,day(payment_time) as day,count(payment) as count,SUM(payment) as sum from my_order  WHERE date_format(payment_time,'%Y-%m')   = #{year_month}   GROUP BY  YEAR(payment_time),MONTH(payment_time),day(payment_time)"})
    ArrayList<Order> selectbydate(String year_month);

    //同类商店今日销售额排名
    @Select({"SELECT count(*) from " +
            "( " +
            "select sum(payment) from my_order where CAST(my_order.payment_time as DATE)=CAST(NOW() as date)  " +
            "GROUP BY store_id  " +
            "HAVING store_id in " +
            "(SELECT id from my_store WHERE category_id=(select category_id from my_store where id=#{store_id})) " +
            "and  " +
            "sum(payment) >= (select sum(payment) from my_order where CAST(my_order.payment_time as DATE)=CAST(NOW() as date) and my_order.store_id=#{store_id}) " +
            ") as a"})
    Integer selectMyRank(@Param("store_id")Long id);

    @Select({"select count(*) from ",STORE_NAME," where status=1 and category_id=#{category_id}"})
    Integer selectAllStoreNum(@Param("category_id")Long category_id);
}
