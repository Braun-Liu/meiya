package com.meiya.got.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Esales;
import com.meiya.got.po.Foods;
import com.meiya.got.service.StatisticalService;
import com.meiya.got.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/data/")
public class StatisticalController {

    @Autowired
    private StatisticalService statisticalService;

    /**
     * 统计页面跳转
     * @param model
     * @param sid
     * @return
     */
    @RequestMapping("statisticalPage")
    public String statisticalPage(Model model, @RequestParam("storeID")Long sid){

        ServerResponse<Integer> todaySales = statisticalService.TodaySales(sid);
        ServerResponse<BigDecimal> MonthRevenue = statisticalService.ThisMonthPayment(sid);
        ServerResponse<BigDecimal> TodayRevenue = statisticalService.TodayRevenue(sid);
        ServerResponse<Integer> MonthSales = statisticalService.ThisMonthSales(sid);
        ServerResponse<List<BigDecimal>> LatestSevenDaysSales = statisticalService.LatestSevenDaysRevenue(sid);
        ServerResponse<Integer> allSales = statisticalService.MyStoreAllSales(sid);



        ArrayList<String> SevenDays = DateTimeUtil.getDays(7);
        ArrayList<String> weekDate=new ArrayList<>();
        for (String day:SevenDays
             ) {

            weekDate.add(DateTimeUtil.getWeek(day));
        }


        System.out.println("日期数组------"+SevenDays.toString());

        //JSONArray LatesSevenDays = JSONArray.parseArray(JSON.toJSONString(SevenDays));

        JSONArray SevenDaysSalesJSON = JSONArray.parseArray(JSON.toJSONString(LatestSevenDaysSales.getData()));
        System.out.println("转化为json对象---------"+SevenDaysSalesJSON.toString());

        model.addAttribute("todaySales",todaySales.getData());
        model.addAttribute("MonthRevenue",MonthRevenue.getData());
        model.addAttribute("TodayRevenue",TodayRevenue.getData());
        model.addAttribute("MonthSales",MonthSales.getData());
        model.addAttribute("SevenDaysSales",SevenDaysSalesJSON);
        model.addAttribute("LatesSevenDayS",weekDate);
        model.addAttribute("allSales",allSales.getData());



//        model.addAttribute();



        return "statistical";
    }

    //七天的销量
    @PostMapping("getSevenData")
    @ResponseBody
    public List<Esales> getSevenData(@RequestParam("sid") Long sid){

        List<BigDecimal> LatestSevenDaysRevenue = statisticalService.LatestSevenDaysRevenue(sid).getData();
        System.out.println("--------------"+LatestSevenDaysRevenue.toString());


        ArrayList<String> SevenDays = DateTimeUtil.getDays(7);
        List<Esales> Revenue  = new ArrayList<Esales>();
        for (int i=0;i<=6;i++){
            Revenue .add(new Esales(LatestSevenDaysRevenue.get(i),SevenDays.get(i)));;

        }

        System.out.println("------------"+Revenue .toString());

        return Revenue ;

    }

    @PostMapping("getSevenSales")
    @ResponseBody
    public List<Esales> getSevenSales(@RequestParam("sid")Long sid){
        List<Integer> LatestSevenDaysSales = statisticalService.LatestSevenDaysSales(sid).getData();
        ArrayList<String> SevenDays = DateTimeUtil.getDays(7);
        List<Esales> saleslist = new ArrayList<Esales>();
        for (int i=0;i<=6;i++){
            saleslist.add(new Esales(SevenDays.get(i),LatestSevenDaysSales.get(i)));


        }

        System.out.println("--------七天销量--"+saleslist.toString());

        return saleslist;

    }


    @PostMapping("getTheMonthSales")
    @ResponseBody
    public List<Esales> GetTheMonthSales(@RequestParam("sid")Long sid){
        List<Integer> TheMonthEachDaysSales = statisticalService.TheEachDaysSales(sid,30).getData();
        ArrayList<String> ThirtyDays = DateTimeUtil.getDays(30);
        List<Esales> saleslist = new ArrayList<Esales>();
        for (int i=0;i<=29;i++){
            saleslist.add(new Esales(ThirtyDays.get(i),TheMonthEachDaysSales.get(i)));
        }

        System.out.println("--------三十天销量--"+saleslist.toString());

        return saleslist;

    }



    //食品排名
    @PostMapping("foodsRank")
    public String foodsrank(@RequestParam("sid")Long sid,Model model){

        List<Foods> foodsList = statisticalService.FoodsRank(sid).getData();

        model.addAttribute("foodsrank",foodsList);

        return "statistical::rank_refresh";

    }


    //未处理订单
    @PostMapping("untreated")
    public String untreated(@RequestParam("sid")Long sid,Model model){
        Integer untreatedOrderNum = statisticalService.untreatedOrderNum(sid).getData();
        Integer untreatedReminder = statisticalService.untreatedReminderNum(sid).getData();
        Integer untreatedRefund = statisticalService.untreatedRefundNum(sid).getData();
        Integer untreatedSend=statisticalService.untreatedSendNum(sid).getData();

        model.addAttribute("untreatedOrderNum",untreatedOrderNum);
        model.addAttribute("untreatedReminder",untreatedReminder);
        model.addAttribute("untreatedRefund",untreatedRefund);
        model.addAttribute("untreatedSendNum",untreatedSend);

        return "statistical::untreated_orders";
    }

    //最新销量
    @PostMapping("latestSales")
    public  String latestSales(@RequestParam("sid")Long sid,Model model){
        ServerResponse<Integer> todaySales = statisticalService.TodaySales(sid);
        ServerResponse<BigDecimal> MonthRevenue = statisticalService.ThisMonthPayment(sid);
        ServerResponse<BigDecimal> TodayRevenue = statisticalService.TodayRevenue(sid);
        ServerResponse<Integer> MonthSales = statisticalService.ThisMonthSales(sid);

        model.addAttribute("todaySales",todaySales.getData());
        model.addAttribute("MonthRevenue",MonthRevenue.getData());
        model.addAttribute("TodayRevenue",TodayRevenue.getData());
        model.addAttribute("MonthSales",MonthSales.getData());

        return "statistical::latestSales";
    }

    //排名
    @PostMapping("myrank")
    public  String myrank(@RequestParam("sid")Long sid,Model model){
        ServerResponse<Integer> allStore = statisticalService.AllStore(sid);
        ServerResponse<Integer> myrank = statisticalService.myRankInSameClass(sid);

        model.addAttribute("allStore",allStore.getData());
        model.addAttribute("myrank",myrank.getData());

        return "statistical::myrank";
    }



}
