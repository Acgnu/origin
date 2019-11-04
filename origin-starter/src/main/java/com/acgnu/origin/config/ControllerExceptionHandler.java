package com.acgnu.origin.config;

import com.acgnu.origin.enums.BizReponse;
import com.acgnu.origin.exception.AccessException;
import com.acgnu.origin.exception.ArgException;
import com.acgnu.origin.exception.BizException;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.util.MessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常拦截Advice
 * 拦截Controller抛出的指定异常
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @Autowired
    private MessageHolder messageHolder;

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result handleBizException(Throwable e){
        log.error(e.getMessage(), e);
        return new Result(BizReponse.E_BUSSINESS, messageHolder);
    }

    @ExceptionHandler(ArgException.class)
    @ResponseBody
    public Result handleArgException(Throwable e){
        return new Result(BizReponse.E_REQ_ARG, messageHolder);
    }

    @ExceptionHandler(AccessException.class)
    @ResponseBody
    public Result handAccessExcption(Throwable e) {
        log.error(e.getMessage(), e);
        return new Result(BizReponse.E_REQ, messageHolder);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Result handleUnauthorized(Throwable throwable) {
        return new Result(BizReponse.E_REQ_UNAUTH, messageHolder);
    }
}
