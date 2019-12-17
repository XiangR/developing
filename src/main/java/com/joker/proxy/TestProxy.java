package com.joker.proxy;


import com.joker.proxy.cglib.MethodInterceptorFactory;
import com.joker.proxy.cglib.MyMethodInterceptor;
import com.joker.proxy.jdk.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by xiangrui on 2019-05-16.
 *
 * @author xiangrui
 * @date 2019-05-16
 */
public class TestProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestProxy.class);

    public static void main(String[] args) {
        TestProxy test = new TestProxy();
        test.userJdk2();
////
////        test.userCglib();
//
//        test.userCglib2();
    }

    private void userJdk() {

        IUserDao target = new UserDao();
        LOGGER.info("目标对象:{}", target.getClass());

        ProxyFactory<IUserDao> proxyFactory = new ProxyFactory<>(target);
        IUserDao proxy = proxyFactory.getProxyInstance();
        LOGGER.info("代理对象:{}", proxy.getClass());
        Object save = proxy.save(new User("xiangrui", 20));
        LOGGER.info("返回结果:{}", save);
    }

    /**
     * 使用JDK 动态代理，要求target必须实现接口，否则会抛出异常
     * Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to com.joker.proxy.UserDao2
     */
    private void userJdk2() {

        UserDao2 target = new UserDao2();
        LOGGER.info("目标对象:{}", target.getClass());

        ProxyFactory<UserDao2> proxyFactory = new ProxyFactory<>(target);

        // Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to com.joker.proxy.UserDao2
        UserDao2 proxy = proxyFactory.getProxyInstance();
        LOGGER.info("代理对象:{}", proxy.getClass());
        Object save = proxy.save(new User("xiangrui", 20));
        LOGGER.info("返回结果:{}", save);
    }

    private void userCglib() {

        //获取Enhancer 对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDao.class); //设置回调方法
        enhancer.setCallback(new MyMethodInterceptor());

        IUserDao userDao = (IUserDao) enhancer.create();
        Object save = userDao.save(new User("xiangrui", 20));

        LOGGER.info("返回结果:{}", save);
    }

    private void userCglib2() {

        User user = new User("xiangrui", 20);
        MethodInterceptorFactory proxyFactory = new MethodInterceptorFactory();
        IUserDao proxy = proxyFactory.getProxy(UserDao.class);
        Object save = proxy.save(user);
        LOGGER.info("返回结果:{}", save);
    }

    private void userCglib3() {

        User user = new User("xiangrui", 20);
        MethodInterceptorFactory proxyFactory = new MethodInterceptorFactory();
        {
            IUserDao proxy = proxyFactory.getProxy(UserDao.class);
            Object save = proxy.save(user);
            LOGGER.info("返回结果:{}", save);
        }
        {
            IUserDao proxy = proxyFactory.getProxy(UserDao.class);
            Object save = proxy.save(user);
            LOGGER.info("返回结果:{}", save);
        }
    }
}
