package com.joker.aop;

import java.lang.annotation.*;

/**
 * Created by xiangrui on 2018/6/25.
 *
 * @author xiangrui
 * @date 2018/6/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AddApplicationContext {

}
