/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */
package com.tasktoys.java8ws.mikan.ch1.ex01;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(Theories.class)
public class ArraysSortTest {

    private static final int DATA_SIZE = 1 << 13; // Arrays.MIN_ARRAY_SORT_GRAN (private field)

    @DataPoint
    public static String[] small = {"test1", "test2", "test3"};

    @DataPoint
    public static String[] large = IntStream.rangeClosed(0, DATA_SIZE).mapToObj(i -> "test" + i).toArray(String[]::new);

    @Test(expected = NullPointerException.class)
    public void testDoSort_NPE() {
        new ArraysSort().doSort(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDoSortAsParallel_NPE() {
        new ArraysSort().doSortAsParallel(null);
    }

    @Theory
    public void testDoSort_allInput(String[] input) {
        ArraysSort arraysSort = new ArraysSort();
        List<Long> threadIds = arraysSort.doSort(input);
        assertThat(threadIds, everyItem(is(threadIds.get(0))));
    }

    @Theory
    public void testDoSortAsParallel_largeInput(String[] input) {
        assumeTrue(input.length > DATA_SIZE);
        ArraysSort arraysSort = new ArraysSort();
        List<Long> threadIds = arraysSort.doSortAsParallel(input);
        assertThat(threadIds, hasItem(not(threadIds.get(0))));
    }

    @Theory
    public void testDoSortAsParallel_smallInput(String[] input) {
        assumeTrue(input.length <= DATA_SIZE);
        ArraysSort arraysSort = new ArraysSort();
        List<Long> threadIds = arraysSort.doSortAsParallel(input);
        assertThat(threadIds, everyItem(is(threadIds.get(0)))); // Same as sequential when small input
    }
}

