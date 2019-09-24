package com.acgnu.origin.pojo;

import lombok.Data;

@Data
public class IpAnalyseResult {
    private String country;
    private String province;
    private String city;
    private String isp;
}
