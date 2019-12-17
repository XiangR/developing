package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.joker.config.Constants;
import com.joker.entity.DataMerge;
import com.joker.entity.Func2;
import com.joker.entity.Func3;
import com.joker.entity.PageParameter;
import com.joker.test.ObjectSizeCalculator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public class DataUtil {

    /**
     * 日志工具
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);

    private static final Joiner HYPHEN_JOIN = Joiner.on(CommonConfig.HYPHEN);

    /**
     * 任务队列
     */
    private static final LinkedBlockingQueue<Runnable> REFRESH_WORKING_QUEUE = new LinkedBlockingQueue<>(1024);

    /**
     * 异步线程池
     */
    private static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(20, 20, 30, TimeUnit.SECONDS, REFRESH_WORKING_QUEUE, new ThreadFactoryBuilder().build(),
                    (r, executor) -> {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );

    /**
     * MD5加密字节文件
     *
     * @param bytes byte文件
     */
    public static String EncoderByMd5(byte[] bytes) {
        String newStr = "";
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newStr = base64en.encode(md5.digest(bytes));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newStr;
    }

    /**
     * 检查对象及其属性不能为null
     *
     * @param target 检测对象
     */
    public static void assertPropertyNotNull(Object target) {
        if (!checkPropertyNotNull(target)) {
            throw new IllegalArgumentException("存在必填参数为空！");
        }
    }

    /**
     * 检查对象及其属性是否存在null
     *
     * @param target 检测对象
     * @return 是否存在null
     */
    public static boolean checkPropertyNotNull(Object target) {
        if (Objects.isNull(target)) {
            return false;
        }
        try {
            Field[] declaredFields = target.getClass().getDeclaredFields();
            for (Field f : declaredFields) {
                f.setAccessible(true);
                //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                if (f.get(target) == null) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("参数异常！");
        }
        return true;
    }

    public static <T> List<T> checkMsg(String msg, Class<T> type) {
        if (StringUtils.isEmpty(msg)) {
            return Lists.newArrayList();
        }
        List<T> parameters = Lists.newArrayList();
        try {
            parameters = JSON.parseArray(msg, type);
        } catch (Exception e) {
            LOGGER.error("消息格式错误,msg:{}", msg);
        }
        if (CollectionUtils.isEmpty(parameters)) {
            LOGGER.info("消息内容为空{}", msg);
            return Lists.newArrayList();
        }
        return parameters;
    }

    /**
     * 生成指定长度随机数字字符串
     */
    public static String getRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static <R, T> List<R> partitionQueryDB(T query, Func3<List<R>, T, PageParameter> func3) {
        return partitionQueryDB(query, func3, 1000);
    }

    public static <R, T> List<R> partitionQueryDB(T query, Func3<List<R>, T, PageParameter> func3, int limit) {
        List<R> allList = Lists.newArrayList();
        PageParameter page = new PageParameter();
        List<R> singleList;
        int cycle = 0;
        page.limit = limit;
        do {
            page.limit = limit;
            page.offset = cycle * limit;
            List<R> invoke = func3.invoke(query, page);
            singleList = CollectionUtils.isEmpty(invoke) ? Lists.newArrayList() : invoke;
            allList.addAll(singleList);
            cycle++;
        } while (singleList.size() >= limit);
        return allList;
    }

    public static <R, T, P> List<R> partitionInvoke(List<T> paramList, P param, Func3<List<R>, List<T>, P> func3) {
        return partitionInvoke(paramList, param, func3, 500);
    }

    public static <R, T, P> List<R> partitionInvoke(List<T> paramList, P param, Func3<List<R>, List<T>, P> func3, int pageSize) {
        if (CollectionUtils.isEmpty(paramList)) {
            return Lists.newArrayList();
        }
        paramList = paramList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<List<T>> queryList = Lists.partition(paramList, pageSize);
        List<R> resultList = Lists.newArrayList();
        for (List<T> query : queryList) {
            List<R> result = func3.invoke(query, param);
            if (CollectionUtils.isNotEmpty(result)) {
                resultList.addAll(result);
            }
        }
        return resultList;
    }

    public static <R, T> List<R> partitionInvoke(Collection<T> paramColl, Func2<List<R>, List<T>> func2) {
        return partitionInvoke(paramColl, func2, 500);
    }

    public static <R, T> List<R> partitionInvoke(Collection<T> paramColl, Func2<List<R>, List<T>> func2, int pageSize) {
        if (CollectionUtils.isEmpty(paramColl)) {
            return Lists.newArrayList();
        }
        List<T> tempList = paramColl.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<List<T>> queryList = Lists.partition(tempList, pageSize);
        List<R> resultList = Lists.newArrayList();
        for (List<T> query : queryList) {
            List<R> result = func2.invoke(query);
            if (CollectionUtils.isNotEmpty(result)) {
                resultList.addAll(result);
            }
        }
        return resultList;
    }

    public static <T, R> List<Multiset.Entry<R>> getRepeatEntry(List<T> list, Function<T, R> map) {
        List<R> checkList = new ArrayList<>();
        for (T t : list) {
            R apply = map.apply(t);
            checkList.add(apply);
        }
        HashMultiset<R> rs = HashMultiset.create(checkList);
        return rs.entrySet().stream().filter(k -> k.getCount() > 1).collect(Collectors.toList());
    }

    public static <T, R> List<R> getRepeatElement(List<T> list, Function<T, R> map) {
        List<Multiset.Entry<R>> repeatEntryList = getRepeatEntry(list, map);
        return repeatEntryList.stream().map(Multiset.Entry::getElement).collect(Collectors.toList());
    }

    /**
     * 中文排序
     * 请注意外部防止 NPE
     *
     * @param list 待排序集合
     * @param func invoke
     * @param <T>  类型
     */
    public static <T> void chineseSort(List<T> list, Func2<String, T> func) {
        // 中文的排序
        Comparator cmp = Collator.getInstance(Locale.CHINA);

        Comparator<T> china_sort = (v1, v2) -> cmp.compare(func.invoke(v1), func.invoke(v2));

        list.sort(china_sort);
    }

    /**
     * 借助 fastjson 实现对象复制
     *
     * @param o   object
     * @param <T> 类型
     * @return 复制后的对象
     */
    public static <T> T deepCopy(T o) {
        if (o == null) {
            return null;
        }
        Class<?> aClass = o.getClass();
        String string = JSON.toJSONString(o);
        return (T) JSON.parseObject(string, aClass);
    }

    public static int getTotalPage(int totalCount, int pageSize) {
        return (int) (Math.ceil((double) totalCount / (double) pageSize));
    }

    /**
     * 处理两个集合的 merge
     *
     * @param insertList 插入的 list
     * @param existList  已经存在的 list
     * @param <T>        范型
     * @return 数据处理结果
     */
    public static <T> DataMerge<T> processMerge(List<T> insertList, List<T> existList) {
        insertList = insertList == null ? Lists.newArrayList() : insertList;
        existList = existList == null ? Lists.newArrayList() : existList;
        List<T> insert = Lists.newArrayList(CollectionUtils.subtract(insertList, existList));
        List<T> delete = Lists.newArrayList(CollectionUtils.subtract(existList, insertList));
        List<T> intersection = Lists.newArrayList(CollectionUtils.intersection(existList, insertList));
        DataMerge<T> merge = new DataMerge<>();
        merge.insert = insert;
        merge.intersection = intersection;
        merge.delete = delete;
        return merge;
    }

    public static <T> String join(T... a) {
        return HYPHEN_JOIN.join(a);
    }

    public static <T> String join(Collection<T> a) {
        return HYPHEN_JOIN.join(a);
    }

    public static <T> String join(Collection<T> p_list, String prefix) {
        return Joiner.on(prefix).join(p_list);
    }

    public static <T, R> String join(Collection<T> p_list, Function<T, R> map) {
        List<R> r_list = Lists.newArrayList();
        for (T t : p_list) {
            R apply = map.apply(t);
            r_list.add(apply);
        }
        return HYPHEN_JOIN.join(r_list);
    }

    public static <T, R> String join(Collection<T> p_list, Function<T, R> map, String prefix) {
        List<R> r_list = Lists.newArrayList();
        for (T t : p_list) {
            R apply = map.apply(t);
            r_list.add(apply);
        }
        return Joiner.on(prefix).join(r_list);
    }

    public static boolean objectSizeOver1MB(Object object) {
        if (object == null) {
            return false;
        }
        return ObjectSizeCalculator.getObjectSize(object) > Constants._1MB;
    }

    public static <R, T> List<R> partitionAsyncInvoke(Collection<T> paramList, Function<List<T>, List<R>> func2, int pageSize) {
        if (CollectionUtils.isEmpty(paramList)) {
            return Lists.newArrayList();
        }
        try {
            List<List<T>> queryList = Lists.partition(Lists.newArrayList(paramList), pageSize);
            List<Callable<List<R>>> taskList = Lists.newArrayList();
            for (List<T> query : queryList) {
                taskList.add(() -> func2.apply(query));
            }
            List<R> resultList = Lists.newArrayList();
            List<Future<List<R>>> futureList = EXECUTOR.invokeAll(taskList);
            for (Future<List<R>> future : futureList) {
                if (future != null) {
                    List<R> rs = future.get();
                    if (CollectionUtils.isNotEmpty(rs)) {
                        resultList.addAll(rs);
                    }
                }
            }
            return resultList;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }


}
