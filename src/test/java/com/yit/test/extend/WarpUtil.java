package com.yit.test.extend;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * Created by xiangrui on 2017/6/1.
 */
public class WarpUtil {

    public static <T> T unWarpService(T service) throws Exception {
    if (AopUtils.isAopProxy(service) && service instanceof Advised) {
        Object target = ((Advised) service).getTargetSource().getTarget();
        return (T) target;
    }
    return service;
}

}
