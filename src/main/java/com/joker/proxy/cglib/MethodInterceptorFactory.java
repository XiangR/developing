package com.joker.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangrui on 2019-09-27.
 *
 * @author xiangrui
 * @date 2019-09-27
 */
public class MethodInterceptorFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodInterceptorFactory.class);

    private static final Map<Class, Object> cacheProxy = new HashMap<>();

    public <T> T getProxy(Class superclass) {
        Object o = cacheProxy.get(superclass);
        if (o == null) {
            o = initProxy(superclass);
            cacheProxy.put(superclass, o);
        }
        return (T) o;
    }

    public <T> T initProxy(Class superclass) {
        //获取Enhancer 对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass); // 设置回调方法
        enhancer.setCallback(new InnerMethodInterceptor());
        return (T) enhancer.create();
    }

    class InnerMethodInterceptor implements MethodInterceptor {

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
}
