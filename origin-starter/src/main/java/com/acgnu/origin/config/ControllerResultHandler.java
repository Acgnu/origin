package com.acgnu.origin.config;

import com.acgnu.origin.enums.ResultEnum;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.util.MessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 对所有Controller的返回值进行拦截
 */
@RestControllerAdvice
public class ControllerResultHandler implements ResponseBodyAdvice {
    @Autowired
    private MessageHolder messageHolder;

    /**
     * 指定拦截的方法类型
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;        //拦截全部
    }

    /**
     * 对controller的返回值进行再封装
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        //如果返回值本身就是指定的标准返回类型, 则直接返回
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof String) {       //string类型不能直接丢入data, 会引起报错
            return body;
        }
        //不是的话则包装后返回, 通常失败了则会由异常拦截器处理, 所以这里返回值默认success即可
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getLocalValue(messageHolder), body);
    }
}
