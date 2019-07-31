package com.meiya.got.service.impl;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.StatisticalDao;
import com.meiya.got.po.Foods;
import com.meiya.got.service.StatisticalService;
import com.meiya.got.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private StatisticalDao statisticalDao;

    @Override
    public ServerResponse<Integer> TodaySales(Long sid) {
        Integer todaySales = statisticalDao.selectStoreTodaySales(sid);


        return ServerResponse.createBySuccess(todaySales);
    }


    //今日销售额
    @Override
    public ServerResponse<BigDecimal> TodayRevenue(Long sid) {

        BigDecimal TodayRevenue = statisticalDao.selectStoreTodayPayment(sid);

        if (TodayRevenue==null){
            TodayRevenue=BigDecimalUtil.add(0.00,0.00);
        }

        System.out.println("今日收益---------------------"+TodayRevenue);

        return ServerResponse.createBySuccess(TodayRevenue);


    }

    @Override
    public ServerResponse<List<BigDecimal>> LatestSevenDaysRevenue(Long sid) {

        List<BigDecimal> sevenDaysRevenue=new ArrayList<BigDecimal>();
        try {

            for (int i=6;i>=0;i--){

                System.out.println("--------------------------------------");

                BigDecimal theDayPayments = statisticalDao.selectStorePastDayPayment(sid, i);
                if (theDayPayments==null){
                    theDayPayments=BigDecimal.valueOf(0);
                }
                sevenDaysRevenue.add(theDayPayments);
            }
            return ServerResponse.createBySuccess(sevenDaysRevenue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError();
    }

    /**
     * 七天销量
     * @param sid
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> LatestSevenDaysSales(Long sid) {

        List<Integer> SevenDaysSales=new ArrayList<Integer>();
        for (int i=6;i>=0;i--){
            Integer sales = statisticalDao.selectStorePastDaySales(sid, i);
            SevenDaysSales.add(sales);
        }
        return ServerResponse.createBySuccess(SevenDaysSales);
    }


    @Override
    public ServerResponse<List<Integer>> TheEachDaysSales(Long sid,Integer days) {

        List<Integer> TheMonthSales=new ArrayList<Integer>();
        for (int i=days-1;i>=0;i--){
            Integer sales = statisticalDao.selectStorePastDaySales(sid, i);
            TheMonthSales.add(sales);
        }
        return ServerResponse.createBySuccess(TheMonthSales);
    }



    @Override
    public ServerResponse<BigDecimal> ThisMonthPayment(Long sid) {
        BigDecimal MonthRevenue = statisticalDao.selectStoreMonthPayment(sid);
        return ServerResponse.createBySuccess(MonthRevenue);
    }

    @Override
    public ServerResponse<Integer> ThisMonthSales(Long sid) {
        Integer MonthSales = statisticalDao.selectStoreMonthSales(sid);
        return ServerResponse.createBySuccess(MonthSales);
    }



    @Override
    public ServerResponse<Integer> TheFoodSumSales(Long sid, Long id) {


        return ServerResponse.createBySuccess(statisticalDao.selectTheFooodSales(id));
    }

    @Override
    public ServerResponse<List<Foods>> FoodsRank(Long sid) {
        List<Foods> foodsList = statisticalDao.selectAllFoodsBySales(sid);


        return ServerResponse.createBySuccess(foodsList);
    }

    @Override
    public ServerResponse<Integer> untreatedReminderNum(Long sid) {



        return ServerResponse.createBySuccess(0);
    }

    @Override
    public ServerResponse<Integer> untreatedOrderNum(Long sid) {
        Integer i = statisticalDao.selectUntreatedOrder(sid, 1);


        return ServerResponse.createBySuccess(i);
    }

    @Override
    public ServerResponse<Integer> untreatedRefundNum(Long sid) {



        return ServerResponse.createBySuccess(0);
    }

    @Override
    public ServerResponse<Integer> untreatedSendNum(Long sid) {

        Integer i = statisticalDao.selectUntreatedOrder(sid, 6);


        return ServerResponse.createBySuccess(i);
    }

    @Override
    public ServerResponse<Integer> MyStoreAllSales(Long sid) {

        Integer allSales = statisticalDao.selectStoreAllSales(sid);

        return ServerResponse.createBySuccess(allSales);
    }

    @Override
    public ServerResponse<Integer> myRankInSameClass(Long sid) {
        return ServerResponse.createBySuccess(statisticalDao.selectMyRank(sid));
    }

    @Override
    public ServerResponse<Integer> AllStore(Long sid) {

        return ServerResponse.createBySuccess(statisticalDao.selectAllStoreNum(statisticalDao.findStoreCategoryId(sid)));
    }




}
