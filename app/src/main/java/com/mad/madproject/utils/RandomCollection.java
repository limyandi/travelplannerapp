package com.mad.madproject.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * The algorithm class to create the possibility of places type to go.
 */
public class RandomCollection {
    private final NavigableMap<Double, String> map = new TreeMap<Double, String>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    /**
     * Add instances
     * @param weight the weight of each type
     * @param result the result
     * @return a RandomCollection instance
     */
    public RandomCollection add(double weight, String result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    /**
     * Get the string value to be used
     * @return the string value (result).
     */
    public String next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
