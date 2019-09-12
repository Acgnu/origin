package com.acgnu.origin.interceptor;

import com.acgnu.origin.model.AccessLog;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.service.SimpleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class AccessLoggerInterceptor implements HandlerInterceptor {
    @Autowired
    private SimpleService simpleService;
    @Autowired
    private RedisHelper redisHelper;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserAgentInfo userAgentInfo = parseUserAgent(request.getHeader("User-Agent"));
        String accessIp = getRealRemoteIp(request);
        String accessHash = getAccessHash(accessIp, request);

        AccessLog accessLog = (AccessLog) redisHelper.get(accessHash);
        if (null != accessLog) {
            accessLog.setFrequency(accessLog.getFrequency() + 1L);
            accessLog.setLastAccessTime(LocalDateTime.now());
            redisHelper.set(accessHash, accessLog);
            logger.info("Access Log: {}", JSON.toJSONString(accessLog));
            return;
        }

        accessLog = new AccessLog(getRealRemoteIp(request),
                request.getRequestURI(),
                userAgentInfo.getDeviceType(),
                userAgentInfo.getOsName(),
                userAgentInfo.getUaName(),
                0L,
                LocalDateTime.now(),
                LocalDateTime.now());

        JSONObject ipAreaJson = getIpAreaInfo(accessLog.getIp());
        accessLog.setProvince(ipAreaJson.getString("province"));
        accessLog.setCity(ipAreaJson.getString("city"));
        accessLog.setOperator(ipAreaJson.getString("operator"));
        accessLog.setLogHash(accessHash);
        redisHelper.set(accessHash, accessLog);
        logger.info("Access Log: {}", JSON.toJSONString(accessLog));
//        System.out.println(JSON.toJSONString(accessLog));
        //{"broswer":"75.0.3770.80","city":"city","device":"Personal computer","firstAccessTime":"2019-09-12T18:24:50.183076800","frequency":0,"id":0,"ip":"0:0:0:0:0:0:0:1","lastAccessTime":"2019-09-12T18:24:50.183076800","logHash":"","operator":"operator","os":"Microsoft Corporation.","province":"province","uri":"/"}
//        System.out.println(JSON.toJSONString(userAgentInfo));
        //{"browserVersionInfo":"75.0.3770.80","deviceIcon":"desktop.png","deviceInfoUrl":"http://user-agent-string.info/list-of-ua/device-detail?device=Personal computer","deviceType":"Personal computer","osCompany":"Microsoft Corporation.","osCompanyUrl":"http://www.microsoft.com/","osFamily":"Windows","osIcon":"windows.png","osName":"Windows","osUrl":"http://en.wikipedia.org/wiki/Windows","robot":false,"type":"Browser","uaCompany":"Google Inc.","uaCompanyUrl":"http://www.google.com/","uaFamily":"Chrome","uaIcon":"chrome.png","uaInfoUrl":"http://user-agent-string.info/list-of-ua/browser-detail?browser=Chrome","uaName":"Chrome 75.0.3770.80","uaUrl":"http://www.google.com/chrome"}
    }

    public JSONObject getIpAreaInfo(String ip){
        //TODO analyse ip
        return new JSONObject();
    }

    public String getAccessHash(String ip, HttpServletRequest request){
        String toHash = new StringBuilder().append(ip).append(request.getRequestURI()).toString();
        return toHash;
    }

    public String getRealRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public UserAgentInfo parseUserAgent(String userAgent){
        UserAgentInfo userAgentInfo = null;
        try {
            UASparser uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
            userAgentInfo = uasParser.parse(userAgent);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userAgentInfo;
    }
}
