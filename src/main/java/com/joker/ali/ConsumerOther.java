package com.joker.ali;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class ConsumerOther implements Runnable {
    private PriorityBlockingQueue<ConsumeObj> queue;
    private LockHelper lockHelper;
    private StopHelper stopHelper;

    public ConsumerOther(PriorityBlockingQueue<ConsumeObj> queue, LockHelper lockHelper, StopHelper stopHelper) {
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
                        System.out.println("收到指令,ConsumerOther结束信息");
                        // 通知下一个
                        lockHelper.conditionTwo.signal();
                        break;
                    }

                    ConsumeObj data = queue.take();
                    if ((data.getData() % 2 != 0) && (data.getData() % 3 != 0)) {
                        System.out.println("既不可以被2整除，也不可以被3整除的数：" + data);
                    } else {
                        queue.offer(data);
                    }

                    // 通知下一个
                    lockHelper.conditionTwo.signal();

                    // 当前等待
                    lockHelper.conditionOther.awaitUninterruptibly();

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
