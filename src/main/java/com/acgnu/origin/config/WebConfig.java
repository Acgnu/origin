package com.acgnu.origin.config;

import com.acgnu.origin.interceptor.ApiDebugLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ApiDebugLogInterceptor apiDebugLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiDebugLogInterceptor).addPathPatterns("/**");
//        .excludePathPatterns("/xxx");
    }
}
