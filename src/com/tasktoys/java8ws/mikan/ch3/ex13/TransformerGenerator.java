/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */

package com.tasktoys.java8ws.mikan.ch3.ex13;


import com.tasktoys.java8ws.mikan.ch3.ex13.TransformerApp.ColorTransformer;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mikan
 */
public class TransformerGenerator {

    private TransformerGenerator() {
        // static use only
    }

    public static ColorTransformer createBlur(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Illegal size: " + size);
        }
        return (x, y, image) -> {
            int total = 0;
            Pixel result = new Pixel();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Pixel pixel = Pixel.ofNullable(image, x + i - 1, y + i - 1);
                    if (pixel != null) {
                        total++;
                        result.add(pixel);
                    }
                }
            }
            return result.div(total).toColor();
        };
    }

    public static ColorTransformer createEdgeDetection() {
        return (x, y, image) -> {
            Pixel base = Pixel.of(image, x, y);
            Objects.requireNonNull(base);
            Map<PixelPosition, Pixel> map = new EnumMap<>(PixelPosition.class);
            map.put(PixelPosition.NORTH, Pixel.ofNullable(image, x, y + 1));
            map.put(PixelPosition.SOUTH, Pixel.ofNullable(image, x, y - 1));
            map.put(PixelPosition.EAST, Pixel.ofNullable(image, x + 1, y));
            map.put(PixelPosition.WEST, Pixel.ofNullable(image, x - 1, y));
            final AtomicInteger total = new AtomicInteger();
            Pixel result = new Pixel();
            map.forEach((k, v) -> {
                if (v != null) {
                    total.incrementAndGet();
                    result.add(v);
                }
            });
            return base.edge(total.get(), result).toColor();
        };
    }

    private enum PixelPosition {
        NORTH, EAST, SOUTH, WEST;
    }
}
