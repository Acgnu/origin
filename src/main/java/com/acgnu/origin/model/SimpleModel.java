package com.acgnu.origin.model;

import java.io.Serializable;

public class SimpleModel implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String c_content;
    private String c_flag_msg;
    private Byte c_flag;
    private String c_addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getC_flag_msg() {
        return c_flag_msg;
    }

    public void setC_flag_msg(String c_flag_msg) {
        this.c_flag_msg = c_flag_msg;
    }

    public Byte getC_flag() {
        return c_flag;
    }

    public void setC_flag(Byte c_flag) {
        this.c_flag = c_flag;
    }

    public String getC_addtime() {
        return c_addtime;
    }

    public void setC_addtime(String c_addtime) {
        this.c_addtime = c_addtime;
    }
}
