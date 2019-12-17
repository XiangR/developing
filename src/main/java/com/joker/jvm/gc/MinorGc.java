package com.joker.jvm.gc;

public class MinorGc {


    private static final int _1MB = 1024 * 1024;


    public static void testAllocation() {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        byte[] allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) throws InterruptedException {
        testAllocation();
    }
}
