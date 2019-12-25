package com.yit.test.entity;

import java.util.Objects;

public class KeyValueObject<T> {

    private String key;

    private T object;

    private long tag;

    private int logicalExpireSeconds;

    private String className;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValueObject that = (KeyValueObject) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key);
    }
}
