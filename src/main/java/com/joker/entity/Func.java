package com.joker.entity;

public interface Func<T, R> {

    R apply(T t);

}