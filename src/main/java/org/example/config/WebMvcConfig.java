package org.example.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.SendImgInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private SendImgInterceptor sendImgInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //log.info("开始注册拦截器........");
        registry.addInterceptor(sendImgInterceptor)
                .addPathPatterns("/**"); // 拦截以 "/" 开头的请求,可以添加多个
    }
}
