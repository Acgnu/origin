package com.acgnu.origin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 国际化配置文件的信息读取工具类
 */
@Component
public class MessageHolder {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    /**
     * 根据request读取国际化文本
     * @param key 读取的key
     * @return 国际化文本
     */
    public String rGet(String key) {
        return messageSource.getMessage(key, null, RequestContextUtils.getLocale(request));
    }

    /**
     * 根据系统配置读取国际化文本
     * @param key 读取的key
     * @return 国际化文本
     */
    public String lGet(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
