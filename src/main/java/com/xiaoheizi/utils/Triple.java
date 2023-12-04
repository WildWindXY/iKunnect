package com.xiaoheizi.utils;

import java.io.Serializable;

public class Triple<A, B, C> implements Serializable {
    private final A first;
    private final B second;
    private final C third;

    private final String name;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.name = null;
    }

    public Triple(A first, B second, C third, String name) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.name = name;
    }

    public A first() {
        return first;
    }

    public B second() {
        return second;
    }

    public C third() {
        return third;
    }

    @Override
    public String toString() {
        return name == null ? "Triple<" + "first=" + first + ", second=" + second + ", third=" + third + ">" : name;
    }
}