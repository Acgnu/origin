package com.acgnu.origin.pojo;

import com.acgnu.origin.enums.BizReponse;
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

    public Result(BizReponse resultEnum, MessageHolder messageHolder){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getLocalValue(messageHolder);
    }

    public Result(BizReponse resultEnum, T data, MessageHolder messageHolder){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getLocalValue(messageHolder);
        this.data = data;
    }
}
