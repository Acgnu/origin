package com.acgnu.origin.common;

import com.acgnu.origin.pojo.IPbaseInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Slf4j
public class RequestUtil {
    private static UASparser uasParser;

    static {
        try {
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
//            uasParser = new UASparser();
        } catch (Exception e) {
            log.error("uasParser初始化失败", e);
        }
    }

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};


    /**
     * 根据IP获取归属地以及运行商信息
     * @param ip
     * @return
     */
    public static IPbaseInfo getIpAreaInfo(String ip, RestTemplate restTemplate) {
        var result = restTemplate.getForObject("http://api.online-service.vip/ip3?ip=" + ip, JSONObject.class);
        var analyseResult = JSON.toJavaObject(result.getJSONObject("data"), IPbaseInfo.class);
        return Optional.ofNullable(analyseResult).orElseGet(IPbaseInfo::new);
    }

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            var ip = request.getHeader(header);
            if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 解析user agent
     * @param request
     * @return UserAgentInfo
     */
    public static UserAgentInfo parseUserAgent(HttpServletRequest request) {
        UserAgentInfo userAgentInfo = null;
        try {
            var userAgent = request.getHeader("User-Agent");
            userAgentInfo = uasParser.parse(userAgent);
        } catch (Exception e) {
            log.error("UA解析失败", e);
        }
        return userAgentInfo;
    }


    /**
     * HTTP协议POST请求添加参数的封装方法
     */
    public static String concatQueryString(TreeMap<String, String> paramsMap) {
        if (null == paramsMap || paramsMap.isEmpty()) {
            return "";
        }
        var param = new StringBuilder();
        for (var e : paramsMap.entrySet()) {
            param.append("&").append(e.getKey().strip()).append("=").append(e.getValue().strip());
        }
        return param.deleteCharAt(0).toString();
    }
}
