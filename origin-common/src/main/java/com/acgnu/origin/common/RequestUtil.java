package com.acgnu.origin.common;

import com.acgnu.origin.pojo.IpAnalyseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

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
    public static IpAnalyseResult getIpAreaInfo(String ip, RestTemplate restTemplate) {
        JSONObject result = restTemplate.getForObject("http://api.online-service.vip/ip3?ip=" + ip, JSONObject.class);
        IpAnalyseResult analyseResult = JSON.toJavaObject(result.getJSONObject("data"), IpAnalyseResult.class);
        return Optional.ofNullable(analyseResult).orElseGet(IpAnalyseResult::new);
    }

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
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
            String userAgent = request.getHeader("User-Agent");
            userAgentInfo = uasParser.parse(userAgent);
        } catch (IOException e) {
            log.error("UA解析失败", e);
        }
        return userAgentInfo;
    }
}
