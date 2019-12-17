package com.joker.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 可以用于一些不通过注入取得 spring bean 的情况
 * <p>
 * Created by xiangrui on 2019-06-13.
 *
 * @author xiangrui
 * @date 2019/06/13
 */
public class SpringContextUtils implements ApplicationContextAware {

    // Spring应用上下文环境

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法。设置上下文环境
     */
    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public synchronized static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) throws BeansException {
        return applicationContext.getBean(type);
    }

}
