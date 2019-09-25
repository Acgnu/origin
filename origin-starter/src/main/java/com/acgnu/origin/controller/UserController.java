package com.acgnu.origin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/sigin")
    public String signIn(String uname, String upass) {
        var result = "success";
        var subject = SecurityUtils.getSubject();
        try {
            var usernamePasswordToken = new UsernamePasswordToken(uname, upass);
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            result = e.getLocalizedMessage();
            subject.logout();
        }
        return result;
    }

    @PostMapping("/signout")
    public String signOut() {
        var subject = SecurityUtils.getSubject();
        subject.logout();
        return "sign out";
    }
}
