package com.acgnu.origin.controller;

import com.acgnu.origin.service.tencentapi.dns.TenDnsService;
import com.acgnu.origin.tencentapi.TenCloudAnalytic;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ten/dns")
public class TenDnsController {
    @Autowired
    private TenDnsService tenDnsService;
//    @RequiresPermissions("/ten/dns/record/list")
    @PostMapping("/record/list")
    public JSONObject recordList(String domain, String subDomain, String recordType) {
        return tenDnsService.getDnsRecords(domain, subDomain, recordType);
    }

    @PostMapping("/record/add")
    public String addDnsRecord() {
        return "add";
    }

//    @RequiresPermissions("/ten/dns/record/del")
    @PostMapping("/record/del")
    public String delDnsRecord() {
        return "delete";
    }

    @PostMapping("/record/update")
    public String updateDnsRecord() {
        return "update";
    }
}
