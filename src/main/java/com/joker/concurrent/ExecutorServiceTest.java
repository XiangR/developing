package com.joker.concurrent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by xiangrui on 2017/12/25.
 *
 * @author xiangrui
 * @date 2017/12/25
 */
public class ExecutorServiceTest {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

//        execute();
//        submitRunnable();
//        submitCallable();
//        invokeAny();
        invokeAll();
//        executorService();
    }

    private static void executorService() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {//5个任务
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " doing task");
                }
            });
        }
    }

    private static void invokeAll() {
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        try {
            List<Future<String>> futures = executorService.invokeAll(callables);
            for (Future<String> future : futures) {
                if (future.isDone()) {
                    System.out.println("future.get = " + future.get());
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * 调用这个方法并不会返回一个 Future，但它返回其中一个 Callable 对象的结果。
     * 无法保证返回的是哪个 Callable 的结果 - 只能表明其中一个已执行结束。
     * 如果其中一个任务执行结束 (或者抛了一个异常)，其他 Callable 将被取消。
     */
    private static void invokeAny() {
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        try {
            String result = executorService.invokeAny(callables);
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * submit 提供返回值，且接收 Callable、Runnable 两种类型
     * Callable 存在泛型的返回值
     * Runnable 不存在返回值
     */
    private static void submitCallable() {
        Future future = executorService.submit(new Callable<Object>() {
            public Object call() throws Exception {
                System.out.println("Asynchronous Callable");
                return "Callable Result";
            }
        });

        try {
            System.out.println("future.get() = " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    private static void submitRunnable() {

        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });

        try {
            Object o = future.get();//returns null if the task has finished correctly.
            System.out.println(o);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void execute() {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        };
        executorService.execute(runnable);
        executorService.shutdown();
    }


}
