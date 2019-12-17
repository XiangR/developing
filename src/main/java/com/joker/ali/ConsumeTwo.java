package com.joker.ali;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class ConsumeTwo implements Runnable {
    private PriorityBlockingQueue<ConsumeObj> queue;
    private LockHelper lockHelper;
    private StopHelper stopHelper;

    public ConsumeTwo(PriorityBlockingQueue<ConsumeObj> queue, LockHelper lockHelper, StopHelper stopHelper) {
        this.queue = queue;
        this.lockHelper = lockHelper;
        this.stopHelper = stopHelper;
    }

    @Override
    public void run() {
        try {

            while (true) {

                try {
                    lockHelper.lock.lock();

                    if (stopHelper.success()) {
                        // 通知下一个
                        lockHelper.conditionThird.signal();
                        System.out.println("收到指令,ConsumeTwo结束信息");
                        break;
                    }

                    ConsumeObj data = queue.take();
                    if (!data.isTwoDone() && data.getData() % 2 == 0) {
                        data.setTwoDone(true);
                        if (data.getData() % 3 == 0) {
                            System.out.println("可以被2/3整除的数：" + data);
                        } else {
                            System.out.println("可以被2整除的数：" + data);
                        }
                    } else {
                        queue.offer(data);
                    }

                    // 通知下一个
                    lockHelper.conditionThird.signal();

                    // 当前等待
                    lockHelper.conditionTwo.awaitUninterruptibly();

                } finally {
                    lockHelper.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
