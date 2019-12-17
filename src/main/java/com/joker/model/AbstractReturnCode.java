package com.joker.model;

import java.io.Serializable;

/**
 * Created by xiangrui on 2017/11/27.
 *
 * @author xiangrui
 * @date 2017/11/27
 */
public abstract class AbstractReturnCode implements Serializable {
    private String name;
    private final String desc;
    private final int code;
    private String service;
    private final AbstractReturnCode display;

    public AbstractReturnCode(String desc, int code) {
        this.desc = desc;
        this.code = code;
        this.display = this;
    }

    public AbstractReturnCode(int code, AbstractReturnCode shadow) {
        this.desc = null;
        this.code = code;
        this.display = shadow;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getCode() {
        return this.code;
    }

    public AbstractReturnCode getDisplay() {
        return this.display;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
