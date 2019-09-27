package com.acgnu.origin.exception;

/**
 * 请求参数有误异常, 例如提交的参数中缺少必要字段
 */
public class ArgException extends RuntimeException{
    public ArgException() {
        super();
    }

    public ArgException(String message) {
        super(message);
    }
}
