package com.joker.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xiangrui on 2018/6/25.
 *
 * @author xiangrui
 * @date 2018/6/25
 */
@Aspect
@Component
public class ApplicationAspect {

    @Resource
    private ApplicationContext applicationContext;

    @Pointcut(value = "@annotation(com.yit.scm.aop.AddApplicationContext)")
    public void check() {
    }

    /**
     * 拿到参数后可以进行一些自己想要的操作
     */
    @Before("check()")
    public void addApplicationContext(JoinPoint joinPoint) {
        MethodInvocationProceedingJoinPoint proceedingJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        Object[] args = proceedingJoinPoint.getArgs();
        if (args == null) {
            return;
        }
        for (Object o : args) {
            // do something
        }
    }

}
