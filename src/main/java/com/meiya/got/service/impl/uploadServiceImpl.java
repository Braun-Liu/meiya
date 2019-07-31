package com.meiya.got.service.impl;

import com.meiya.got.dao.AdminDAO;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.exception.ImgException;
import com.meiya.got.service.uploadService;
import com.meiya.got.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class uploadServiceImpl implements uploadService {

//    @Autowired
//    private OSSClientUtil ossClientUtil;

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private FoodDao foodDao;


    public String updateHead(MultipartFile file, long foodId) throws IOException, ImgException {
        if (file == null || file.getSize() <= 0) {

            return null;
        }
        System.out.println("到这里了  要用阿里云了");
        System.out.println("文件准备好了————————"+file);


        String filename = file.getOriginalFilename();
        System.out.println(filename);

        try {

            if (file != null) {
                /*if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    //String uploadUrl = OSSClientUtil.upLoad(newFile);
                    String uploadUrl = OSSClientUtil.getFileUrl(file);
                    adminDAO.updateImgById(userId,uploadUrl);
                    return uploadUrl;
                }*/
                String uploadUrl = OSSClientUtil.getFileUrl(file);
                foodDao.updateImgById(foodId,uploadUrl);
                return uploadUrl;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }



//        System.out.println("到这里了  上传图片了  图片地址——————————"+url);
////        String imgUrl = ossClient.getImgUrl(name);
//        adminDAO.updateImgById(userId,url);
////        userDao.updateHead(userId, imgUrl);//只是本地上传使用的

        return null;
    }
    public String addHead(MultipartFile file) throws IOException, ImgException {
        if (file == null || file.getSize() <= 0) {
            return null;
        }
        System.out.println("到这里了  要用阿里云了");
        System.out.println("文件准备好了————————"+file);


        /*String filename = file.getOriginalFilename();
        System.out.println(filename);*/

        try {

            if (file != null) {
                /*if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    //String uploadUrl = OSSClientUtil.upLoad(newFile);
                    String uploadUrl = OSSClientUtil.getFileUrl(file);
                    adminDAO.updateImgById(userId,uploadUrl);
                    return uploadUrl;
                }*/
                String uploadUrl = OSSClientUtil.getFileUrl(file);
//                foodDao.updateImgById(uploadUrl);
                return uploadUrl;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }



//        System.out.println("到这里了  上传图片了  图片地址——————————"+url);
////        String imgUrl = ossClient.getImgUrl(name);
//        adminDAO.updateImgById(userId,url);
////        userDao.updateHead(userId, imgUrl);//只是本地上传使用的

        return null;
    }
}
