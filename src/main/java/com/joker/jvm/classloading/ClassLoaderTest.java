package com.joker.jvm.classloading;

import java.io.InputStream;

/**
 * Created by xiangrui on 2019-10-03.
 *
 * @author xiangrui
 * @date 2019-10-03
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream stream = getClass().getResourceAsStream(fileName);
                    if (stream == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[stream.available()];
                    stream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        Object o = myLoader.loadClass("com.joker.jvm.classloading.ClassLoaderTest").newInstance();
        System.out.println(o.getClass());
        System.out.println(o instanceof ClassLoaderTest);


    }
}
