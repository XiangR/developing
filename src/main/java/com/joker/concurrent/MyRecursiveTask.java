package com.joker.concurrent;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by xiangrui on 2017/12/25.
 *
 * @author xiangrui
 * @date 2017/12/25
 */
public class MyRecursiveTask extends RecursiveTask<Long> {

    private long workLoad = 0;

    public MyRecursiveTask(long workLoad) {
        this.workLoad = workLoad;
    }

    protected Long compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);

            List<MyRecursiveTask> subTasks = Lists.newArrayList(createSubTasks());
            for (MyRecursiveTask subTask : subTasks) {
                subTask.fork();
            }

            long result = 0;
            for (MyRecursiveTask subTask : subTasks) {
                result += subTask.join();
            }
            return result;

        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
            return workLoad * 3;
        }
    }

    private List<MyRecursiveTask> createSubTasks() {

        MyRecursiveTask subTask1 = new MyRecursiveTask(this.workLoad / 2);
        MyRecursiveTask subTask2 = new MyRecursiveTask(this.workLoad / 2);
        List<MyRecursiveTask> subTasks = Lists.newArrayList(subTask1, subTask2);
        return subTasks;
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);
        long mergedResult = forkJoinPool.invoke(myRecursiveTask);
        System.out.println("mergedResult =" + mergedResult);
    }
}
