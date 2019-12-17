package com.joker.utils.pagingquery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询Util
 *
 * @author xiangrui
 */
public class PagingQueryResp<T extends Serializable> implements Serializable {
    /**
     * 列表数据
     */
    public List<T> result = new ArrayList<>();
    /**
     * 当前游标位置
     */
    public int cursor = 0;

    public PagingQueryResp(int cursor) {
        this.cursor = cursor;
    }

    public PagingQueryResp() {
    }

}