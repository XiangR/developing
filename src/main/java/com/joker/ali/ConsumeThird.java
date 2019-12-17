package com.joker.ali;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class ConsumeThird implements Runnable {

    private PriorityBlockingQueue<ConsumeObj> queue;
    private LockHelper lockHelper;
    private StopHelper stopHelper;

    public ConsumeThird(PriorityBlockingQueue<ConsumeObj> queue, LockHelper lockHelper, StopHelper stopHelper) {
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
                        lockHelper.conditionOther.signal();
                        System.out.println("收到指令,ConsumeThird结束信息");
                        break;
                    }

                    ConsumeObj data = queue.take();
                    if (!data.isThirdDone() && data.getData() % 3 == 0) {
                        System.out.println("可以被3整除的数：" + data);
                        data.setThirdDone(true);
                        if (data.getData() % 2 == 0) {
                            queue.offer(data);
                        }
                    } else {
                        queue.offer(data);
                    }

                    // 通知下一个
                    lockHelper.conditionOther.signal();

                    // 当前等待
                    lockHelper.conditionThird.awaitUninterruptibly();

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
