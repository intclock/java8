/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */
package com.tasktoys.java8ws.mikan.ch8.ex01;

/**
 *
 * @author mikan
 */
public class UnsignedInt {

    private final int value;

    public UnsignedInt() {
        value = 0;
    }

    public UnsignedInt(int value) {
        this.value = value;
    }

    public long add(int value) {
        return Integer.toUnsignedLong(this.value) + Integer.toUnsignedLong(value);
    }

    public long sub(int value) {
        return Integer.toUnsignedLong(this.value) - Integer.toUnsignedLong(value);
    }

    public int div(int value) {
        return Integer.divideUnsigned(this.value, value);
    }

    public int compare(int value) {
        return Long.compare(Integer.toUnsignedLong(this.value), Integer.toUnsignedLong(value));
    }

    public int getIntValue() {
        return value;
    }
}
