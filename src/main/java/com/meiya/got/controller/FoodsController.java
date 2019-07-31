package com.meiya.got.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.exception.ImgException;
import com.meiya.got.po.FoodCate;
import com.meiya.got.po.Foods;
import com.meiya.got.service.FoodsService;
import com.meiya.got.service.uploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/foods/")
public class FoodsController {
    @Autowired
    private FoodsService foodsService;

    @Autowired
    private uploadService uploadservice;

    @Autowired
    private FoodDao foodDao;

    @RequestMapping(value = "findMyAll",method = RequestMethod.GET)
    public String findAll(HttpServletRequest request,Model model){

        Long sid= Long.valueOf(request.getParameter("storeID"));


        ServerResponse<List<Foods>> listServerResponse = foodsService.selectAll(sid);
        if(listServerResponse.isSuccess()){
            model.addAttribute("foods",listServerResponse.getData());

            return "emp/list";
        }

        model.addAttribute("foods",null);
        return "emp/list";

    }
// PageHelper.startPage(1,10);




    /**
     *
     * 分页查询
     * @param storeID 店铺id
     * @param model
     * @param pageNo 分页起始页
     * @param pageSize  每一页条数
     * @return foods集合
     */

    @RequestMapping(value = "pagefindMyAll",method = RequestMethod.GET)
    public String pagefindAll(String storeID, Model model, @RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="5")int pageSize){
//        int store_id= Integer.parseInt(storeID);
        List<FoodCate> allFoodCate = foodsService.findAllFoodCate();

        Long sid=Long.valueOf(storeID);

        PageInfo<Foods> page=foodsService.pageAll(sid,pageNo,pageSize).getData();
        model.addAttribute("pageInfo", page);
        model.addAttribute("foodcates",allFoodCate);
        //System.out.println(page.toString());

        return  "emp/list";

//            model.addAttribute("list",listServerResponse.getData());
//            model.addAttribute("msg",listServerResponse.getMsg());
//            return  "foodsManage";

}

    @GetMapping("refresh")
    public String updatetable(Model model,@RequestParam("store_id") Long store_id,@RequestParam("cate_id")Integer cate_id, @RequestParam(value="pageNo",defaultValue="1")Integer pageNo, @RequestParam(value="pageSize",defaultValue="5")Integer pageSize){

        if(cate_id==null){
            System.out.println("商家为空----------");
            PageInfo<Foods> page=foodsService.pageAll(store_id,pageNo,pageSize).getData();
            model.addAttribute("pageInfo", page);
            return "emp/list::table_refresh";

        }
        PageInfo<Foods> page=foodsService.pageAllByCate(store_id,cate_id,pageNo,pageSize).getData();

        model.addAttribute("pageInfo", page);
        System.out.println("到了！要返回了-----------"+page);
        return "emp/list::table_refresh";
    }




    //一次删除一个食物
    @DeleteMapping(value = "deletefood/{id}")
    public String deleteFood(@PathVariable("id") Long id){
        Long aLong = foodDao.selectStoreById(id);
        System.out.println("删除的食物ID为：------------"+id);
        ServerResponse<Foods> foodsResponse = foodsService.deleteFood(id);
        System.out.println(foodsResponse.getMsg());
        System.out.println("执行删除的店铺ID为--------------------"+aLong);
        if (foodsResponse.isSuccess()){
            System.out.println("删除成功");
            return "redirect:/foods/pagefindMyAll?storeID="+String.valueOf(aLong);
        }
        return  "error";
    }

    //一次删除多个食物  （未实现）
    @RequestMapping(value = "deleteFoods",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String deleteFoods(@RequestBody List<Long> foodId,Model model){
        ServerResponse<Foods> foodsResponse = foodsService.deleteFoods(foodId);
        if (foodsResponse.isSuccess()){
            model.addAttribute(foodsResponse.getMsg());
            return "foodsManage";

        }
        model.addAttribute(foodsResponse.getMsg());
        return  "error";
    }

    //   转到addfood界面
    @RequestMapping(value = "addfoodpage",method = RequestMethod.GET)
    public String toAddFoodsPage(Model model){

        List<FoodCate> allFoodCate = foodsService.findAllFoodCate();

        model.addAttribute("foodcates",allFoodCate);

        return "emp/add";


    }

    //添加食物
    @RequestMapping(value = "addfoods",method = RequestMethod.POST)
    public String addfoods(Foods food, @RequestParam("file") MultipartFile file,Model model){

        System.out.println("保存的菜品信息"+food.toString());

        String filename = file.getOriginalFilename();
        System.out.println("菜品图片名字--------------：："+filename);

        try {
            String uploadUrl = uploadservice.addHead(file);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
            model.addAttribute("url",uploadUrl);
            model.addAttribute("success", true);
            model.addAttribute("errorCode", 0);
            model.addAttribute("imgMsg", "上传成功");
            food.setPhoto_url(uploadUrl);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("url",null);
            model.addAttribute("success", false);
            model.addAttribute("errorCode", 1);
            model.addAttribute("imgMsg", "上传失败");
        }
        System.out.println("传入的photo地址---"+food.getPhoto_url());
        ServerResponse<Foods> foodsServerResponse = foodsService.addFoods(food);
        if (foodsServerResponse.isSuccess()){
            model.addAttribute("msg",foodsServerResponse.getMsg());
            System.out.println("插入成功——————————————————————————————————");
//            return  "redirect:/foods/findMyAll/{"+food.getStore_id()+"}";
            return  "redirect:/foods/pagefindMyAll?storeID="+String.valueOf(food.getSid());
        }
        model.addAttribute("msg",foodsServerResponse.getMsg());
        return "emp/add";
    }

    /**
     * 跳转修改食品界面（重用添加页面）
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/updateFoodsPage/{id}")
    public String updateFoodsPage(@PathVariable("id")Long id,Model model)  {
        System.out.println("菜品的id为-----------------："+id);
        Foods foods = foodDao.selectById(id);
        model.addAttribute("foods",foods);
        System.out.println("原来菜品对象的值----------"+foods.toString());
        List<FoodCate> allFoodCate = foodsService.findAllFoodCate();
        model.addAttribute("foodcates",allFoodCate);
        System.out.println("菜品的种类"+allFoodCate);
        return "emp/update";
    }

    /**
     * 更新菜品信息
     * @param foods
     * @param model
     * @return
     */
    @PutMapping("addfoods")
    public String updateFoods(Foods foods,@RequestParam("file") MultipartFile file, Model model) throws IOException, ImgException {
        System.out.println("修改的对象属性---------："+foods);

        System.out.println("修改前的图片地址"+foods.getPhoto_url());
        String photourl = uploadservice.updateHead(file, foods.getId());
        if(photourl!=null){
            System.out.println("有图片-");
            foods.setPhoto_url(photourl);
        }
        System.out.println("修改后的图片地址"+foods.getPhoto_url());

        ServerResponse<Foods> foodsServerResponse = foodsService.updateFoods(foods);



        System.out.println(foodsServerResponse.getMsg());
        if (foodsServerResponse.isSuccess()){
            System.out.println("修改好了");
            return "redirect:/foods/pagefindMyAll?storeID="+String.valueOf(foods.getSid());
        }
            model.addAttribute("msg",foodsServerResponse.getMsg());
         return "error";
    }


//    public String

}
