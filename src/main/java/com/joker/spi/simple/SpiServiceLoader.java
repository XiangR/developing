package com.joker.spi.simple;

import com.google.common.collect.Maps;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 一种优雅的实现 api 的方法
 * Created by xiangrui on 2019-06-25.
 *
 * @author xiangrui
 * @date 2019-06-25
 */
public class SpiServiceLoader {

    // 读取配置获取所有实现
    private static ServiceLoader<SpiService> spiLoader = ServiceLoader.load(SpiService.class);
    private static String defaultName = "";

    private static final Map<String, Class<?>> cachedClasses = Maps.newHashMap();

    /**
     * 使用 support 方法来取得实例
     */
    public static SpiService getExtensionBySupport(String name) {
        for (SpiService spiService : spiLoader) {
            if (spiService.isSupport(name)) {
                return spiService;
            }
        }
        return null;
    }

    public static SpiService getExtension(String name) {
        Map<String, Class<?>> extensionClasses = getExtensionClasses();
        Class<?> aClass = extensionClasses.get(name);
        try {
            Object o = aClass.newInstance();
            return (SpiService) o;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static SpiService getDefaultExtension() {
        loadDefaultName();
        return getExtension(defaultName);
    }

    /**
     * 初始化 bean default name
     */
    public static void loadDefaultName() {
        if (defaultName != null && defaultName.length() > 0) {
            return;
        }
        Annotation[] annotations = SpiService.class.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Resource) {
                Class<?> type = ((Resource) annotation).type();
                defaultName = type.getSimpleName();
            }
        }
    }

    private static Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses;
        if (classes.isEmpty()) {
            synchronized (cachedClasses) {
                classes = loadExtensionClasses();
                cachedClasses.putAll(classes);
            }
        }
        return classes;
    }

    private static Map<String, Class<?>> loadExtensionClasses() {
        Map<String, Class<?>> classes = Maps.newHashMap();
        for (SpiService spiService : spiLoader) {
            classes.put(spiService.getClass().getSimpleName(), spiService.getClass());
        }
        return classes;
    }

}
