package com.joker.model;

import java.io.Serializable;

/**
 * Created by xiangrui on 2017/11/27.
 *
 * @author xiangrui
 * @date 2017/11/27
 */
public class ServiceRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private int displayCode;
    private String description;
    private String msg;

    private ServiceRuntimeException() {
    }

    public ServiceRuntimeException(String msg) {
        this(SupplyChainReturnCode.PARAMETER_ERROR, msg);
    }

    public ServiceRuntimeException(String s, Integer code) {
        System.out.println(s + code);
    }

    public ServiceRuntimeException(AbstractReturnCode code) {
        this(code, code.getDesc());
    }

    public ServiceRuntimeException(AbstractReturnCode code, String msg) {
        super("code:" + code.getCode() + ":" + code.getDesc() + " msg:" + msg);
        this.code = code.getCode();
        this.displayCode = code.getDisplay().getCode();
        this.description = code.getDisplay().getDesc();
        this.msg = msg;
    }

    public ServiceRuntimeException(AbstractReturnCode code, Exception e) {
        this(code, e.getMessage(), e);
    }

    public ServiceRuntimeException(AbstractReturnCode code, String msg, Exception e) {
        super("code:" + code.getCode() + ":" + code.getDesc() + " msg:" + msg, e);
        this.code = code.getCode();
        this.displayCode = code.getDisplay().getCode();
        this.description = code.getDisplay().getDesc();
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public int getDisplayCode() {
        return this.displayCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDescription() {
        return this.description;
    }
}
