package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSlidingWindowTest {
    private final MaxSlidingWindow solution = new MaxSlidingWindow();

    @Test
    void windowOfOneReturnsInput() {
        assertArrayEquals(new int[]{9, 8, 7, 6}, solution.maxSlidingWindow(new int[]{9, 8, 7, 6}, 1));
    }

    @Test
    void decreasingInput() {
        assertArrayEquals(new int[]{4, 3, 2}, solution.maxSlidingWindow(new int[]{4, 3, 2, 1}, 2));
    }

    @Test
    void increasingInput() {
        assertArrayEquals(new int[]{3, 4, 5}, solution.maxSlidingWindow(new int[]{1, 2, 3, 4, 5}, 3));
    }

    @Test
    void negativeValues() {
        assertArrayEquals(new int[]{-1, -3, -2, -2}, solution.maxSlidingWindow(new int[]{-1, -3, -5, -2, -4}, 2));
    }

    @Test
    void duplicateValues() {
        assertArrayEquals(new int[]{5, 5, 5}, solution.maxSlidingWindow(new int[]{5, 5, 5, 5, 5}, 3));
    }

    @Test
    void windowEqualToLengthReturnsSingleMax() {
        assertArrayEquals(new int[]{7}, solution.maxSlidingWindow(new int[]{3, 7, 1}, 3));
    }

    @Test
    void mixedValues() {
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7},
                solution.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void largeWindowOverLongInput() {
        int[] nums = new int[1000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        int[] result = solution.maxSlidingWindow(nums, 10);

        assertEquals(991, result.length);
        assertEquals(9, result[0]);
        assertEquals(999, result[990]);
    }

    @Test
    void emptyInputOrOversizedWindowGivesEmptyResult() {
        assertArrayEquals(new int[]{}, solution.maxSlidingWindow(new int[]{}, 2));
        assertArrayEquals(new int[]{}, solution.maxSlidingWindow(new int[]{1, 2}, 3));
    }
}
