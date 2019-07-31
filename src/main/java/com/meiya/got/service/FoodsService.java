package com.meiya.got.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Admin;
import com.meiya.got.po.FoodCate;
import com.meiya.got.po.Foods;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.util.List;

@Service
public interface FoodsService {


    ServerResponse<Foods> addFoods(Foods food);


    ServerResponse<Foods> updateFoods(Foods food);


    ServerResponse<Foods> deleteFoods(List<Long> foodsID);

    ServerResponse<Foods> deleteFood(Long foodsID);

    List<FoodCate> findAllFoodCate();

    ServerResponse<List<Foods>>  selectAll(Long store_id);

    //ServerResponse<List<Foods>>  selectpageAll(Long store_id, int p, int Size);

    ServerResponse<PageInfo<Foods>>  pageAll(Long store_id, Integer p, Integer Size);

    ServerResponse<PageInfo<Foods>>  pageAllByCate(Long store_id,Integer cate_id, Integer p, Integer Size);





}
