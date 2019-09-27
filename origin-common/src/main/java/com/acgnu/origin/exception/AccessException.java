package com.acgnu.origin.exception;

/**
 * 访问异常, 例如权限不足, 请求地址, 方式有误等
 */
public class AccessException extends RuntimeException{
    public AccessException() {
        super();
    }

    public AccessException(String message) {
        super(message);
    }
}
