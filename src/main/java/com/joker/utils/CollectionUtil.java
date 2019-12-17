package com.joker.utils;

import com.google.common.collect.Lists;
import com.joker.entity.EqualsFunc;
import com.joker.entity.FilterFunc;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2017/12/21.
 *
 * @author xiangrui
 * @date 2017/12/21
 */
public class CollectionUtil {

    /**
     * List去除重复元素 支持调用方自定义元素重复规则
     */
    public static <T> List<T> distinct(List<T> source, EqualsFunc<? super T> func) {
        List<T> data = Lists.newArrayList();
        if (CollectionUtils.isEmpty(source) || Objects.isNull(func)) {
            return data;
        }
        data.addAll(source);
        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = data.size() - 1; j > i; j--) {
                if (func.invoke(data.get(i), data.get(j))) {
                    data.remove(j);
                }
            }
        }
        return data;
    }

    public static <T> List<T> filter(List<T> source, FilterFunc<? super T> func) {
        List<T> data = Lists.newArrayList();
        if (CollectionUtils.isEmpty(source) || Objects.isNull(func)) {
            return data;
        }
        data.addAll(source.stream().filter(func::filter).collect(Collectors.toList()));
        return data;
    }


    // test
// test git

}
