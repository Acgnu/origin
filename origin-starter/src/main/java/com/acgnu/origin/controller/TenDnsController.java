package com.acgnu.origin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ten/dns")
public class TenDnsController {
    @PostMapping("/record/list")
    public String recordList() {
        return "recordList";
    }

    @PostMapping("/record/add")
    public String addDnsRecord() {
        return "add";
    }

    @PostMapping("/record/del")
    public String delDnsRecord() {
        return "delete";
    }

    @PostMapping("/record/update")
    public String updateDnsRecord() {
        return "update";
    }
}
