package com.acgnu.origin.pojo;

import com.acgnu.origin.enums.ResultEnum;
import com.acgnu.origin.util.MessageHolder;
import lombok.Getter;
import lombok.Setter;

/**
 * 页面结果返回对象
 * @param <T>  任何对象
 */
@Getter
@Setter
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(MessageHolder messageHolder){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getLocalValue(messageHolder);
    }

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(T data, MessageHolder messageHolder){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getLocalValue(messageHolder);
        this.data = data;
    }

    public Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
