package com.acgnu.origin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {
    @Value("${origin.config.creator}")
    private String creator;

}
