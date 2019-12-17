package com.joker.proxy.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiangrui on 2019-05-16.
 *
 * @author xiangrui
 * @date 2019-05-16
 */
public class ProxyFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyFactory.class);

    // 接收一个目标对象
    private T target;

    @SuppressWarnings("unchecked")
    public ProxyFactory(T target) {
        this.target = target;
    }

    // 返回对目标对象(target)代理后的对象(proxy)
    @SuppressWarnings("unchecked")
    public T getProxyInstance() {
        Object o = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 目标对象使用的类加载器
                target.getClass().getInterfaces(),   // 目标对象实现的所有接口
                new InvocationHandler() {            // 执行代理对象方法时候触发
                    @Override
                    public Object invoke(Object proxy1, Method method, Object[] args)
                            throws Throwable {

                        // 获取当前执行的方法的方法名
                        String methodName = method.getName();
                        // 方法返回值
                        Object result;
                        if ("find".equals(methodName)) {
                            // 直接调用目标对象方法
                            result = method.invoke(target, args);
                        } else {

                            LOGGER.info("模拟：开启事务...");

                            // 执行目标对象方法
                            result = method.invoke(target, args);

                            LOGGER.info("模拟：提交事务...");

                        }
                        return result;
                    }
                }
        );
        return (T) o;
    }
}
