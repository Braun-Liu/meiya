package com.meiya.got.interceptor;

import com.meiya.got.po.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.debug("preHandle method is now running!");
       /* Object storePhone = request.getSession().getAttribute("phone");
        Object storeName = request.getSession().getAttribute("storeName");

        Object storeID = request.getSession().getAttribute("storeID");
        if (request.getParameter("storeID")!=String.valueOf(storeID) ){
            return false;
        }
        String userName = (String) storeName;
        System.out.println("当前用户已登录，登录的用户名为： " + storeName);*/
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.debug("postHandle method is now running!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       // logger.debug("afterCompletion method is now running!");
    }




}
