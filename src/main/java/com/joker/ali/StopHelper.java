package com.joker.ali;

import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class StopHelper {

    public volatile int size;
    public volatile int cursor;
    public PriorityBlockingQueue<ConsumeObj> queue;

    public StopHelper(int size, PriorityBlockingQueue<ConsumeObj> queue) {
        this.size = size;
        this.cursor = 0;
        this.queue = queue;
    }

    public boolean success() {
        return Objects.equals(size, cursor) && queue.isEmpty();
    }
}
