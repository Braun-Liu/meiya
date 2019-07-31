package com.meiya.got.controller;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.StoreDAO;
import com.meiya.got.exception.ImgException;
import com.meiya.got.po.Category;
import com.meiya.got.po.License;
import com.meiya.got.po.Store;
import com.meiya.got.service.StoreService;
import com.meiya.got.service.uploadService;
import com.meiya.got.util.AliSmsServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;


@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreDAO storeDao;

    @Autowired
    private uploadService uploadservice;

    /**
     * 登录
     * @param model
     * @param request
     * @param httpResponse
     * @param session
     * @param map
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/storelogin", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, HttpServletResponse httpResponse, HttpSession session, Map<String, Object> map) throws ServletException, IOException {

        String phone = request.getParameter("username");
        String password = request.getParameter("password");


        ServerResponse<Store> response = storeService.login(phone, password);
        Long sid = response.getData().getId();

        if (response.isSuccess()) {
            session.setAttribute("storeUser", response.getData());
            System.out.println(session.getAttribute("storeUser"));
            session.setAttribute("phone", response.getData().getPhone());
            session.setAttribute("storeName", response.getData().getStorename());
            session.setAttribute("storeID", sid);

            model.addAttribute("store", response.getData());
            System.out.println("得到的用户" + response.getData().toString());
            return "redirect:/data/statisticalPage?storeID="+sid;
        }

        map.put("msg", "用户名密码错误");
        return "login";
    }

    /**
     * 注册
     * @param model
     * @return
     */
    @RequestMapping(value = "registerPage", method = RequestMethod.GET)
    public String toRegisterPage(Model model) {

        List<Category> allStoreCate = storeService.findAllStoreCate();
        model.addAttribute("storecates", allStoreCate);
        return "emp/register";
    }

    /**
     * 商家注册
     *
     * @param store
     * @param file
     * @param model
     * @return
     * @throws IOException
     * @throws ImgException
     */
    @RequestMapping(value = "storeRegister", method = RequestMethod.POST)
    public String Register(Store store, @RequestParam("file") MultipartFile file, @RequestParam("code") String code, Model model,HttpSession session) throws IOException, ImgException {
        String storename = store.getStorename();
        String password = store.getPassword();
        String phone = store.getPhone();
        String address = store.getAddress();
        String rightcode= (String) session.getAttribute("code");
        if(!code.equalsIgnoreCase(rightcode)){
            model.addAttribute("msg","验证码不正确，请重新注册");
            return "emp/register";
        }


        String description = store.getDescription();


        //      int cate_id = Integer.parseInt(request.getParameter("cate_id"));
        int cate_id = store.getCategory_id();


        System.out.println("得到了商家的图片------" + file.getOriginalFilename());
        String photo_url = uploadservice.addHead(file);


        ServerResponse<Store> register = storeService.Register(storename, phone, password, address, cate_id, description, photo_url);
        if (register.isSuccess()) {
            model.addAttribute("msg", register.getMsg());
            model.addAttribute("store", register.getData());
        }
        model.addAttribute("msg", register.getMsg());


        return "login";
    }

    //登出
    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {


        Long store_id = (Long) session.getAttribute("storeID");
        storeDao.CloseById(store_id,0);
        System.out.println("登出自动关店了----------");
        session.setMaxInactiveInterval(0);


        return "redirect:/";
    }

    @GetMapping("deleteStore")
    public String deleteStore(@RequestBody String phone, Model model) {
        ServerResponse<Store> storeServerResponse = storeService.deleteStore(phone);
        if (phone != null) {

            model.addAttribute("msg", storeServerResponse.getMsg());
            return "storelogin";
        }

        model.addAttribute("msg", storeServerResponse.getMsg());
        return "error";

    }


    @GetMapping(value = "openStore/{id}")
    public String openStore(@PathVariable("id") Long id, Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("进入关店方法");
        System.out.println("请求路径为---------" + request.getRequestURI());
        ServerResponse<Store> serverResponse = storeService.openStore(id);
        session.setAttribute("storeUser", serverResponse.getData());
        System.out.println("关店了");
        model.addAttribute("msg", serverResponse.getMsg());
        return "dashboard";

/*        Long aLong = foodDao.selectStoreById(id);
        System.out.println("删除的食物ID为：------------"+id);
        ServerResponse<Foods> foodsResponse = foodsService.deleteFood(id);
        System.out.println(foodsResponse.getMsg());
        System.out.println("执行删除的店铺ID为--------------------"+aLong);
        if (foodsResponse.isSuccess()){
            System.out.println("删除成功");
            return "redirect:/foods/findMyAll?storeID="+String.valueOf(aLong);

        }

        return  "error";*/


    }





    @PostMapping("storeUpdate")
    public String updateStore(Model model, Store store, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException, ImgException {


        String filename = file.getOriginalFilename();

        System.out.println("商家图片名字-------" + filename);

        String photo_url = uploadservice.addHead(file);
        if (photo_url != null) {

            store.setPhoto_url(photo_url);
        }


        System.out.println("图片地址为---------" + photo_url);


        ServerResponse<Store> serverResponseresponse = storeService.updateStore(store);


        if (serverResponseresponse.isSuccess()) {

            model.addAttribute("msg", serverResponseresponse.getMsg());
            session.setAttribute("storeUser", storeDao.selectById(store.getId()));
            return "dashboard";
        }


        model.addAttribute("msg", serverResponseresponse.getMsg());
        return "error";


    }


    /*

     */


        /**
         * 验证码
         */
//        @PostMapping("sendSms")
//        public String sendSms(HttpServletRequest request,@RequestParam("memPhone") String memPhone,HttpSession session){
//            try {
//            JSONObject json = null;
//            //生成6位验证码
//            setNewcode();
//            String code = Integer.toString(getNewcode());
//            System.out.println("发送的验证码为："+code);
//            //发短信
//            CommonResponse response = AliSmsServiceUtil.send("15710696028", code);// TODO 填写你需要测试的手机号码
//            System.out.println("短信接口返回的数据----------------");
//            System.out.println(response.getData());
//
//            //发送短信
//            //ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "100689", "9517cb5e-783c-4906-8d00-69ef22b5e891");
//
//            //String result = client.send(mobile, "您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次！");
//            json = JSONObject.parseObject(result);
//            if (json.getIntValue("code") != 0) {//发送短信失败
//                return "fail";
//            }
//            //将验证码存到session中,同时存入创建时间
//            //以json存放，这里使用的是阿里的fastjson
//
//            json = new JSONObject();
//            json.put("verifyCode", verifyCode);
//            json.put("createTime", System.currentTimeMillis());
//            // 将认证码存入SESSION
//            request.getSession().setAttribute("verifyCode", json);
//            return "success";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

        public static int newcode;

        public static int getNewcode() {
        return newcode;
    }

        public static void setNewcode() { newcode = (int) (Math.random() * 9999) + 100;}
        @ResponseBody
        @GetMapping("sendSms")
        public boolean getCode(@RequestParam("phone") String phone, HttpSession httpSession){
            System.out.println("phone的值-----------"+phone);
            int i = storeDao.selectByPhone(phone);
            if (i==0){
                try {
                    JSONObject json = null;
                    //随机生成验证码
                    setNewcode();
                    String code =Integer.toString(getNewcode());
                    System.out.println("code========="+code);
                    //将验证码通过阿里云接口发送至手机
                    CommonResponse response = AliSmsServiceUtil.send(phone, code);

                    json=JSONObject.parseObject(String.valueOf(response.getData()));
                    System.out.println("json对象为---------------"+json);
                    System.out.println();
                    if (!json.getString("Code").equalsIgnoreCase("OK")){//发送短信失败
                        return  false;
                    }
                    //将验证码存到session中,同时存入创建时间
                    //以json存放，这里使用的是阿里的fastjson
                    json = new JSONObject();
                    json.put("phone",phone);
                    json.put("code",code);
                    json.put("createTime",System.currentTimeMillis());
                    // 将认证码存入SESSION
                    httpSession.setAttribute("code",code);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        //认证页面跳转
        @GetMapping("jumpcertificate")
        public String jumpcertificate(@RequestParam("storeID") Long store_id,Model model){

            License license = storeDao.findLicenseBySid(store_id);
            model.addAttribute("license",license);
            return "license";
        }

        //提交认证
        @PostMapping("certificate")
        public String Certificate(License license,@RequestParam("idfile") MultipartFile idfile,@RequestParam("lifile") MultipartFile lifile, Model model) throws IOException, ImgException {

            System.out.println("上传的license----------"+license.toString());
            if (license.getId_card()!=null) {
                if (idfile.getOriginalFilename()!=null){
                    String id_card = uploadservice.addHead(idfile);
                    license.setId_card(id_card);
                }
            }

            if (license.getLicense()!=null){

                if(lifile.getOriginalFilename()!=null){
                    String license_url=uploadservice.addHead(lifile);
                    license.setLicense(license_url);
                }
            }


            ServerResponse<String> serverResponse = storeService.StoreCertificateUpdate(license);
            if (serverResponse.isSuccess()){
                System.out.println("认证信息已成功提交");
                model.addAttribute("li_msg","认证信息已提交，等待审核！");
                return "dashboard";
            }

            model.addAttribute("error","认证信息提交失败，请重新提交！");
            return "license";

        }





}
