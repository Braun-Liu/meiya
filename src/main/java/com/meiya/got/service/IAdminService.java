package com.meiya.got.service;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Admin;
import com.meiya.got.po.Store;
import org.springframework.stereotype.Service;

@Service
public interface IAdminService {

    ServerResponse<Admin> login(String username, String password);


}
