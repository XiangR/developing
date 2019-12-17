package com.joker.reflect.construct;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 体会使用构造器来构造类
 * Created by xiangrui on 2019-06-27.
 *
 * @author xiangrui
 * @date 2019-06-27
 */
public class ConstructorTest {

    public static void main(String[] args)
            throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        test_one_param();
        test_two_param();
        test_error_constructor();
    }

    /**
     * 无参数的构造器
     */
    @SuppressWarnings("unchecked")
    private static void test_one_param() {

        try {
            //当我不想 newInstance初始化的时候执行空参数的构造函数的时候
            //可以通过字节码文件对象方式 getConstructor() 获取到该构造函数

            String classname = "com.joker.model.Person";

            //寻找名称的类文件，加载进内存 产生class对象
            Class cl = Class.forName(classname);

            // 无参数构造器
            Constructor con = cl.getConstructor();

            //通过构造器对象 newInstance 构造对象
            Object obj = con.newInstance();

            System.out.println(obj instanceof com.joker.model.Person);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 有参数的构造器
     */
    @SuppressWarnings("unchecked")
    private static void test_two_param()
            throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //当我不想 newInstance初始化的时候执行空参数的构造函数的时候
        //可以通过字节码文件对象方式 getConstructor(parameterTypes) 获取到该构造函数

        String classname = "com.joker.model.Person";

        //寻找名称的类文件，加载进内存 产生class对象
        Class cl = Class.forName(classname);

        //获取到Person(String name,int age) 构造函数
        Constructor con = cl.getConstructor(String.class, int.class);

        //通过构造器对象 newInstance 方法对对象进行初始化 有参数构造函数
        Object obj = con.newInstance("神奇的我", 12);

        System.out.println(obj instanceof com.joker.model.Person);
        System.out.println(JSON.toJSONString(obj));

    }

    @SuppressWarnings("unchecked")
    private static void test_error_constructor()
            throws NoSuchMethodException, ClassNotFoundException {

        try {
            //当我不想 newInstance初始化的时候执行空参数的构造函数的时候
            //可以通过字节码文件对象方式 getConstructor(parameterTypes) 获取到该构造函数

            String classname = "com.joker.model.Person";

            //寻找名称的类文件，加载进内存 产生class对象
            Class cl = Class.forName(classname);

            //获取到Person(String name,int age) 构造函数
            Constructor con = cl.getConstructor(String.class);

        } catch (Exception e) {

            // NoSuchMethodException 因为找不到一个参数的构造器
            if (!(e instanceof NoSuchMethodException)) {
                throw e;
            }

        }

    }
}
