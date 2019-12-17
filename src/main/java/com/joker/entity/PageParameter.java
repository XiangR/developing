package com.joker.entity;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public class PageParameter {

    public int offset;

    public int limit;

    public PageParameter() {
    }

    public PageParameter(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }
}
