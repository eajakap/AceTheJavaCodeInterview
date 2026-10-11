package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestConsectiveSolutionTest {
    private final LongestConsectiveSolution solution = new LongestConsectiveSolution();

    @Test
    void longestConsecutiveCountsLongestRun() {
        assertEquals(4, solution.longestConsecutive(new int[]{10, 5, 12, 3, 55, 11, 13, 4}));
        assertEquals(6, solution.longestConsecutive(new int[]{-3, -2, -1, 0, 1, 5, 6, 7, 8, 9, 10}));
        assertEquals(4, solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }

    @Test
    void longestConsecutiveHandlesEdgeCases() {
        assertEquals(0, solution.longestConsecutive(new int[]{}));
        assertEquals(1, solution.longestConsecutive(new int[]{7}));
        assertEquals(1, solution.longestConsecutive(new int[]{7, 7, 7}));
        assertEquals(2, solution.longestConsecutive(new int[]{1000000000, -1000000000, 0, 999999999, -999999999}));
    }

    @Test
    void longestConsecutiveSeriesReturnsTheRunInOrder() {
        assertArrayEquals(new int[]{10, 11, 12, 13},
                solution.longestConsecutiveSeries(new int[]{10, 5, 12, 3, 55, 11, 13, 4}));
        assertArrayEquals(new int[]{5, 6, 7, 8, 9, 10},
                solution.longestConsecutiveSeries(new int[]{-3, -2, -1, 0, 1, 5, 6, 7, 8, 9, 10}));
    }

    @Test
    void longestConsecutiveSeriesHandlesEdgeCases() {
        assertArrayEquals(new int[]{}, solution.longestConsecutiveSeries(new int[]{}));
        assertArrayEquals(new int[]{7}, solution.longestConsecutiveSeries(new int[]{7}));
    }

    @Test
    void mainPrintsResultsForAllCases() {
        String output = ConsoleOutputCapture.capture(() -> LongestConsectiveSolution.main(new String[0]));

        assertEquals(true, output.contains("\tResult: 4"));
        assertEquals(true, output.contains("\tLongest Consecutive Series: [10, 11, 12, 13]"));
        assertEquals(true, output.contains("\tResult: 0"));
    }
}
