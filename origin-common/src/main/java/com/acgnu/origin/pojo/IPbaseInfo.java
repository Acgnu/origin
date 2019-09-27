package com.acgnu.origin.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * ip归属地基本信息
 */
@Getter
@Setter
public class IPbaseInfo {
    private String country;
    private String province;
    private String city;
    private String isp;
}
