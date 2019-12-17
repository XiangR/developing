package com.joker.utils.pagingquery;

/**
 * 分页查询Util
 *
 * @author xiangrui
 */
public interface PagingQueryFilterable {

    /**
     * page query 是否被需要被过滤
     *
     * @return 是否被需要被过滤
     */
    boolean filtered();
}
