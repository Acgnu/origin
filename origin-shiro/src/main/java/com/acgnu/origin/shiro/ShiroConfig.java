package com.acgnu.origin.shiro;

import com.acgnu.origin.common.Constants;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.service.PrivilegeService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.HashMap;

@Configuration
public class ShiroConfig {
    @Value("${shiro.cache.use-redis}")
    private boolean useRedisCache;
    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 启用登陆加密
     * @return
     */
    @Bean
    public ShiroRealm getCustomerShiroRealm(){
        var myShiroRealm = new ShiroRealm();
        // 配置 加密 （在加密后，不配置的话会导致登陆密码失败）
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm, RedisHelper template){
        var securityManager = new DefaultWebSecurityManager();
        // 配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行，因为setRealm 和 setSessionManage都有方法初始化了cachemanager,看下源码就知道了
        if (useRedisCache) {
            securityManager.setCacheManager(cacheManager(template));
        }
        securityManager.setRealm(getCustomerShiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        var shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        var allPrivilege = privilegeService.findAll();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        var map = new HashMap<String, String>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        map.put("/user/signout","logout");
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访
//        map.put("/user/signin","anon");
        allPrivilege.stream().forEach(privilege -> map.put(privilege.getUri(), MessageFormat.format("perms[{0}]", privilege.getUri())));
//        allPrivilege.stream().forEach(privilege -> map.put(privilege.getUri(), "authc"));
//        anon  未认证可以访问
//        authc  认证后可以访问
//        perms 需要特定权限才能访问
//        roles 需要特定角色才能访问
//        user 需要特定用户才能访问
//        port  需要特定端口才能访问
//        reset  根据指定 HTTP 请求访问才能访问
//        map.put("/shiro/**","authc");
        shiroFilterFactoryBean.setLoginUrl("/shiro/authc");
        shiroFilterFactoryBean.setSuccessUrl("/success");
        shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置框架异常处理
     * 如果不用这个，当使用注解并跑出框架级异常（无权访问等）时，无法在业务层捕获，而且控制台会报错。
     * @return
     */
//    @Bean
//    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver(){
//        var simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//        var mappings = new Properties();
//        mappings.setProperty("UnauthorizedException", "/deny");
//        mappings.setProperty("DatabaseException", "/error");
//        simpleMappingExceptionResolver.setExceptionMappings(mappings);
//        simpleMappingExceptionResolver.setDefaultErrorView("/error");
//        simpleMappingExceptionResolver.setExceptionAttribute("ex");
//        return simpleMappingExceptionResolver;
//    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        var authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //同上
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    //使用自定义的缓存管理
    private ShiroRedisCacheManager cacheManager(RedisHelper redisHelper){
        return new ShiroRedisCacheManager(redisHelper);
    }

    /**
     * 配置使shiro调用登陆时对密码进行加盐后匹配
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        var hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        hashedCredentialsMatcher.setHashIterations(Constants.SHIRO_HASH_ITERATE);
        return hashedCredentialsMatcher;
    }
}
