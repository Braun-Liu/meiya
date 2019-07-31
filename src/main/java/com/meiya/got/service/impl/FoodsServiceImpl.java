package com.meiya.got.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.po.FoodCate;
import com.meiya.got.po.Foods;
import com.meiya.got.service.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.SQLOutput;
import java.util.List;

@Service
public class FoodsServiceImpl implements FoodsService {


    @Autowired
    private FoodDao foodDao;

    @Override
    @Transactional
    public ServerResponse<Foods> addFoods(Foods food) {
        if(food!=null){
            Date time= new java.sql.Date(new java.util.Date().getTime());
            food.setCreate_time(time);

           // food.setStatus(1);
            food.setUpdate_time(food.getCreate_time());
            foodDao.addfoods(food);

            return ServerResponse.createBySuccessMessage("添加成功");
        }

        return ServerResponse.createByErrorMessage("添加失败");



    }

    @Override
    @Transactional
    public ServerResponse<Foods> updateFoods(Foods food) {
        if(food!=null){

            if (foodDao.selectById(food.getId())!=null){

                Date time= new java.sql.Date(new java.util.Date().getTime());
                food.setUpdate_time(time);
                foodDao.updateTime(food);
                foodDao.updateCate_id_sub(food);
                foodDao.updatePhoto(food);
                foodDao.updatePhoto_sub(food);
                foodDao.updatePrice(food);
                foodDao.updateStatus(food);
                foodDao.updateStock(food);
                foodDao.updateDescription(food);
                foodDao.updateName(food);
                return ServerResponse.createBySuccessMessage("更新成功");
            }

        }


        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    @Transactional
    public ServerResponse<Foods> deleteFoods(List<Long> foodsID) {

        if(foodsID!=null){

            for (Long food:foodsID ){
                foodDao.deleteById(food);
            }
            return ServerResponse.createBySuccessMessage("删除成功");
        }

        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<Foods> deleteFood(Long foodsID) {
        foodDao.deleteById(foodsID);
        return ServerResponse.createBySuccessMessage("删除成功");


    }

    @Override
    public List<FoodCate> findAllFoodCate() {

        List<FoodCate> allFoodCate = foodDao.findAllFoodCate();

        return allFoodCate;
    }

/*
    @Transactional
    public ServerResponse<List<Foods>> selectpageAll(int store_id,int p,int size) {

            try {
                PageHelper.startPage(p,size);
                List<Foods> foods=foodDao.selectAll(store_id);

                return ServerResponse.createBySuccess("分页查询成功",foods);
            }catch (Exception e){
                System.out.println("-------------chucuo-------------------------");
                e.printStackTrace();
                return  ServerResponse.createByErrorMessage("分页查询失败");

            }

    }*/
    @Override
    @Transactional
    public ServerResponse<List<Foods>> selectAll(Long sid) {

        try {

                List<Foods> foods = foodDao.selectAll(sid);


                return ServerResponse.createBySuccess("查询成功", foods);


        }catch (Exception e){

            e.printStackTrace();
            return  ServerResponse.createByErrorMessage("查询失败");

        }

    }



    @Override
    public ServerResponse<PageInfo<Foods>> pageAllByCate(Long store_id, Integer cate_id, Integer p, Integer Size) {




        PageHelper.startPage(p,Size);

        List<Foods> list = foodDao.selectFoodsByCate(store_id,cate_id);

        //用PageInfo对结果进行包装
        PageInfo<Foods> page = new PageInfo<Foods>(list);


        return ServerResponse.createBySuccess("菜品分类 分页查询成功",page);
    }

    @Override
    public ServerResponse<PageInfo<Foods>> pageAll(Long sid, Integer p, Integer Size) {


        PageHelper.startPage(p,Size);

        List<Foods> list = foodDao.selectAll(sid);

        //用PageInfo对结果进行包装
        PageInfo<Foods> page = new PageInfo<Foods>(list);


        return ServerResponse.createBySuccess("分页成功",page);



    }

//    @Override
//    public ServerResponse<Page<Foods>> pageAll(int store_id) {
//
////        if(foodDao.selectBySId(store_id)==1){
////            Page<Foods> pagefoods= (Page<Foods>)foodDao.selectAll(store_id);
////
////            return  ServerResponse.createBySuccess("分页查询成功",pagefoods);
////        }
////        return ServerResponse.createByErrorMessage("店铺不存在");
//        return  null;
//    }


}
