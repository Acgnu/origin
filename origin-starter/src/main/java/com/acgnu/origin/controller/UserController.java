package com.acgnu.origin.controller;

import com.acgnu.origin.entity.User;
import com.acgnu.origin.enums.ResultEnum;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public User signUp(User user) {
        return userService.addUser(user, getLoginCredential());
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/update")
    public String update(User user){
        userService.updateById(user);
        return "update";
    }

    /**
     * 登陆
     * @param uname
     * @param upass
     * @return
     */
    @PostMapping("/sigin")
    public Result signIn(String uname, String upass) {
        try {
            var subject = SecurityUtils.getSubject();
            var usernamePasswordToken = new UsernamePasswordToken(uname, upass);
            subject.login(usernamePasswordToken);
            return success(subject.getPrincipal());
        } catch (UnknownAccountException | LockedAccountException e) {
            return new Result(ResultEnum.E_REQ_ARG.getCode(), e.getMessage());
        } catch (AuthenticationException e) {
            return new Result(ResultEnum.E_REQ_ARG.getCode(), messageHolder.lGet("user.login.account-error"));
        }
    }

    /**
     * 注销
     * @return
     */
    @PostMapping("/signout")
    public Result signOut() {
        var subject = SecurityUtils.getSubject();
        subject.logout();
        return success();
    }
}
