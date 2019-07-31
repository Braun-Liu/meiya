package com.meiya.got.Config;

import com.meiya.got.common.LoginHandlerInterceptor;
import com.meiya.got.conponent.MyLocaleResolver;
import com.meiya.got.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 通过@Bean注解，将我们定义的拦截器注册到Spring容器
     * @return
     */
    final String[] notLoginInterceptPaths = {"/static/**","/admin/login","/error/**","/login","/register"};

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    public LoginHandlerInterceptor loginHandlerInterceptor(){return new LoginHandlerInterceptor();}
//
//    /**
//     * 重写接口中的addInterceptors方法，添加自定义拦截器
//     * @param registry
//     */
//    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // 通过registry来注册拦截器，通过addPathPatterns来添加拦截路径
       registry.addInterceptor(this.loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","index.html","/login","/css/**","/img/**","/js/**");
       registry.addInterceptor(this.loginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/admin","/success","/registerPage","/register","/storeRegister","/storelogin","/webjars/**","/war/**","/asserts/**","index.html","/login","/sendSms");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }
    //所有的WebMvcConfigurerAdapter组件都会一起起作用
   /* @Bean //将组件注册在容器
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {


            //注册拦截器
           *//* @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //super.addInterceptors(registry);
                //静态资源；  *.css , *.js
                //SpringBoot已经做好了静态资源映射
//               registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                      .excludePathPatterns("/index.html","/","/storelogin","/static");


            }*//*


        };
        return adapter;
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        //排除静态资源拦截
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    @Bean
    public LocaleResolver localeResolver(){

        return new MyLocaleResolver();
    }
}
