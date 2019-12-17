package com.joker.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public class DataMerge<T> {

    public List<T> insert = new ArrayList<>();
    public List<T> intersection = new ArrayList<>();
    public List<T> delete = new ArrayList<>();
}
