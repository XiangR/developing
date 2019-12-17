package com.joker.entity;

public interface FuncWithException<T> {
    T invoke() throws Exception;
}