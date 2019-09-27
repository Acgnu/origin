package com.acgnu.origin.controller;

import com.acgnu.origin.enums.ResultEnum;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.util.MessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 全局错误处理
 */
@Slf4j
@RestController
public class ExtErrorController implements ErrorController {
    @Autowired
    private MessageHolder messageHolder;
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public Result handleError(HttpServletRequest request) {
        var requestAttributes =  new ServletWebRequest(request);
        var errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        var statusCode = Integer.parseInt(errorAttributes.get("status").toString());
        //请求类型的错误, 展示具体请求状态以及错误信息
        if (statusCode < 500) {
            var errorMsg = MessageFormat.format("{0} ({1}) -> {2} ",
                    messageHolder.lGet("exception.access.invalid"), statusCode, errorAttributes.get("message"));
            return new Result(ResultEnum.E_REQ_ARG.getCode(), errorMsg);
        } else {
            //系统内部错误不展示具体报错内容
            return new Result(ResultEnum.E_UNKNOW.getCode(), ResultEnum.E_UNKNOW.getLocalValue(messageHolder));
        }
    }

    /**
     * 没有访问权限时shiro的跳转地址
     * @return
     */
    @RequestMapping("/shiro/unauthorized")
    public Result deny(){
        return new Result(ResultEnum.E_REQ_UNAUTH.getCode(), messageHolder.lGet("eurm.result.error-unauth"));
    }

    /**
     * 没有登陆时shiro的跳转地址
     * @return
     */
    @RequestMapping("/shiro/authc")
    public Result authc() {
        return new Result(ResultEnum.E_REQ_UNAUTH.getCode(), messageHolder.lGet("eurm.result.error-need-signin"));
    }

    /**
     * 返回自定义的错误地址
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
