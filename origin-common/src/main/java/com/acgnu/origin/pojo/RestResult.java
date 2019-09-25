package com.acgnu.origin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> {
    private int code;
    private String msg;
    private T data;
}