package com.joker.proxy.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by xiangrui on 2019-09-27.
 *
 * @author xiangrui
 * @date 2019-09-27
 */
public class MyMethodInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMethodInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
            throws Throwable {

        // 获取当前执行的方法的方法名
        String methodName = method.getName();

        // 方法返回值
        Object result;
        if ("find".equals(methodName)) {
            // 直接调用目标对象方法
            result = methodProxy.invokeSuper(obj, args);
        } else {

            LOGGER.info("模拟：开启事务...");

            // 执行目标对象方法
            result = methodProxy.invokeSuper(obj, args);

            LOGGER.info("模拟：提交事务...");

        }
        return result;
    }
}
