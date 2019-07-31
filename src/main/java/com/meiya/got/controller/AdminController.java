package com.meiya.got.controller;


import com.meiya.got.common.Const;
import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Admin;
import com.meiya.got.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @RequestMapping(value = "admainlogin",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Admin> login(String username, String password, HttpSession session){
        ServerResponse<Admin> response = iAdminService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
   @GetMapping("/success")
    public String tosuccess(){
        return "success";
    }

}
