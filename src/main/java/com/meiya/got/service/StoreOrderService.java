package com.meiya.got.service;


import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Comment;
import com.meiya.got.po.storeOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreOrderService {

    List<storeOrder> findAllOrder(Long store_id);

    //List<storeOrder> findAllOrderList(Long store_id);

    ServerResponse<PageInfo<storeOrder>> pagefindAllOrder(Long store_id,Integer p,Integer size);

    ServerResponse<String> receiveOrder(Long order_id);

    ServerResponse<String> deliveryOrder(Long order_id);

    ServerResponse<String> refuseOrder(Long id);

    ServerResponse<List<Comment>> findAllComments(Long store_id);

    ServerResponse<List<storeOrder>>  findOrderByStaus(Long sid,Integer status);

    //自动接单
    ServerResponse<String> autoRecieveOrder(Long sid);
    //异步增加销量
    public void addSalesByOrderId(Long order_id);
    public void addSalesBySid(Long sid);




}
