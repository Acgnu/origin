package com.acgnu.origin.config;

import com.acgnu.origin.interceptor.AccessLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AccessLoggerInterceptor accessLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLoggerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/error")
        ;

        //为语言切换拦截器设置请求参数字段
        var localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("_lang");     //参数值例如 en_US  zh_CN
        registry.addInterceptor(localeInterceptor);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }


    /**
     * 默认国际化解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
