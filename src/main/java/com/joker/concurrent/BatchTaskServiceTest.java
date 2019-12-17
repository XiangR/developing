package com.joker.concurrent;

import com.joker.utils.BatchTaskService;

import java.util.stream.IntStream;

/**
 * Created by xiangrui on 2017/12/25.
 *
 * @author xiangrui
 * @date 2017/12/25
 */
public class BatchTaskServiceTest {


    public static void test() {
        try (BatchTaskService.TaskList tasks = BatchTaskService.newTasks()) {
            tasks.add(() -> {
                IntStream.range(0, 10).forEach(k -> System.out.println(k + " task1 " + System.currentTimeMillis()));
            });

            tasks.add(() -> {
                IntStream.range(0, 10).forEach(k -> System.out.println(k + " task2 " + System.currentTimeMillis()));
            });
        }
    }

    public static void main(String[] args) {
        test();
    }
}
