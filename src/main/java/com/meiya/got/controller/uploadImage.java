package com.meiya.got.controller;


import com.meiya.got.service.uploadService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class uploadImage {
    @Autowired
    private uploadService uploadservice;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("upload")
    public String uploadPage(){
        return "emp/Testupload";
    }

    @RequestMapping(value = "headImgUpload", method = RequestMethod.POST)
    public String headImgUpload(Model model, @RequestParam("file") MultipartFile file) {
        logger.info("文件上传");

        System.out.println("来到controller---------------------");

        try {
            String uploadUrl = uploadservice.updateHead(file, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
            model.addAttribute("url",uploadUrl);
            model.addAttribute("success", true);
            model.addAttribute("errorCode", 0);
            model.addAttribute("Msg", "上传成功");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("url",null);
            model.addAttribute("success", false);
            model.addAttribute("errorCode", 1);
            model.addAttribute("Msg", "上传失败");
        }
        return "success";
    }

}
