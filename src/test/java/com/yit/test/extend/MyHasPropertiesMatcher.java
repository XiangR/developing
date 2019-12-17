package com.yit.test.extend;

import org.hamcrest.*;

import java.lang.reflect.Field;

import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

/**
 * Created by xiangrui on 2017/4/19.
 */

/**
 * 解决hamcrest不支持没有get set方法的类的问题，自定义matcher，检验Field属性
 * @param <T>
 */
public class MyHasPropertiesMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final String propertyName;
    private final Matcher<Object> valueMatcher;

    public MyHasPropertiesMatcher(String propertyName, Matcher<?> valueMatcher) {
        this.propertyName = propertyName;
        this.valueMatcher = nastyGenericsWorkaround(valueMatcher);
    }

    @Override
    protected boolean matchesSafely(T bean, Description mismatch) {
        try {
            return propertyOn(bean, mismatch)
                    .and(withPropertyValue(bean))
                    .matching(valueMatcher, "property '" + propertyName + "' ");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Condition.Step<Field, Object> withPropertyValue(final T bean) {
        return (field, mismatch) -> {
            try {
                field.setAccessible(true);
                return matched(field.get(bean), mismatch);
            } catch (Exception e) {
                mismatch.appendText(e.getMessage());
                return notMatched();
            }
        };
    }


    private Condition<Field> propertyOn(T bean, Description mismatch) throws NoSuchFieldException {
        Field field = bean.getClass().getDeclaredField(propertyName);
        return matched(field, mismatch);
    }

    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> valueMatcher) {
        return (Matcher<Object>) valueMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasPropertyExt(").appendValue(propertyName).appendText(", ")
                .appendDescriptionOf(valueMatcher).appendText(")");

    }

    @Factory
    public static <T> Matcher<T> hasPropertyExt(String propertyName, Matcher<?> valueMatcher) {
        return new MyHasPropertiesMatcher<>(propertyName, valueMatcher);
    }

}
