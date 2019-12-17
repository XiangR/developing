package com.joker.ali;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class LockHelper {

    public Lock lock;
    public Condition conditionOther;
    public Condition conditionTwo;
    public Condition conditionThird;

    public LockHelper() {
        lock = new ReentrantLock();
        conditionOther = lock.newCondition();
        conditionTwo = lock.newCondition();
        conditionThird = lock.newCondition();
    }
}
