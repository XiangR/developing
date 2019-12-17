package com.yit.test.spring;

import com.joker.spring.api.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiangrui on 2019-09-25.
 *
 * @author xiangrui
 * @date 2019-09-25
 */
public class App {

    @Test
    public void start() {

        // 用我们的配置文件来启动一个 ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF.spring/application.xml");

        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        MessageService messageService = context.getBean(MessageService.class);

        String test = "测试";

        String message = messageService.getMessage(test);

        Assert.assertEquals(String.format("test-%s", test), message);
    }

}
