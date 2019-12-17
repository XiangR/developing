package com.joker.utils.pagingquery;

import com.joker.entity.Func2;
import com.joker.entity.PageParameter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询Util
 *
 * @author xiangrui
 */
public class PagingQueryUtil {

    /**
     * 分页查询
     *
     * @param pageParameter 前端的分页参数
     * @param func          func
     * @param <T>           T
     * @return result
     */
    public static <T extends Serializable> PagingQueryResp<T> pagingQuery(
            PageParameter pageParameter,
            Func2<List<PagingQueryAdapter<T>>, PageParameter> func) {
        return pagingQuery(5, pageParameter, func);
    }

    /**
     * 分页查询
     *
     * @param bufferSize    后端预取数据大小
     * @param pageParameter 前端的分页参数
     * @param func          func
     * @param <T>           T
     * @return result
     */
    public static <T extends Serializable> PagingQueryResp<T> pagingQuery(int bufferSize,
                                                                          PageParameter pageParameter,
                                                                          Func2<List<PagingQueryAdapter<T>>,
                                                                                  PageParameter> func) {
        int expectSize = pageParameter.limit;
        pageParameter.limit = pageParameter.limit + bufferSize;
        PagingQueryResp<T> pagingQueryResp = new PagingQueryResp<>();
        pagingQueryResp.cursor = pageParameter.offset;
        return pagingQuery(expectSize, pageParameter, func, pagingQueryResp);
    }

    /**
     * 分页查询
     *
     * @param expectSize       当前缺少的数据量
     * @param newPageParameter 当前查询分页参数
     * @param func             func
     * @param pagingQueryResp  返回数据集
     * @param <T>              T
     * @return result
     */
    private static <T extends Serializable> PagingQueryResp<T> pagingQuery(int expectSize,
                                                                           PageParameter newPageParameter,
                                                                           Func2<List<PagingQueryAdapter<T>>,
                                                                                   PageParameter> func,
                                                                           PagingQueryResp<T> pagingQueryResp) {
        List<PagingQueryAdapter<T>> queryResult = func.invoke(newPageParameter);
        for (PagingQueryAdapter<T> t : queryResult) {
            if (expectSize > 0) {
                if (!t.filtered()) {
                    pagingQueryResp.result.add(t.getSource());
                    expectSize--;
                }
                pagingQueryResp.cursor++;
            } else {
                break;
            }
        }
        // expectSize > 0 ，还需要请求下一页来填充当前的缺失数据
        if (queryResult.size() >= newPageParameter.limit && expectSize > 0) {
            newPageParameter.offset = newPageParameter.offset + newPageParameter.limit;
            pagingQuery(expectSize, newPageParameter, func, pagingQueryResp);
        }
        return pagingQueryResp;
    }
}
