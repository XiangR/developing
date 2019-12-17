package com.joker.spi.simple;

/**
 * Created by xiangrui on 2019-06-25.
 *
 * @author xiangrui
 * @date 2019-06-25
 */
public class SpiServiceBImpl implements SpiService {

    @Override
    public boolean isSupport(String name) {
        return "B".equalsIgnoreCase(name.trim());
    }

    @Override
    public String sayHello() {
        return "hello 我是厂商B";
    }
}
