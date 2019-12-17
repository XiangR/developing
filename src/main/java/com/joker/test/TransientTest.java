package com.joker.test;

import com.alibaba.fastjson.JSON;

/**
 * Created by xiangrui on 2019-06-25.
 *
 * @author xiangrui
 * @date 2019-06-25
 */
public class TransientTest {

    public static void main(String[] args) {

        Rectangle rectangle = new Rectangle(3, 4);

        String beforeSer = JSON.toJSONString(rectangle);
        System.out.format("序列化前:%s \n", beforeSer);
        System.out.format("序列化前面积:%s \n", rectangle.area);

        Rectangle afterSer = JSON.parseObject(beforeSer, Rectangle.class);
        System.out.format("序列化后:%s \n", JSON.toJSONString(afterSer));
        System.out.format("序列化后面积:%s \n", afterSer.area);

        System.out.format("序列化前面积-计算所得值:%s\n", rectangle.findAbstractArea());
        System.out.format("序列化后面积-计算所得值:%s\n", afterSer.findAbstractArea());
    }

    /**
     * 体会 transient 关键字，其不可被序列化
     * 可以用于一些需要计算的属性，或者一些被动注入的属性比如 添加一个 全局的 ApplicationContext {@link com.joker.utils.SpringContextUtils}
     */
    static class Rectangle {

        private int width;

        private int length;

        private transient int area;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int findAbstractArea() {
            return area == 0
                    ? (width * length) / 2
                    : area;
        }

        public Rectangle() {
        }

        public Rectangle(int width, int length) {
            this.width = width;
            this.length = length;
            this.area = findAbstractArea();
        }
    }
}
