package com.joker.cache;

import java.util.Objects;

public class KeyValueObject<T, R> {

    private T key;

    private R value;

    private long tag;

    private int logicalExpireSeconds;

    private String keyClassName;

    private String objClassName;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public R getValue() {
        return value;
    }

    public void setValue(R value) {
        this.value = value;
    }

    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public int getLogicalExpireSeconds() {
        return logicalExpireSeconds;
    }

    public void setLogicalExpireSeconds(int logicalExpireSeconds) {
        this.logicalExpireSeconds = logicalExpireSeconds;
    }

    public String getKeyClassName() {
        return keyClassName;
    }

    public void setKeyClassName(String keyClassName) {
        this.keyClassName = keyClassName;
    }

    public String getObjClassName() {
        return objClassName;
    }

    public void setObjClassName(String objClassName) {
        this.objClassName = objClassName;
    }

    /**
     * 当前 key 是否逻辑过期
     */
    public boolean isLogicExpire() {
        if (logicalExpireSeconds < 0) {
            return false;
        } else {
            return tag + logicalExpireSeconds * 1000 < System.currentTimeMillis();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyValueObject)) return false;
        KeyValueObject<?, ?> that = (KeyValueObject<?, ?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
