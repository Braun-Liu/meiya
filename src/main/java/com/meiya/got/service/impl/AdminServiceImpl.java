package com.meiya.got.service.impl;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.AdminDAO;
import com.meiya.got.po.Admin;
import com.meiya.got.service.IAdminService;
import com.meiya.got.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service//("iAdminService")
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public ServerResponse<Admin> login(String phone, String password) {
        int resultCount = adminDAO.checkUsername(phone);
        if(resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5(password);
        Admin admin  = adminDAO.selectLogin(phone,md5Password);
        if(admin == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        admin.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",admin);
    }

}
