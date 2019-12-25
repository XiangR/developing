package com.yit.test.entity;

public class KeyValueTest<T, R> {

    private T key;

    private R object;

    private long tag;

    private int logicalExpireSeconds;

    private String className;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public R getObject() {
        return object;
    }

    public void setObject(R object) {
        this.object = object;
    }

    public int getLogicalExpireSeconds() {
        return logicalExpireSeconds;
    }

    public void setLogicalExpireSeconds(int logicalExpireSeconds) {
        this.logicalExpireSeconds = logicalExpireSeconds;
    }

    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public boolean isLogicExpire() {
        if (logicalExpireSeconds < 0) {
            return false;
        } else {
            return tag + logicalExpireSeconds * 1000 < System.currentTimeMillis();
        }
    }
}
