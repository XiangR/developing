package com.joker.ali;

/**
 * Created by xiangrui on 2019-10-18.
 *
 * @author xiangrui
 * @date 2019-10-18
 */
public class ConsumeObj implements Comparable<ConsumeObj> {

    private final int date;

    private boolean twoDone;
    private boolean thirdDone;

    ConsumeObj(int d) {
        date = d;
        twoDone = false;
        thirdDone = false;
    }

    public int getData() {
        return date;
    }

    public int getDate() {
        return date;
    }

    public boolean isTwoDone() {
        return twoDone;
    }

    public void setTwoDone(boolean twoDone) {
        this.twoDone = twoDone;
    }

    public boolean isThirdDone() {
        return thirdDone;
    }

    public void setThirdDone(boolean thirdDone) {
        this.thirdDone = thirdDone;
    }

    @Override
    public String toString() {
        return "data:" + date;
    }

    @Override
    public int compareTo(ConsumeObj o) {
        return Integer.compare(this.date, o.date);
    }
}
