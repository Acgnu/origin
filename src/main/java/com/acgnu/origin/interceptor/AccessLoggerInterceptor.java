package com.acgnu.origin.interceptor;

import com.acgnu.origin.entity.AccessPvLog;
import com.acgnu.origin.entity.AccessUvLog;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.redis.RedisKeyConst;
import com.acgnu.origin.utils.RequestUtil;
import com.alibaba.fastjson.JSON;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pojo.IpAnalyseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class AccessLoggerInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisHelper redisHelper;

    private String[] excludeUris = new String[]{"/error"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            System.err.println(request.getHeader(headerNames.nextElement()));
//        }
        //错误页面不进行统计
        for (String excludeUri : excludeUris) {
            if (excludeUri.equals(request.getRequestURI())) {
                return;
            }
        }

        //获取请求UA
        UserAgentInfo userAgentInfo = RequestUtil.parseUserAgent(request);
        //获取请求IP
        String accessIp = RequestUtil.getClientIpAddress(request);

        //访问UV信息
        AccessUvLog accessUvLog = (AccessUvLog) redisHelper.get(RedisKeyConst.ACCESS_IP_PRE + accessIp);
        //访问PV信息
        AccessPvLog newAccessPv = new AccessPvLog(0L,       //默认初始化为0
                0L,
                request.getRequestURI(),
                userAgentInfo.getDeviceType(),
                userAgentInfo.getOsName(),
                userAgentInfo.getUaName(),
                1,
                LocalDateTime.now());

        //如果不是初次访问
        if (null != accessUvLog) {
            //更新UV信息
            accessUvLog.setTotalAccess(accessUvLog.getTotalAccess() + 1L);
            accessUvLog.setLastAccessTime(LocalDateTime.now());

            //更新PV信息
            Set<AccessPvLog> accessPvLogs = accessUvLog.getAccessPvLogs();
            //查询此IP下是否有此链接的访问记录
            boolean isMatch = false;
            for (AccessPvLog accessPvLog : accessPvLogs) {
                if (request.getRequestURI().equals(accessPvLog.getUri())) {
                    //更新可能发生改变的信息
                    accessPvLog.setDevice(userAgentInfo.getDeviceType());
                    accessPvLog.setOs(userAgentInfo.getOsName());
                    accessPvLog.setBroswer(userAgentInfo.getUaName());
                    //更新频次和时间
                    accessPvLog.setFrequency(accessPvLog.getFrequency() + 1);
                    accessPvLog.setUpdateTime(LocalDateTime.now());
                    isMatch = true;
                    break;
                }
            }
            //没有匹配到则添加新项
            if (!isMatch) {
                //记录访问PV信息
                newAccessPv.setUvid(accessUvLog.getId());
                accessPvLogs.add(newAccessPv);
            }
            redisHelper.set(RedisKeyConst.ACCESS_IP_PRE + accessIp, accessUvLog, 7776000);
            log.info("Access Log: {}", JSON.toJSONString(accessUvLog));
            return;
        }

        //解析IP地区
        IpAnalyseResult ipAreaInfo = RequestUtil.getIpAreaInfo(accessIp);

        //初次访问, 记录访问UV信息
        accessUvLog = new AccessUvLog(System.nanoTime(), //TODO: 模拟一个ID
                accessIp,
                ipAreaInfo.getProvince(),
                ipAreaInfo.getCity(),
                ipAreaInfo.getIsp(),
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                new HashSet<>());

        //记录访问PV信息
        newAccessPv.setUvid(accessUvLog.getId());
        accessUvLog.getAccessPvLogs().add(newAccessPv);

        //缓存记录
        redisHelper.set(RedisKeyConst.ACCESS_IP_PRE + accessIp, accessUvLog, 7776000);
        log.info("Access Log: {}", JSON.toJSONString(accessUvLog));
    }
}
