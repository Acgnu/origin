package com.acgnu.origin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @Value("${origin.config.creator}")
    private String creator;

    @RequestMapping(value = "/")
    public String demoView(Model model){
        model.addAttribute("creator", creator);
        return "demoview";
    }
}
