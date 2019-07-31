package com.meiya.got.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆检查，
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {


    private Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      /*  Object user = request.getSession().getAttribute("storeUser");

        if(user == null){
            //未登陆，返回登陆页面
            request.setAttribute("msg","没有权限请先登陆");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else if (request.getParameter("storeID")!=null){
            Object storeId = request.getSession().getAttribute("storeID");
            System.out.println("登陆的商家id为-----------"+storeId);
            if (String.valueOf(request.getParameter("storeID"))!=storeId){
                return false;
            }
            已登陆，放行请求

        }*/



        logger.debug("preHandle method is now running!");
        logger.info("进入LoginInterceptor拦截器==============");
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        logger.info("basePath:" + basePath);
        logger.info("path:" + path);
        logger.info("userkey:"+request.getSession().getAttribute("userkey"));
        if(request.getSession().getAttribute("storeUser") == null){

            logger.info("尚未登录，跳转到登录界面");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }else if(request.getParameter("storeID")!=null){
            Object storeId = request.getSession().getAttribute("storeID");
            System.out.println("登陆的商家id为-----------"+storeId);
            if (!request.getParameter("storeID").equals(String.valueOf(storeId))){
                System.out.println(request.getParameter("storeID"));
                System.out.println("id不同  无法访问");
                return false;
            }
        }
        logger.info("已登录，放行！");


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
