package com.joker.spring.service;

import com.joker.spring.api.MessageService;
import com.joker.spring.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xiangrui on 2019-09-25.
 *
 * @author xiangrui
 * @date 2019-09-25
 */
@Component
public class MessageServiceImpl implements MessageService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private UserService userService;

    // region spring

    /**
     * 初始化的节点 {@link org.springframework.beans.factory.config.ConfigurableListableBeanFactory#preInstantiateSingletons()}
     */
    @Override
    public void afterPropertiesSet() {

        LOGGER.info("init-MessageServiceImpl-afterPropertiesSet-user:{}", userService);
//        LOGGER.info("userNotNull:{}", checkUserNotNull());
    }

    public void initMethod() {

        LOGGER.info("initMethod-userNotNull:{}", checkUserNotNull());
    }

    // endregion spring

    // region public

    @Override
    public String getMessage() {
        return "hello world";
    }

    @Override
    public String getMessage(String msg) {
        return String.format("test-%s", msg);
    }

    @Override
    public boolean checkUserNotNull() {
        return userService != null;
    }

    // endregion public
}