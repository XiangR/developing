package com.yit.test.extend;

import org.hamcrest.Matchers;

public class MyMatchers extends Matchers {

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @param itemMatchers the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(org.hamcrest.Matcher<? super E>... itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each logically equal to the
     * corresponding item in the specified items.  For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     *
     * @param items the items that must equal the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> contains(E... items) {
        return org.hamcrest.collection.IsIterableContainingInOrder.<E>contains(items);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     */
    @SafeVarargs
    public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T>... matchers) {
        return org.hamcrest.core.AllOf.<T>allOf(matchers);
    }

}
