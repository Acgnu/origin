package com.acgnu.origin.config;

import com.acgnu.origin.common.Constants;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 控制器拦截器
 * 拦截所有controller的方法执行
 * 截取前后参数作为debug日志
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {
    private String REQUEST_ID_KEY = "_requestId";
    private HttpServletRequest request;

    @Pointcut("execution(public * com.acgnu.origin.controller.*.*(..))")
    public void debugLog(){ }

    //前置通知
    @Before("debugLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        request=attributes.getRequest();

        // 记录请求参数
        String requestId = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID_KEY, requestId);
        log.debug("\n reqId: {}\n ip: {}\n uri: {}\n header: {}\n invoke: {} -> {}\n params: {}\n args: {}",
                request.getAttribute(REQUEST_ID_KEY),
                request.getRemoteAddr(),
                request.getRequestURI(),
                request.getHeader(Constants.KEY_REQUEST_HEADER),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                request.getParameterMap(),
                joinPoint.getArgs());
    }

    @AfterReturning(returning = "object", pointcut = "debugLog()")
    public void doAfterReturning(Object object){
        //记录响应参数
        log.debug("\n reqId: {}\n response: {}", request.getAttribute(REQUEST_ID_KEY), JSON.toJSONString(object));
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
