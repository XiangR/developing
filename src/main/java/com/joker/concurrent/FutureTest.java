package com.joker.concurrent;

import com.google.common.collect.Lists;
import com.joker.model.Person;

import java.util.List;
import java.util.concurrent.*;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class FutureTest {

    private static ExecutorService executorService2 = new ThreadPoolExecutor(5, 20, 10, TimeUnit.SECONDS, new
            LinkedBlockingQueue<Runnable>(1000));

    public static void main(String[] args) throws Exception {
        Person person = test3();
        System.out.println(person);
    }

    public static void test() throws InterruptedException {

        List<Integer> list = Lists.newArrayList();

        List<Callable<String>> list1 = Lists.newArrayList();
        list1.add(() -> {
            list.add(1);
            return "SUCCESS";
        });
        list1.add(() -> {
            list.add(2);
            return "SUCCESS";
        });
        List<Future<String>> futures = executorService2.invokeAll(list1);

        System.out.println(list);
    }

    public static void runAsyncExample() {
        List<Integer> list = Lists.newArrayList();

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture
                .runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                        list.add(1);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .thenRunAsync(() -> {
                    try {
                        Thread.sleep(2000);
                        list.add(2);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
//        voidCompletableFuture.join();
        System.out.println(list);
    }

    public static Person test3() {
        Person per = new Person();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                per.setAge(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService2);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                per.setName("xiang");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService2);
        CompletableFuture<Void> future2 = CompletableFuture.allOf(future, future1);
        future.join();
        return per;
    }

    public static void test4() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "abc";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            return "d";
        });
        CompletableFuture<String> aBreak = future.thenCombineAsync(future2, (x, y) -> {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("break");
            return y + "-" + x;
        });

        System.out.println("end");

    }

    public static void test1() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture
                .completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(result::append);
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

}
