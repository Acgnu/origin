package com.acgnu.origin.exception;

/**
 * 业务异常, 例如运行中缺少必要数据, 无法继续执行的异常
 */
public class BizException extends RuntimeException{
    public BizException(String msg) {
        super(msg);
    }
}
