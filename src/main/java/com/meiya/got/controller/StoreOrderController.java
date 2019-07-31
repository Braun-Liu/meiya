package com.meiya.got.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.po.Comment;
import com.meiya.got.po.Foods;
import com.meiya.got.po.Store;
import com.meiya.got.po.storeOrder;
import com.meiya.got.service.FoodsService;
import com.meiya.got.service.StoreOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(("/order/"))
public class StoreOrderController {
    @Autowired
    private FoodsService foodsService;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private StoreOrderService storeOrderService;


    /*//查询全部orders
    @GetMapping("/findorders")
    public String findMyAllOrder(Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("------------开始查询-------------");
        String store_id = request.getParameter("storeID");
        System.out.println("-------------商店ID为------"+Long.valueOf(store_id));

        ServerResponse<List<storeOrder>> allOrders = storeOrderService.findAllOrder(Long.valueOf(store_id));
        System.out.println("查询完毕--------------"+allOrders);
        if (allOrders.isSuccess()){
            System.out.println("成功返回数据------------"+allOrders.getMsg());
            model.addAttribute("allOrders",allOrders.getData());

            return "emp/order";
        }
        System.out.println("来到了失败页面------------------");
        response.getWriter().write("获取失败");
        return "emp/order";

    }*/


    //分页查询全部orders
    @GetMapping("pagefindorders")
    public String pageFindAllOrders(@RequestParam("storeID")Long storeID, Model model, @RequestParam(value="pageNo",defaultValue="1")Integer pageNo, @RequestParam(value="pageSize",defaultValue="50")Integer pageSize){

        PageHelper.startPage(pageNo,pageSize);

        List<storeOrder> storeOrders = storeOrderService.findAllOrder(storeID);
//        System.out.println("----------------------------------"+allOrder.getData().size());
        PageInfo<storeOrder> page = new PageInfo<storeOrder>(storeOrders);
//        PageInfo<storeOrder> page = storeOrderService.pagefindAllOrder(storeID, pageNo, pageSize).getData();
        System.out.println("分页规模-----------------"+page.getNextPage());
        System.out.println("============================"+page.getNextPage());
        System.out.println("============================"+page.getPageNum());

        model.addAttribute("allpage", page);

        return "emp/order";

    }



    //查询所属分类
    @GetMapping("refresh")
    public String refreshOrder(@RequestParam("status_id")Integer status_id,@RequestParam("store_id")Long sid,@RequestParam(value="pageNo",defaultValue="1")Integer pageNo, @RequestParam(value="pageSize",defaultValue="50")Integer pageSize,Model model){

        PageHelper.startPage(pageNo,pageSize);


        ServerResponse<List<storeOrder>> listServerResponse = storeOrderService.findOrderByStaus(sid, status_id);
        if (listServerResponse.isSuccess()){
            List<storeOrder> orderByStaus=listServerResponse.getData();

            PageInfo<storeOrder> orderPage = new PageInfo<storeOrder>(orderByStaus);


            model.addAttribute("allpage",orderPage);

            return "emp/order::orders";
        }


        return "redirect:/order/pagefindorders?storeID="+sid;

    }







    //接单
    @GetMapping(value = "sendOrder")
    @ResponseBody
    public boolean ReceiveOrder(@RequestParam("id") Long id,Model model){
        System.out.println("收到接单请求----------订单id--"+id);

        ServerResponse<String> stringServerResponse = storeOrderService.receiveOrder(id);

        model.addAttribute("msg",stringServerResponse.getMsg());

        if (stringServerResponse.isSuccess()){
            return true;
        }
        return false;
    }

    //发货
    @GetMapping(value = "deliveryOrder")
    @ResponseBody
    public boolean DeliveryOrder(@RequestParam("id") Long id,Model model){
        System.out.println("收到发货请求----------订单id--"+id);

        ServerResponse<String> stringServerResponse = storeOrderService.deliveryOrder(id);

        model.addAttribute("msg",stringServerResponse.getMsg());


        if (stringServerResponse.isSuccess()){
            return true;
        }
        return false;
    }


    //拒单
    @GetMapping(value = "refuseOrder")
    @ResponseBody
    public boolean refuseOrder(@RequestParam("id") Long id,Model model){

        ServerResponse<String> serverResponse=storeOrderService.refuseOrder(id);



        if (serverResponse.isSuccess()){
            System.out.println("拒单成功");
            return true;
        }
        return  false;
    }

    //自动接单
    @GetMapping(value = "autoRecieveOrder")
    @ResponseBody
    public String autoRecieveOrder(@RequestParam("storeID") Long id,Model model){

        ServerResponse<String> autoRecieveOrderResponse = storeOrderService.autoRecieveOrder(id);
        if (autoRecieveOrderResponse.isSuccess()){


            return autoRecieveOrderResponse.getMsg();
        }

        return  "开启失败";
    }



  @GetMapping("getcomments")
  public String getAllComments(Long store_id,Model model){
      ServerResponse<List<Comment>> allComments = storeOrderService.findAllComments(store_id);
      if (allComments.isSuccess()){

          model.addAttribute("allcomments",allComments.getData());
          return "emp/comments";
      }
         model.addAttribute("msg","查询失败");
      return "emp/comments";
  }

}
