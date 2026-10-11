package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstMissingPostiveSolutionTest {
    private final FirstMissingPostiveSolution solution = new FirstMissingPostiveSolution();

    @Test
    void findsMissingValueInsideRange() {
        assertEquals(2, solution.firstMissingPositive(new int[]{3, 4, -1, 1}));
        assertEquals(3, solution.firstMissingPositive(new int[]{1, 2, 0}));
    }

    @Test
    void returnsNextValueWhenAllPresent() {
        assertEquals(4, solution.firstMissingPositive(new int[]{3, 1, 2}));
    }

    @Test
    void ignoresNonPositiveAndTooLargeValues() {
        assertEquals(1, solution.firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
        assertEquals(1, solution.firstMissingPositive(new int[]{-5, 0}));
    }

    @Test
    void handlesDuplicatesAndEmptyInput() {
        assertEquals(2, solution.firstMissingPositive(new int[]{1, 1}));
        assertEquals(1, solution.firstMissingPositive(new int[]{}));
    }
}
