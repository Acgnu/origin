package com.acgnu.origin.shiro;

import com.acgnu.origin.pojo.LoginCredential;
import com.acgnu.origin.service.UserService;
import com.acgnu.origin.util.MessageHolder;
import net.bytebuddy.utility.RandomString;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm{
    //数据查询
    @Autowired
    private UserService userService;
    @Autowired
    private MessageHolder messageHolder;

    /**
     * 获取角色和权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录的用户名
        var loginCredential = (LoginCredential) principals.getPrimaryPrincipal();
        //查询
        var admin = userService.findUserByUname(loginCredential.getUname());

        //添加角色和权限
        var simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        admin.getRoles().forEach(role -> {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            role.getPrivileges().forEach(privilege -> simpleAuthorizationInfo.addStringPermission(privilege.getUri()));
        });

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //用户登陆校验
        var uname = token.getPrincipal().toString();
        var user = userService.findUserByUname(uname);

        if (null == user) {
            throw new UnknownAccountException(messageHolder.lGet("user.login.account-error"));
        }

        if (user.isLocked()) {
            throw new LockedAccountException(messageHolder.lGet("user.login.account-locked"));
        }

        var loginCredential = new LoginCredential(user.getId(), user.getUname(), user.getNick(), RandomString.make(16));
        var credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        //调用shiro登陆, 如果凭证匹配则将登陆凭证信息存入subject中, 不匹配则抛异常
        return new SimpleAuthenticationInfo(loginCredential, user.getUpass(), credentialsSalt, getName());
    }
}
