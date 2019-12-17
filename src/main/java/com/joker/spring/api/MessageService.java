package com.joker.spring.api;

/**
 * Created by xiangrui on 2019-09-25.
 *
 * @author xiangrui
 * @date 2019-09-25
 */
public interface MessageService {

    String getMessage();

    String getMessage(String msg);

    boolean checkUserNotNull();

}