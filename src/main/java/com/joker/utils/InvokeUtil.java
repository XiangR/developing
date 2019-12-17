package com.joker.utils;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Objects;

/**
 * Created by xiangrui on 2019-07-08.
 *
 * @author xiangrui
 * @date 2019-07-08
 */
public class InvokeUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        InvokeUtil.applicationContext = applicationContext;
    }

    public static <R> List<R> invokeList(String methodPath, String data) {
        Object o = invoke0(methodPath, data);
        return (List<R>) o;
    }

    public static <R> R invoke(String methodPath, String data) {
        Object o = invoke0(methodPath, data);
        return (R) o;
    }

    private static Object invoke0(String methodPath, String data) {
        ApplicationConfig applicationConfig = applicationContext.getBean(ApplicationConfig.class);
        RegistryConfig registryConfig = applicationContext.getBean(RegistryConfig.class);

        if (StringUtils.isBlank(methodPath)) {
            return null;
        }
        if (!methodPath.contains("#")) {
            return null;
        }
        String[] split = methodPath.split("#");
        if (split.length != 2) {
            return null;
        }
        String interfaceName = split[0];
        String methodName = split[1];
        if (StringUtils.isBlank(interfaceName) || StringUtils.isBlank(methodName)) {
            return null;
        }
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface(interfaceName);
        reference.setGeneric(true); // 声明为泛化接口
        reference.setCheck(false); // 不要检测
        reference.setVersion("LATEST");// 版本最新
        reference.setTimeout(1000 * 60 * 60 * 4); // 超时时间（4小时）

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        String[] parameterType = {};
        Object[] parameterValue = {};
        if (Objects.nonNull(data)) {
            parameterType = new String[]{"java.lang.String"};
            parameterValue = new Object[]{data};
        }
        return genericService.$invoke(methodName, parameterType, parameterValue);
    }
}
