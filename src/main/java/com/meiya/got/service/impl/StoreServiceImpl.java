package com.meiya.got.service.impl;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.dao.StoreDAO;
import com.meiya.got.po.Category;
import com.meiya.got.po.License;
import com.meiya.got.po.Store;
import com.meiya.got.service.StoreService;
import com.meiya.got.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDAO storeDao;
    @Autowired
    private FoodDao foodDao;

    @Override
    @Transactional
    public ServerResponse<Store> login(String phone, String password) {



        int resultCount=storeDao.selectByPhone(phone);



        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");

        }
        Store rightstore = storeDao.findByPhone(phone);

        String md5Password = MD5Util.MD5(password+rightstore.getSalt());
        Store store=storeDao.selectLogin(phone, md5Password);


        if(store==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        store.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登录成功",store);
    }

    @Override
    @Transactional
    public ServerResponse<Store> Register(String storename, String phone, String password,  String address, int cate_id,String description,  String photo_url) {

        int resultCount=storeDao.selectByPhone(phone);
        if(resultCount==1){
            return ServerResponse.createByErrorMessage("手机号已经被注册");
        }

        if(StringUtils.isBlank(storename)){
            return ServerResponse.createByErrorMessage("店名不能为空");
        }

        if(StringUtils.isBlank(password)){
            return ServerResponse.createByErrorMessage("密码不能为空");
        }

        Store store=storeDao.selectByName(storename);

        if(store!=null){
            return ServerResponse.createByErrorMessage("店名已经被注册");
        }

        store=new Store();
        store.setStorename(storename);
        store.setSalt(UUID.randomUUID().toString().substring(0,5));
        if(photo_url==null){

            String photoUrl=String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
            store.setPhoto_url(photoUrl);
        }
        store.setPhoto_url(photo_url);
        store.setPassword(MD5Util.MD5(password+store.getSalt()));
        store.setAddress(address);
        store.setPhone(phone);
        store.setDescription(description);
        store.setCategory_id(cate_id);
        store.setOperational(0);
        store.setSales(0);

        store.setStatus(0);
        Date time= new java.sql.Date(new java.util.Date().getTime());
        store.setCreate_time(time);
        store.setUpdate_time(time);
        store.setUpdate_ip(store.getAddress());
        storeDao.addStore(store);
        Long store_id = storeDao.selectIdByPhone(phone);

        License license=new License();
        license.setStatus(0);
        license.setStore_id(store_id);
        storeDao.addlicense(license);
        return ServerResponse.createBySuccess("注册成功",store);
    }

    @Override
    @Transactional
    public ServerResponse<Store> updateStore(Store store) {
        Date time= new java.sql.Date(new java.util.Date().getTime());

        int i=storeDao.selectByPhone(store.getPhone());
        if (i==0){
            return ServerResponse.createByErrorMessage("商家不存在，更新失败");
        }
        store.setStatus(0);
        if(store.getPassword()!=null){
            storeDao.updatePassword(store);
        }

        storeDao.updateAddress(store);
        storeDao.updateDescription(store);
        storeDao.updateName(store);
        storeDao.updatePhone(store);
        storeDao.updatePhoto_url(store);
        storeDao.updateStatus(store);
        store.setUpdate_time(time);
        storeDao.updateUpdate_time(store);




        return ServerResponse.createBySuccess("商户信息更新成功,等待审核",store);
    }

    @Override
    @Transactional
    public ServerResponse<Store> deleteStore(String phone) {

        int i=storeDao.selectByPhone(phone);
        if(i==0){
            return ServerResponse.createByErrorMessage("商家不存在");
        }

        Long store_id=storeDao.findIdByPhone(phone);
        foodDao.deleteByStoreId(store_id);
        storeDao.deleteStore(phone);

        return ServerResponse.createBySuccessMessage("商家注销成功");
    }

    public ServerResponse<Store> openStore(Long id){
        Store store = storeDao.selectById(id);
        System.out.println("store的属性"+store);
        if(store.getOperational()==1){
            store.setOperational(0);
            storeDao.updateOperational(store);
        }else {
            store.setOperational(1);
            storeDao.updateOperational(store);
        }
        store = storeDao.selectById(id);
        return  ServerResponse.createBySuccess("店铺营业状态改变",store);

    }




    /**
     * 商家认证信息添加
     * @param license
     * @return
     */
    @Override
    public ServerResponse<String> StoreCertificate(License license) {

       Integer i= storeDao.addlicense(license);
       if (i==1){

       }




        return null;
    }


    /**
     * 商家认证信息更新
     * @param license
     * @return
     */
    @Override
    public ServerResponse<String> StoreCertificateUpdate(License license) {
        Date time= new java.sql.Date(new java.util.Date().getTime());

        license.setUpdate_time(time);
        license.setStatus(1);
        Long sid=license.getStore_id();

        System.out.println("要保存的license信息----------"+license.toString());
        try {

            storeDao.updatelicense(license);


           return ServerResponse.createBySuccess("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }



    }


    @Override
    public List<Category> findAllStoreCate() {

        return  storeDao.findAllStoreCate();


    }


}
