package com.meiya.got.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.dao.OrderDao;
import com.meiya.got.dao.StoreDAO;
import com.meiya.got.po.*;
import com.meiya.got.rabbitmq.sender.DirectSender;
import com.meiya.got.rabbitmq.sender.FanoutSender;
import com.meiya.got.rabbitmq.sender.TopicSender;
import com.meiya.got.service.StoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class StoreOrderServiceImpl implements StoreOrderService {

    @Autowired
    private StoreDAO storeDAO;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private DirectSender directSender;

    @Override
    public List<storeOrder> findAllOrder(Long store_id) {

        System.out.println("store_id的值为——————————————"+store_id);
        List<Order> orders = orderDao.selectOrderBySId(store_id);
        System.out.println("查询到的orders-----------"+orders.size());
        List<storeOrder> storeOrders=new ArrayList<>();
        for (Order order:orders){

            Long user_id = order.getUser_id();
           // System.out.println("user id---------------"+user_id);
            Long order_id=order.getId();
           // System.out.println("order id--------------"+order_id);
            List<OrderItem> orderItems = orderDao.selectOItemByOrderId(order_id);
            //System.out.println("查询到的orderiteams---------"+orderItems);
            List<FoodsOrder> foodsOrderList=findFoodsOrder(orderItems);
            //System.out.println("得到的foodorderlist--------------------"+foodsOrderList);
            //order里的属性提取
            String address = order.getAddress();
            int payment_type = order.getPayment_type();
            BigDecimal payment = order.getPayment();
            String note = order.getNote();
            Date create_time = order.getCreate_time();
            int status = order.getStatus();
            //user里的属性
            User user = orderDao.selectUserByOrderId(user_id);
            String name=user.getName();

            String phone=user.getPhone();

            storeOrder sorder=new storeOrder(order_id,name,address,foodsOrderList,payment,note,create_time,status ,payment_type);

            storeOrders.add(sorder);

            System.out.println("第n个storeOrder对象----------------------"+sorder.toString());
        }

        return storeOrders;


    }

    //分页查询订单
    @Override
    public ServerResponse<List<storeOrder>> findOrderByStaus(Long sid, Integer status) {

        if (status!=null){



            //未完成订单
            if(status==1){
                List<Order> untreatedOrders = orderDao.selectOrderByStatus(sid, 0, 1);
                List<storeOrder> storeOrders = orderIntegrate(untreatedOrders);
                return ServerResponse.createBySuccess(storeOrders);
            }else if(status==2){
                //进行中订单
                List<Order> receivedOrder = orderDao.selectOrderByStatus(sid, 6, 6);
                List<storeOrder> storeOrders = orderIntegrate(receivedOrder);
                return ServerResponse.createBySuccess(storeOrders);
            }else if (status==3){
                //已完成订单
                List<Order> finishedOrder=orderDao.selectOrderByStatus(sid,2,5);
                List<storeOrder> storeOrders = orderIntegrate(finishedOrder);
                return ServerResponse.createBySuccess(storeOrders);
            }

        }else {
            List<Order> finishedOrder=orderDao.selectOrderByStatus(sid,0,6);
            List<storeOrder> storeOrders = orderIntegrate(finishedOrder);
            return ServerResponse.createBySuccess(storeOrders);
        }

        return ServerResponse.createByError();
    }


    /**
     * 自动接单
     * @param sid
     * @return
     */
    @Override
    public ServerResponse<String> autoRecieveOrder(Long sid) {
        if (storeDAO.findMyAutorecieveStatus(sid)==0){

            storeDAO.autoRecieve(1,sid);
            System.out.println("开启自动接单------------");
            return ServerResponse.createBySuccessMessage("您已开启自动接单");
        }else {
            storeDAO.autoRecieve(0,sid);
            System.out.println("关闭自动接单------------");
            return ServerResponse.createBySuccessMessage("您已关闭自动接单");


        }

    }

    /*@Override
    public List<storeOrder> findAllOrderList(Long store_id) {

        //System.out.println("store_id的值为——————————————"+store_id);
        List<Order> orders = orderDao.selectOrderBySId(store_id);
       // System.out.println("查询到的orders-----------"+orders);
        List<storeOrder> storeOrders=new ArrayList<>();
        for (Order order:orders){

            Long uid = order.getUser_id();
          //  System.out.println("user id---------------"+user_id);
            Long oid=order.getId();
          //  System.out.println("order id--------------"+order_id);
            List<OrderItem> orderItems = orderDao.selectOItemByOrderId(oid);
          //  System.out.println("查询到的orderiteams---------"+orderItems);
            List<FoodsOrder> foodsOrderList=findFoodsOrder(orderItems);
          //  System.out.println("得到的foodorderlist--------------------"+foodsOrderList);
            //order里的属性提取
            String address = order.getAddress();
            Integer payment_type = order.getPayment_type();
            BigDecimal payment = order.getPayment();
            String note = order.getNote();
            Date create_time = order.getCreate_time();
            Integer status = order.getStatus();

            //user里的属性
            User user = orderDao.selectUserByOrderId(uid);
            String name=user.getName();
            String phone=user.getPhone();


            storeOrder sorder=new storeOrder(oid,name,address,foodsOrderList,payment,note,create_time,status ,payment_type);

            storeOrders.add(sorder);

           // System.out.println("第n个storeOrder对象----------------------"+sorder.toString());
        }

        return storeOrders;
    }*/






    @Override
    public ServerResponse<PageInfo<storeOrder>> pagefindAllOrder(Long store_id,Integer p,Integer Size) {

        List<storeOrder> storeOrders=new ArrayList<>();

        PageHelper.startPage(p,Size);

        System.out.println("store_id的值为——————————————"+store_id);


        List<Order> orders = orderDao.selectOrderBySId(store_id);
        System.out.println("order的size---------------------------"+orders.size());
       // System.out.println("查询到的orders-----------"+orders);


        int i=0;
        for (Order order:orders){
            i++;
            Long user_id = order.getUser_id();
            //System.out.println("user id---------------"+user_id);
            Long order_id=order.getId();
           // System.out.println("order id--------------"+order_id);
            List<OrderItem> orderItems = orderDao.selectOItemByOrderId(order_id);
            //System.out.println("查询到的orderiteams---------"+orderItems);
            List<FoodsOrder> foodsOrderList=findFoodsOrder(orderItems);
            //System.out.println("得到的foodorderlist--------------------"+foodsOrderList);
            //order里的属性提取
            String address = order.getAddress();
            Integer payment_type = order.getPayment_type();
            BigDecimal payment = order.getPayment();
            String note = order.getNote();
            Date create_time = order.getCreate_time();
            Integer status = order.getStatus();

            //user里的属性
            User user = orderDao.selectUserByOrderId(user_id);
            String name=user.getName();
            String phone=user.getPhone();


            storeOrder sorder=new storeOrder(order_id,name,address,foodsOrderList,payment,note,create_time,status ,payment_type);

            storeOrders.add(sorder);

            //System.out.println("第n个storeOrder对象----------------------"+sorder.toString());
        }
        System.out.println("i----------="+i);

        PageInfo<storeOrder> page = new PageInfo<storeOrder>(storeOrders);

        return ServerResponse.createBySuccess("分页查询所有订单成功",page);
    }


    @Override
    public ServerResponse<String> receiveOrder(Long order_id) {


        Integer i=orderDao.updateStatusByOrderId(6,order_id);
        if (i==1){
            Long store_id = orderDao.selectStoreIdNameByOrderId(order_id);
            Long user_id=orderDao.selectUserIdNameByOrderId(order_id);
            MsgConnection msgConnection=new MsgConnection(order_id,4,0,store_id,user_id);
            directSender.sendMsgToUser(msgConnection);
            addSalesByOrderId(order_id);
            return ServerResponse.createBySuccess("成功接单");
        }

        return ServerResponse.createByErrorMessage("接单失败");

/**/

    }

    /**
     * 发货
     * @param order_id
     * @return
     */
    @Override
    public ServerResponse<String> deliveryOrder(Long order_id) {
        Integer i=orderDao.updateStatusByOrderId(2,order_id);
        Integer timeStatus=orderDao.updateDeliveryTime(new Date(),order_id);

        if (i==1){
            Long store_id = orderDao.selectStoreIdNameByOrderId(order_id);
            Long user_id=orderDao.selectUserIdNameByOrderId(order_id);
            MsgConnection msg=new MsgConnection(order_id,2,0,store_id,user_id);
            directSender.sendMsgToUser(msg);
            addSalesByOrderId(order_id);
            return ServerResponse.createBySuccess("成功发货");
        }
        return ServerResponse.createByErrorMessage("发货失败");

    }


    /**
     * 拒单
     * @param order_id
     * @return
     */
    @Override
    public ServerResponse<String> refuseOrder(Long order_id) {

        Integer i=orderDao.updateStatusByOrderId(4,order_id);

        Integer closeStatus = orderDao.updateCloseTime(new Date(), order_id);


        if (i!=0){

            Long store_id = orderDao.selectStoreIdNameByOrderId(order_id);
            Long user_id = orderDao.selectUserIdNameByOrderId(order_id);
            MsgConnection msgConnection=new MsgConnection(order_id,1,0,store_id,user_id);

            directSender.sendMsgToUser(msgConnection);

            return ServerResponse.createBySuccess("拒单成功");
        }

        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<List<Comment>> findAllComments(Long store_id) {
        List<Comment> allComments = orderDao.findAllComment(store_id);
        System.out.println("所有的订单---"+allComments);
        for (Comment comment:allComments) {
            String username = orderDao.selectUserNameByOrderId(comment.getUser_id());
            comment.setUsername(username);
        }
        return ServerResponse.createBySuccess("查询订单评论成功",allComments);

    }



    /**
     * 订单商品查找
     * @param orderItems
     * @return
     */
    public   List<FoodsOrder> findFoodsOrder(List<OrderItem> orderItems){
         List<FoodsOrder> foodsOrderList=new ArrayList<>();
        for (OrderItem orderItem:orderItems){
            FoodsOrder foodsOrder=new FoodsOrder();
            Integer quantity = orderItem.getQuantity();
            //System.out.println("商品数量——————"+quantity);
            BigDecimal total_price = orderItem.getTotal_price();
            //System.out.println("商品总价——————"+total_price);
            Long food_id = orderItem.getFood_id();
            //System.out.println("食物id----------"+food_id);

            Foods food = foodDao.selectById(food_id);
            //System.out.println("food对象的tostring---"+food);
            foodsOrder.setFoods(food);
            //System.out.println("成功插入food---------"+food);

            foodsOrder.setPrice(total_price);
            //System.out.println("成功插入totalprice---"+total_price);
            foodsOrder.setQuantity(quantity);
            //System.out.println("成功插入商品数量---"+quantity);

            foodsOrderList.add(foodsOrder);
           // System.out.println("得到的foodorderlist---------"+foodsOrderList);
        }

        //System.out.println("把所有的item转化成了foodsorder！---------------------"+foodsOrderList.toString());
        return foodsOrderList;
    }


    /**
     * 订单封装方法
     * @param allorders
     * @return
     */
    public List<storeOrder> orderIntegrate(List<Order> allorders){

        List<storeOrder> storeOrders=new ArrayList<storeOrder>();

        for (Order order:allorders){

            Long user_id = order.getUser_id();
            // System.out.println("user id---------------"+user_id);
            Long order_id=order.getId();
            // System.out.println("order id--------------"+order_id);
            List<OrderItem> orderItems = orderDao.selectOItemByOrderId(order_id);
            //System.out.println("查询到的orderiteams---------"+orderItems);
            List<FoodsOrder> foodsOrderList=findFoodsOrder(orderItems);
            //System.out.println("得到的foodorderlist--------------------"+foodsOrderList);
            //order里的属性提取
            String address = order.getAddress();
            int payment_type = order.getPayment_type();
            BigDecimal payment = order.getPayment();
            String note = order.getNote();
            Date create_time = order.getCreate_time();
            int status = order.getStatus();
            //user里的属性
            User user = orderDao.selectUserByOrderId(user_id);
            String name=user.getName();

            String phone=user.getPhone();

            storeOrder sorder=new storeOrder(order_id,name,address,foodsOrderList,payment,note,create_time,status ,payment_type);

            storeOrders.add(sorder);

            System.out.println("第n个storeOrder对象----------------------"+sorder.toString());
        }



        return storeOrders;
    }

        //异步增加销量
        @Override
        @Async
        public void addSalesByOrderId(Long order_id){

            System.out.println("手动接单开启线程了------");
            List<OrderItem> orderItems = orderDao.selectOItemByOrderId(order_id);
            for (OrderItem orderItem:orderItems){
                foodDao.addFoodSales(orderItem.getQuantity(),orderItem.getFood_id());

            }

        }

        @Override
        @Async
        public void addSalesBySid(Long sid){

            System.out.println("自动接单开启线程了------");
            storeDAO.addStoreSalesById(1,sid);

            List<Order> allPaymentOrder = orderDao.findAllPaymentOrder(1, sid);
            orderDao.autoRecieveOrder(6,sid);

            for (Order order:allPaymentOrder){

                storeDAO.addStoreSalesById(1,order.getStore_id());


                List<OrderItem> orderItems = orderDao.selectOItemByOrderId(order.getId());

                for (OrderItem orderItem:orderItems){

                    foodDao.addFoodSales(orderItem.getQuantity(),orderItem.getFood_id());

                }
            }
        }
}
