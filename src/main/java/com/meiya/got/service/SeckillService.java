package com.meiya.got.service;

import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Events;
import com.meiya.got.po.Foods;
import com.meiya.got.po.Seckill;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public interface SeckillService {

    ServerResponse<String>  addSeckillGoods(Seckill seckill);

    ServerResponse<PageInfo<Events>> findAllEvents(Long sid,Integer p,Integer Size);

    /**
     * 报名活动
     * @param events_id
     * @param sid
     * @return
     */
    ServerResponse<String> joinTheSeckill(Long events_id,Long sid);

    /**
     * 取消报名
     * @param events_id
     * @param sid
     * @return
     */
    ServerResponse<String> exitTheSeckill(Long events_id,Long sid);

    ServerResponse<Seckill> findTheSeckill(Long events_id,Long sid);

    ServerResponse<String> updateSeckillFoods(Seckill seckill);
//    ServerResponse<List<>>
}
