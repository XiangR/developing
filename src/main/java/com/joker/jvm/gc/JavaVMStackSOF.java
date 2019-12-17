package com.joker.jvm.gc;

/**
 * Created by xiangrui on 2019-10-01.
 * <p>
 * VM args: -Xss128k
 * 如果线程请求的栈深度超过虚拟机的最大深度，将抛出 StackOverflowError
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();

        try {

            oom.stackLeak();

        } catch (Throwable e) {

            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
