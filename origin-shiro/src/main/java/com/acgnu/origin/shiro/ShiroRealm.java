package com.acgnu.origin.shiro;

import com.acgnu.origin.entity.Accesser;
import com.acgnu.origin.service.SimpleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

public class ShiroRealm extends AuthorizingRealm{
    //数据查询
    @Autowired
    private SimpleService simpleService;

    /**
     * 获取角色和权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录的用户名
        String name = (String) principals.getPrimaryPrincipal();
        //查询
        Accesser admin = simpleService.findUserByUname(name);

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
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
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (ObjectUtils.isEmpty(token.getPrincipal())) {
            return null;
        }

        //获取用户
        String name = token.getPrincipal().toString();
        Accesser admin = simpleService.findUserByUname(name);
        Optional<Accesser> optional = Optional.ofNullable(admin);
        return optional.isPresent() ? new SimpleAuthenticationInfo(name, optional.get().getUpass(), getName()) : null;
    }
}
