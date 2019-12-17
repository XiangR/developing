package com.joker.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiangrui on 2019-05-16.
 * <p>
 * 测试代理类必须实现接口
 */
public class UserDao2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao2.class);

    public Object save(User user) {
        LOGGER.info("模拟：保存用户！");
        return "success";
    }

    public Object find() {
        LOGGER.info("模拟：查询用户");
        return "success";
    }
}
