package com.joker.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by xiangrui on 2017/12/25.
 *
 * @author xiangrui
 * @date 2017/12/25
 */
public class MyRecursiveAction extends RecursiveAction {

    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        //if work is above threshold, break tasks up into smaller tasks
        if (this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);
            List<MyRecursiveAction> subTasks = createSubTasks();
            for (RecursiveAction subTask : subTasks) {
                subTask.fork();
            }
        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
        }
    }

    private List<MyRecursiveAction> createSubTasks() {
        List<MyRecursiveAction> subTasks = new ArrayList<>();

        MyRecursiveAction subTask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subTask2 = new MyRecursiveAction(this.workLoad / 2);

        subTasks.add(subTask1);
        subTasks.add(subTask2);

        return subTasks;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(24);
        forkJoinPool.invoke(myRecursiveAction);
    }

}
