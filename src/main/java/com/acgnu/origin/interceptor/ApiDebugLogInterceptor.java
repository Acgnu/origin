package com.acgnu.origin.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class ApiDebugLogInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("_requestTime", LocalDateTime.now());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //求情参数包含kv, 流, 文件
        //响应包含流, 文件
        //格式 requestID | 求情时间 | 接口名称 | 自定义的请求头| 参数{} | 参数body | 响应 {}
//        String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//        byte b[] = new byte[1024];
//
//        IOUtils.write(b, response.getWriter(), "UTF-8");
//        ByteArrayInputStream xxx = new ByteArrayInputStream(b);
//        System.out.println(IOUtils.toString(xxx, StandardCharsets.UTF_8));
//        StringBuilder content = new StringBuilder();
//        content.append("\n request time: ").append(request.getAttribute("_requestTime"))
//                .append("\n uri: ").append(request.getRequestURI())
//                .append("\n header: ").append(request.getHeader("response"))
//                .append("\n request parameter: ").append(JSON.toJSONString(request.getParameterMap()))
//                .append("\n request body: ").append(body)
//                .append("\n response body:").append("");
//        logger.info(content.toString());
    }
}
