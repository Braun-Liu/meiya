package com.meiya.got.service;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Category;
import com.meiya.got.po.License;
import com.meiya.got.po.Store;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface StoreService {

    ServerResponse<Store> login(String phone, String password);

    ServerResponse<Store> Register(String name, String  phone, String password,  String address,int cate_id, String description, String photo_url );

    //更新商户
    ServerResponse<Store> updateStore(Store store);

    //删除商户
    ServerResponse<Store> deleteStore(String phone);

    List<Category> findAllStoreCate();

    ServerResponse<Store> openStore(Long id);

    //认证商户
    ServerResponse<String> StoreCertificate(License license);
    ServerResponse<String> StoreCertificateUpdate(License license);

}
