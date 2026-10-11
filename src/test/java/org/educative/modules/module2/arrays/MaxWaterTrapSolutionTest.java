package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxWaterTrapSolutionTest {
    private final MaxWaterTrapSolution solution = new MaxWaterTrapSolution();

    @Test
    void maxAreaFindsLargestContainer() {
        assertEquals(49, solution.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        assertEquals(1, solution.maxArea(new int[]{1, 1}));
        assertEquals(16, solution.maxArea(new int[]{4, 3, 2, 1, 4}));
    }

    @Test
    void maxAreaIsZeroForTooFewBars() {
        assertEquals(0, solution.maxArea(new int[]{}));
        assertEquals(0, solution.maxArea(new int[]{5}));
    }

    @Test
    void maxWaterComputesTrappedRainWater() {
        assertEquals(6, solution.maxWater(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        assertEquals(19, solution.maxWater(new int[]{2, 9, 5, 3, 7, 4, 9, 6, 8}));
        assertEquals(9, solution.maxWater(new int[]{4, 2, 0, 3, 2, 5}));
    }

    @Test
    void maxWaterIsZeroWhenNothingCanBeTrapped() {
        assertEquals(0, solution.maxWater(new int[]{}));
        assertEquals(0, solution.maxWater(new int[]{1, 2, 3, 4}));
        assertEquals(0, solution.maxWater(new int[]{3, 3, 3}));
    }
}
