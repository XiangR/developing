package com.joker.utils;

import com.google.common.collect.Lists;
import com.joker.entity.ActionWithException;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 批量多任务并行执行服务
 * 用法:
 * try (TaskList tasks = BatchTaskService.newTasks()) {
 * tasks.add(() -> {
 * // 任务内容1
 * });
 * tasks.add(() -> {
 * // 任务内容2
 * });
 * }
 * <p>
 * Created by xiangrui on 2017/12/25.
 *
 * @author xiangrui
 * @date 2017/12/25
 */
public class BatchTaskService {

    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    /**
     * 新建一个任务列表
     */
    public static TaskList newTasks() {
        return new TaskList();
    }

    /**
     * 设置线程池线程数
     */
    public void resetThreadCount(int threadCount) {
        executor.shutdown();
        executor = Executors.newFixedThreadPool(threadCount);
    }

    public static class TaskList implements AutoCloseable {
        /**
         * 任务列表
         */
        List<ActionWithException> tasks = Lists.newArrayList();

        /**
         * 添加任务
         */
        public void add(ActionWithException task) {
            tasks.add(task);
        }

        /**
         * 并发执行所有任务并等待任务执行完毕
         */
        @Override
        public void close() {
            List<Callable<Object>> callables = Lists.newArrayList();

            for (ActionWithException task : tasks) {
                callables.add(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        task.invoke();
                        return null;
                    }
                });
            }

            try {
                List<Future<Object>> futures = executor.invokeAll(callables);
                for (Future<Object> future : futures) {
                    if (future.isDone()) {
                        future.get();
                    }
                }
            } catch (CancellationException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }
    }
}
