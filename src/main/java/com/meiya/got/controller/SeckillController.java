package com.meiya.got.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Events;
import com.meiya.got.po.Foods;
import com.meiya.got.po.Seckill;
import com.meiya.got.service.FoodsService;
import com.meiya.got.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill/")
public class SeckillController  {


    @Autowired
    private SeckillService seckillService;

    @Autowired
    private FoodsService foodsService;




    @GetMapping(value = "allevents")
    public String eventsPage(Model model,@RequestParam("storeID") Long sid, @RequestParam(value="pageNo",defaultValue="1")Integer pageNo, @RequestParam(value="pageSize",defaultValue="5")Integer pageSize){

        ServerResponse<PageInfo<Events>> allEvents = seckillService.findAllEvents(sid,pageNo,pageSize);

        PageInfo<Events> eventsData = allEvents.getData();

        System.out.println("得到的events--------------------------"+eventsData);

        model.addAttribute("events",eventsData);

        return "emp/events";
    }


    /**
     * 参加活动
     * @param model

     * @return
     */
    @PostMapping("joinevent")
    @ResponseBody
    public String joinEvent(Model model,@RequestParam("events_id")Long events_id,@RequestParam("store_id")Long store_id){
    //
        System.out.println("报名后台-------------------------------------------------------");
/**/

/*
        Long eventsid= params.getLong("events_id");
        Long store_id=params.getLong("store_id");*/

        ServerResponse<String> serverResponse = seckillService.joinTheSeckill(events_id, store_id);

        if (serverResponse.getData().equals("success")){
            System.out.println("----------------成功参加");

            return "您已经成功报名秒杀推广活动，店铺人气值增加了！";
        }else if(serverResponse.getData().equals("cancel")){

            return "您已报名成功，请勿重复报名！";
        }
        ;

        return "对不起，您没有添加活动商品，请添加！";
    }


    @PostMapping("exitevent")
    @ResponseBody
    public String exitEvent(Model model,@RequestParam("events_id")Long events_id,@RequestParam("store_id")Long store_id){
        //
        System.out.println("取消后台-------------------------------------------------------");
        /**/

/*
        Long eventsid= params.getLong("events_id");
        Long store_id=params.getLong("store_id");*/

        ServerResponse<String> serverResponse = seckillService.exitTheSeckill(events_id,store_id);

        if (serverResponse.getData().equals("success")){


            return "您退出了此活动！请三思！";
        }else if(serverResponse.getData().equals("cancel")){

            return "您未参加！";
        }
        ;

        return "出错了！";
    }



    /**
     *
     * @param events_id
     * @param store_id
     * @param model
     * @return
     */
    @GetMapping("addgoods")
    public String addEventsGood(@RequestParam("event_id")Long events_id,@RequestParam("store_id")Long store_id,Model model){


        model.addAttribute("events_id",events_id);

        ServerResponse<Seckill> theSeckill = seckillService.findTheSeckill(events_id, store_id);

        model.addAttribute("seckill",theSeckill.getData());


        ServerResponse<List<Foods>> list = foodsService.selectAll(store_id);

        model.addAttribute("foods",list.getData());

        return "emp/editgoods";
    }

    /**
     * 添加活动商品
     * @param seckill
     * @param model
     * @return
     */
    @PostMapping("insertfood")
    public String insertFoods(Seckill seckill,Model model){

        System.out.println("添加的秒杀商品-------------"+seckill.toString());

        ServerResponse<String> serverResponse = seckillService.addSeckillGoods(seckill);

        Long sid=seckill.getSid();

        return "redirect:allevents?storeID="+sid;
    }

    /**
     * 更改活动商品
     * @param seckill
     * @param model
     * @return
     */
    @PutMapping("insertfood")
    public String updateFoods(Seckill seckill,Model model){

        System.out.println("更改的秒杀商品-------------"+seckill.toString());

        ServerResponse<String> serverResponse = seckillService.updateSeckillFoods(seckill);

        Long sid=seckill.getSid();

        return "redirect:allevents?storeID="+sid;
    }


    //更新活动商品



}


