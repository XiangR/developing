package com.joker.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiangrui on 2019-05-16.
 *
 * @author xiangrui
 * @date 2019-05-16
 */
public class UserDao implements IUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Override
    public Object save(User user) {
        LOGGER.info("模拟：保存用户！");
        return "success";
    }

    @Override
    public Object find() {
        LOGGER.info("模拟：查询用户");
        return "success";
    }
}
