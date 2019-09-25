package com.acgnu.origin.shiro;

import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.service.PrivilegeService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.HashMap;
import java.util.Properties;

@Configuration
public class ShiroConfig {
    @Autowired
    private PrivilegeService privilegeService;

    @Bean
    public ShiroRealm getCustomerShiroRealm(){
        return new ShiroRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm, RedisHelper template){
        var securityManager = new DefaultWebSecurityManager();
        // 配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行，因为setRealm 和 setSessionManage都有方法初始化了cachemanager,看下源码就知道了
        securityManager.setCacheManager(cacheManager(template));
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
        map.put("/user/signout","logout");
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访
        map.put("/user/signin","anon");
        allPrivilege.stream().forEach(privilege -> {
            map.put(privilege.getUri(),"authc");
        });
//        map.put("/shiro/**","authc");
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/success");
        shiroFilterFactoryBean.setUnauthorizedUrl("/deny");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置框架异常处理
     * 如果不用这个，当使用注解并跑出框架级异常（无权访问等）时，无法在业务层捕获，而且控制台会报错。
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver(){
        var simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        var mappings = new Properties();
        mappings.setProperty("UnauthorizedException", "/deny");
//        mappings.setProperty("DatabaseException", "/error");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
//        simpleMappingExceptionResolver.setDefaultErrorView("/error");
        simpleMappingExceptionResolver.setExceptionAttribute("ex");
        return simpleMappingExceptionResolver;
    }

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
}
