package com.meiya.got.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.dao.SeckillDao;
import com.meiya.got.po.Events;
import com.meiya.got.po.Seckill;
import com.meiya.got.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
@Service
public class SeckillServiceImpl implements SeckillService {

        @Autowired
        private FoodDao foodDao;

        @Autowired
        private SeckillDao seckillDao;

    /**
     * 增加秒杀商品
     * @param seckill
     * @return
     */
    @Override
    public ServerResponse<String> addSeckillGoods(Seckill seckill) {

        try {
            seckill.setStatus(0);
            Long events_id=seckill.getEvents_id();

            Events events = seckillDao.selectEventsById(events_id);

            seckill.setStart_date(events.getStart_time());

            seckillDao.addSeckillGoods(seckill);

            return ServerResponse.createBySuccess();

        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }

    }

    /**
     * 查询所有商品
     * @param p
     * @param Size
     * @return
     */

    @Override
    public ServerResponse<PageInfo<Events>> findAllEvents(Long sid,Integer p,Integer Size) {

        PageHelper.startPage(p,Size);

        List<Events> eventsList = seckillDao.selectAllEvents();

       /* for(Events events:eventsList){


            if (seckillDao.judgeMyStatus(events.getId(),sid)!=0){

                //设置为1  参加过活动
                events.setMyStatus(1);

            };

            //没参加过
            events.setMyStatus(0);



        }*/

        System.out.println("eventsList-----------------------------"+eventsList.toString());


        PageInfo<Events> events=new PageInfo<Events>(eventsList);

        return ServerResponse.createBySuccess("所有活动信息拿到了！",events);


    }

    /**
     * 改变参加状态
     * @param events_id
     * @param sid
     * @return
     */
    @Override
    public ServerResponse<String> joinTheSeckill(Long events_id, Long sid) {
        if (seckillDao.judgeMyStatus(events_id,sid)!=0){

            Seckill theEvents = seckillDao.findTheEvents(events_id, sid);

            System.out.println("此时的秒杀商品参加状态--------------------="+theEvents.getStatus());

            if (theEvents.getStatus()==2){
                seckillDao.SetGoodsStatus(theEvents.getId(),0);

                return ServerResponse.createBySuccess("success");
            }


            //设置为1  参加过活动
            return ServerResponse.createBySuccess("cancel");

        };

        return ServerResponse.createByError();

    }



    @Override
    public ServerResponse<String> exitTheSeckill(Long events_id, Long sid){
        if (seckillDao.judgeMyStatus(events_id,sid)!=0){
            Seckill theEvents = seckillDao.findTheEvents(events_id, sid);
            if (theEvents.getStatus()!=2){
                seckillDao.SetGoodsStatus(theEvents.getId(),2);
                return ServerResponse.createBySuccess("success");
            }
            return ServerResponse.createBySuccess("cancel");

        }

        return ServerResponse.createByError();

    }



    @Override
    public ServerResponse<Seckill> findTheSeckill(Long events_id, Long sid) {

        Seckill theSeckillGoods = seckillDao.findTheEvents(events_id, sid);

        if (theSeckillGoods==null){
            return ServerResponse.createBySuccess(null);
        }
        return ServerResponse.createBySuccess(theSeckillGoods);


    }

    @Override
    public ServerResponse<String> updateSeckillFoods(Seckill seckill) {

        Integer i = seckillDao.updateSeckill(seckill);
        if (i==1){
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByError();
    }





}
