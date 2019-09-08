package com.acgnu.origin.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerLogAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.acgnu.origin.controller.*.*(..))")
    public void debugLog(){ }

    //前置通知
    @Before("debugLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();

        // 获取url
        logger.info("\n ip: {}\n uri: {}\n header: {}\n invoke: {} -> {}\n params: {}\n args: {}",
                request.getRemoteAddr(),
                request.getRequestURI(),
                request.getHeader("_customer"),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                request.getParameterMap(),
                joinPoint.getArgs());
    }

    @AfterReturning(returning = "object", pointcut = "debugLog()")
    public void doAfterReturning(Object object){
        logger.info("\n response: {}", JSON.toJSONString(object));
    }

    //后置异常通知
    @AfterThrowing("debugLog()")
    public void doThrow(){

    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("debugLog()")
    public void doAfter(){

    }

    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("debugLog()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
//        Object o = null;
//        Object o = proceedingJoinPoint.proceed();
//        return o;
//    }
}
