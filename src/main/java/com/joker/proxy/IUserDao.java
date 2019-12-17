package com.joker.proxy;

/**
 * Created by xiangrui on 2019-05-16.
 *
 * @author xiangrui
 * @date 2019-05-16
 */
public interface IUserDao {

    Object save(User user);

    Object find();
}
