package com.joker.spring.service;

import com.joker.spring.api.MessageService;
import com.joker.spring.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xiangrui on 2019-09-25.
 *
 * @author xiangrui
 * @date 2019-09-25
 */
@Component
public class UserServiceImpl implements UserService, InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private MessageService messageService;

    // region spring

    /**
     * 初始化的节点 {@link org.springframework.beans.factory.config.ConfigurableListableBeanFactory#preInstantiateSingletons()}
     */
    @Override
    public void afterPropertiesSet() {

        LOGGER.info("init-UserServiceImpl-afterPropertiesSet-message:{}", messageService);
        LOGGER.info("afterPropertiesSet-userNotNull:{}", messageService.checkUserNotNull());

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();

        LOGGER.info("onApplicationEvent-userNotNull:{}", messageService.checkUserNotNull());
    }

    // endregion spring

    // region public

    @Override
    public String getUser() {
        return "hello world";
    }

    @Override
    public String getUser(String msg) {
        return String.format("test-%s", msg);
    }

    // endregion public
}