package com.joker.ali;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 使用“生产者-消费者模式”编写代码实现：线程A随机间隔（10~200ms）按顺序生成1到100的数字（共100个），
 * 放到某个队列中.3个线程B、C、D即时消费这些数据，线程B打印（向控制台）所有被2整除的数，
 * 线程C打印被3整除的数，线程D打印其它数据，要求数字的打印是有序的（从1到100）
 * 限时40分钟，可以使用IDE及第三方类库
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        int size = 100;

        Comparator comparator = (Comparator<ConsumeObj>) (i1, i2) -> i2.getData() - i1.getData();
        PriorityBlockingQueue<ConsumeObj> queue = new PriorityBlockingQueue<>(size, comparator);

        LockHelper lockHelper = new LockHelper();
        StopHelper stopHelper = new StopHelper(size, queue);

        Producer a = new Producer(queue, lockHelper, stopHelper, size);

        ConsumeTwo two = new ConsumeTwo(queue, lockHelper, stopHelper);
        ConsumeThird third = new ConsumeThird(queue, lockHelper, stopHelper);
        ConsumerOther other = new ConsumerOther(queue, lockHelper, stopHelper);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(a);
        service.execute(other);
        service.execute(two);
        service.execute(third);

        service.shutdown();

        Thread.sleep(1000);

        System.out.println("主线程退出");
    }

}
