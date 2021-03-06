/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */
package com.tasktoys.java8ws.mikan.ch1.ex07;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author mikan
 */
public class AndThenTest {

    @Test
    public void testAndThen_normalInput() {
        String[] results = new String[2];
        AndThen.andThen(() -> results[0] = "1", () -> results[1] = "2").run();
        assertThat(Arrays.asList(results), is(Arrays.asList("1", "2")));
    }

    @Test
    public void testAndThenWithThread_normalInput() {
        String[] results = new String[2];
        AndThen.andThenWithThread(() -> results[0] = "1", () -> results[1] = "2").run();
        assertThat(results[0], is("1"));
    }
}
