package com.joker.spi.simple;


import javax.annotation.Resource;

/**
 * Created by xiangrui on 2019-06-25.
 *
 * @author xiangrui
 * @date 2019-06-25
 */
@Resource(type = SpiServiceAImpl.class)
public interface SpiService {

    boolean isSupport(String name);

    String sayHello();
}
