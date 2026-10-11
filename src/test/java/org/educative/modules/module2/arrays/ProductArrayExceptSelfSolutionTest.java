package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ProductArrayExceptSelfSolutionTest {
    @Test
    void returnsProductOfAllOtherElements() {
        assertArrayEquals(new int[]{24, 12, 8, 6},
                ProductArrayExceptSelfSolution.productExceptSelf(new int[]{1, 2, 3, 4}));
    }

    @Test
    void handlesSingleZero() {
        assertArrayEquals(new int[]{0, 8, 0, 0},
                ProductArrayExceptSelfSolution.productExceptSelf(new int[]{1, 0, 4, 2}));
    }

    @Test
    void handlesMultipleZerosAndNegatives() {
        assertArrayEquals(new int[]{0, 0, 0},
                ProductArrayExceptSelfSolution.productExceptSelf(new int[]{0, 2, 0}));
        assertArrayEquals(new int[]{-6, 3, -2},
                ProductArrayExceptSelfSolution.productExceptSelf(new int[]{-1, 2, -3}));
    }

    @Test
    void handlesEmptyArray() {
        assertArrayEquals(new int[]{}, ProductArrayExceptSelfSolution.productExceptSelf(new int[]{}));
    }
}
