package com.acgnu.origin.service.tencentapi.dns;

import com.acgnu.origin.tencentapi.TenCloudAnalytic;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenDnsService {
    @Autowired
    private TenCloudAnalytic tenCloudAnalytic;

    public JSONObject getDnsRecords(String domain, String subDomain, String recordType) {
        var result = tenCloudAnalytic.queryRecordList(domain, subDomain, recordType);

        return tenCloudAnalytic.analyseAndGetData(result, null);
    }


}
