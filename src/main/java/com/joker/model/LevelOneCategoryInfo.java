package com.joker.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by xiangrui on 2017/12/18.
 *
 * @author xiangrui
 * @date 2017/12/18
 */
public class LevelOneCategoryInfo {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
