package com.acgnu.origin.controller;

import com.acgnu.origin.enums.BizReponse;
import com.acgnu.origin.pojo.LoginCredential;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.util.MessageHolder;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected MessageHolder messageHolder;

    protected <T> Result<T> success(T data){
        return new Result<>(BizReponse.SUCCESS, data, messageHolder);
    }

    protected Result success() {
        return new Result(BizReponse.SUCCESS, messageHolder);
    }

    protected Result fail(){
        return new Result(BizReponse.E_UNKNOW, messageHolder);
    }

    protected LoginCredential getLoginCredential(){
        return (LoginCredential) SecurityUtils.getSubject().getPrincipal();
    }
}
