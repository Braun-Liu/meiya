package com.meiya.got.dao;

import com.meiya.got.po.Comment;
import com.meiya.got.po.Order;
import com.meiya.got.po.OrderItem;
import com.meiya.got.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDao {
    String ORDER_TABLE_NAME = "my_order";
    String ITEM_TABLE_NAME = "my_order_item";
    String USER_TABLE_NAME="my_user";
    String COMMENT_NAME="my_comment";

    @Select({"select * from ",ORDER_TABLE_NAME," where store_id=#{store_id} order by create_time DESC"})
    List<Order> selectOrderBySId(@Param("store_id")Long store_id);

    @Select({"select * from ",ITEM_TABLE_NAME," where order_id=#{order_id}"})
    List<OrderItem> selectOItemByOrderId(@Param("order_id") Long order_id);

    @Select({"select name from ",USER_TABLE_NAME," where id=#{id}"})
    String selectUserNameByOrderId(@Param("id") Long id);



    @Select({"select address from ",USER_TABLE_NAME," where id=#{id}"})
    String selectAddressByOrderId(@Param("id") Long id);

    @Select({"select * from ",USER_TABLE_NAME," where id=#{id}"})
    User selectUserByOrderId(@Param("id") Long id);

    //分类查询订单
    @Select({"select * from ",ORDER_TABLE_NAME," where store_id=#{store_id} and  status between #{BeforeStatus} and #{AfterStatus} order by create_time DESC"})
    List<Order> selectOrderByStatus(@Param("store_id")Long sid,@Param("BeforeStatus")Integer BeforeStatus,@Param("AfterStatus")Integer AfterStatus);


    //发货
    @Update({"update ",ORDER_TABLE_NAME," set status=#{status}  where id=#{id}"})
    Integer updateStatusByOrderId(@Param("status")Integer status,@Param("id")Long id);

    //
    @Update({"update ",ORDER_TABLE_NAME," set send_time=#{send_time} where id=#{id}"})
    Integer updateDeliveryTime(@Param("send_time")Date send_time,@Param("id")Long id);

    //
    @Update({"update ",ORDER_TABLE_NAME," set close_time=#{close_time} where id=#{id}"})
    Integer updateCloseTime(@Param("close_time")Date close_time,@Param("id")Long id);


    @Select({"select user_id from ",ORDER_TABLE_NAME," where id=#{id}"})
    Long selectUserIdNameByOrderId(@Param("id") Long id);

    @Select({"select store_id from ",ORDER_TABLE_NAME," where id=#{id}"})
    Long selectStoreIdNameByOrderId(@Param("id") Long id);




    //所有评论
    @Select({"select * from ",COMMENT_NAME," where sid=#{sid} order by create_time DESC"})
    List<Comment> findAllComment(@Param("sid")Long sid);


    //自动接单
    @Update({"update ",ORDER_TABLE_NAME," set status=#{status} where store_id=#{store_id} and status=1"})
    Integer autoRecieveOrder(@Param("status")Integer status,@Param("store_id")Long sid);


    //跟据sid 和 已付款状态  查询所有的order
    @Select({"select * from ",ORDER_TABLE_NAME," where status=#{status} and store_id=#{store_id}"})
    List<Order> findAllPaymentOrder(@Param("status")Integer status,@Param("store_id")Long store_id);





}
