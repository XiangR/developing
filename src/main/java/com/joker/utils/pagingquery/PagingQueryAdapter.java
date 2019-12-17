package com.joker.utils.pagingquery;

/**
 * 分页查询Util
 *
 * @author xiangrui
 */
public class PagingQueryAdapter<T> implements PagingQueryFilterable {
    private T source;
    private boolean filtered;

    public PagingQueryAdapter(boolean filtered) {
        this.filtered = filtered;
    }

    public PagingQueryAdapter(T source) {
        this.source = source;
        if (this.source == null) {
            this.filtered = true;
        }
    }

    public PagingQueryAdapter(boolean filtered, T source) {
        this.source = source;
        this.filtered = this.source == null || filtered;
    }

    public T getSource() {
        return source;
    }

    @Override
    public boolean filtered() {
        return source == null || filtered;
    }
}
