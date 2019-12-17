package com.joker.ali;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class Producer implements Runnable {

    private static Random r = new Random();
    private int size;
    private PriorityBlockingQueue queue;
    private LockHelper lockHelper;
    private StopHelper stopHelper;

    public Producer(PriorityBlockingQueue<ConsumeObj> queue, LockHelper lockHelper, StopHelper stopHelper, int size) {
        this.queue = queue;
        this.size = size;
        this.lockHelper = lockHelper;
        this.stopHelper = stopHelper;
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {

            // 停顿一下
            sleep();

            // 开始放数量
            int thisCount = i + 1;
            System.out.println(String.format("%s 加入队列", thisCount));

            // 延迟
            if (!queue.offer(new ConsumeObj(thisCount), 2, TimeUnit.SECONDS)) {
                System.out.println(String.format("%s 加入队列失败", thisCount));
            }
            stopHelper.cursor = thisCount;
        }
        System.out.println("Producers结束生产");
    }

    private void sleep() {

        try {
            Thread.sleep(10 + r.nextInt(190)); //停顿 10~200毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
