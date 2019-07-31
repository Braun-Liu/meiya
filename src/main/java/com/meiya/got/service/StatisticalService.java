package com.meiya.got.service;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Foods;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface StatisticalService {
    //当日数据
    ServerResponse<Integer> TodaySales(Long sid);
    ServerResponse<BigDecimal> TodayRevenue(Long sid);

    //七天销量
    ServerResponse<List<BigDecimal>> LatestSevenDaysRevenue(Long sid);
    ServerResponse<List<Integer>> LatestSevenDaysSales(Long sid);
    ServerResponse<List<Integer>> TheEachDaysSales(Long sid,Integer days);


    //当月数据
    ServerResponse<BigDecimal> ThisMonthPayment(Long sid);
    ServerResponse<Integer> ThisMonthSales(Long sid);


    //某菜品总销量
    ServerResponse<Integer> TheFoodSumSales(Long sid,Long id);

    //菜品销量排行榜
    ServerResponse<List<Foods>> FoodsRank(Long sid);

    //订单状态统计
    ServerResponse<Integer> untreatedReminderNum(Long sid);
    ServerResponse<Integer> untreatedOrderNum(Long sid);
    ServerResponse<Integer> untreatedRefundNum(Long sid);
    ServerResponse<Integer> untreatedSendNum(Long sid);

    //总销售量
    ServerResponse<Integer> MyStoreAllSales(Long sid);

    //排名
    ServerResponse<Integer> myRankInSameClass(Long sid);
    ServerResponse<Integer> AllStore(Long sid);






}
