package com.meiya.got.service;

import com.meiya.got.common.ServerResponse;
import com.meiya.got.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface AccountService {

    ServerResponse<String> createAccount(Long sid);

    ServerResponse<String> openUpAccount(Long sid);

    //ServerResponse<Account> updateIncreaseAccount(Long Aid, Integer );



}
