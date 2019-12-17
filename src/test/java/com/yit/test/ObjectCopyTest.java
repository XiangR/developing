package com.yit.test;

import com.joker.model.pricecurve.AbstractPriceEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Created by xiangrui on 2018/1/9.
 *
 * @author xiangrui
 * @date 2018/1/9
 */
public class ObjectCopyTest {


    @Test
    public void test() {
        AbstractPriceEntity entity = new AbstractPriceEntity();
        entity.setPrice(new BigDecimal(60));
        entity.setWeight(60);


        AbstractPriceEntity newObject = entity;
        newObject.setWeight(70);
        newObject.setPrice(new BigDecimal(70));

        Assert.assertEquals(newObject.getWeight(), entity.getWeight());
        Assert.assertEquals(newObject.getPrice(), entity.getPrice());

//        AbstractPriceEntity copyFrom = copyFrom(newObject);
        AbstractPriceEntity copyFrom = copyFromWithType(newObject, AbstractPriceEntity.class);
        copyFrom.setPrice(new BigDecimal(80));
        copyFrom.setWeight(80);

        Assert.assertNotEquals(newObject.getWeight(), copyFrom.getWeight());
        Assert.assertNotEquals(newObject.getPrice(), copyFrom.getPrice());

    }

    private AbstractPriceEntity copyFrom(AbstractPriceEntity source) {
        if (source == null) {
            return null;
        }
        try {
            Class sourceClazz = source.getClass();
            AbstractPriceEntity result = (AbstractPriceEntity) sourceClazz.newInstance();
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    private <T> T copyFromWithType(Object source, Class<T> type) {
        if (source == null) {
            return null;
        }
        try {
            Class sourceClazz = source.getClass();
            Object result = sourceClazz.newInstance();
            BeanUtils.copyProperties(source, result);
            return (T) result;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
