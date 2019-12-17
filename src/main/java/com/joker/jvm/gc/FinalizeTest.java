package com.joker.jvm.gc;

public class FinalizeTest {


    public static FinalizeTest save_hook = null;

    public void isAlive() {

        System.out.println(" i am still alive !");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method exec !");
        save_hook = this;
    }

    public static void main(String[] args) throws InterruptedException {

        save_hook = new FinalizeTest();

        save_hook = null;
        System.gc();
        Thread.sleep(500);
        if (save_hook != null) {
            save_hook.isAlive();
        } else {
            System.out.println(" i dead");
        }

        save_hook = null;
        System.gc();
        Thread.sleep(500);
        if (save_hook != null) {
            save_hook.isAlive();
        } else {
            System.out.println(" i dead");
        }

    }
}
