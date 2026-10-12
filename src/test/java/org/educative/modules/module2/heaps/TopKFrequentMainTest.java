package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TopKFrequentMainTest {
    @Test
    void returnsMostFrequentNumbersForTypicalInput() {
        TopKFrequentMain solver = new TopKFrequentMain();

        assertArrayEquals(
                new int[]{1, 2},
                sortedCopy(solver.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2))
        );
    }

    @Test
    void handlesSingleElementAndEmptyResultRequests() {
        TopKFrequentMain solver = new TopKFrequentMain();

        assertArrayEquals(new int[]{7}, solver.topKFrequent(new int[]{7}, 1));
        assertArrayEquals(new int[0], solver.topKFrequent(new int[0], 0));
    }

    @Test
    void handlesNegativeValuesAndDistinctFrequencies() {
        TopKFrequentMain solver = new TopKFrequentMain();

        assertArrayEquals(
                new int[]{-1, 3},
                sortedCopy(solver.topKFrequent(new int[]{-1, -1, -1, 2, 2, 3, 3, 3, 3}, 2))
        );
    }

    @Test
    void handlesLargerInputWithTopThreeFrequencies() {
        TopKFrequentMain solver = new TopKFrequentMain();

        int[] nums = {
                10, 10, 10, 10, 10,
                20, 20, 20, 20,
                30, 30, 30,
                40, 40,
                50
        };

        assertArrayEquals(new int[]{10, 20, 30}, sortedCopy(solver.topKFrequent(nums, 3)));
    }

    private int[] sortedCopy(int[] values) {
        int[] copy = values.clone();
        Arrays.sort(copy);
        return copy;
    }
}
