package com.acgnu.origin.notes;

import com.alibaba.fastjson.JSON;

/**
 * 执行结果对象, 用于接口数据返回
 * @author Administrator
 */
public class Result {
	public static final int SUCCESS = 0;
	public static final int PARAM_ERROR = -1;	//参数异常
	public static final int EXCEPTION = -2;	//系统异常
	public static final int UNKNOW_ERROR = -3;	//未知异常
	private Integer code;
	private String msg;
	
	public Result(){
		this.code = UNKNOW_ERROR;
		this.msg = "Unknow error";
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setSUccess(){
		this.code = SUCCESS;
		this.msg = "";
	}
	
	public void setError(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public String toString(){
		return JSON.toJSONString(this);
	}
}
