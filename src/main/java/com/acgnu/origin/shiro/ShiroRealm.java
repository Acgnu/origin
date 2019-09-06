package com.acgnu.origin.shiro;

import com.acgnu.origin.model.User;
import com.acgnu.origin.model.Permission;
import com.acgnu.origin.model.Role;
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

public class ShiroRealm extends AuthorizingRealm{
    //数据查询
    @Autowired
    private SimpleService baseDbService;

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
        User admin = baseDbService.findVirtulUser(name);

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : admin.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getName());
            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPremission());
            }
        }
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
        if (token.getPrincipal() == null) {
            return null;
        }

        //获取用户
        String name = token.getPrincipal().toString();
        User admin = baseDbService.findVirtulUser(name);
        if (null == admin) {
            return null;
        }else{
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, admin.getUpass(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
