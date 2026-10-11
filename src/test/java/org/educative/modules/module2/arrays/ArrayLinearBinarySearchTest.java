package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayLinearBinarySearchTest {
    @Test
    void linearSearchFindsFirstMatchInUnsortedArray() {
        int[] scores = {120, 135, 98, 210, 175, 210};

        assertEquals(3, ArrayLinearBinarySearch.linearSearch(scores, 210));
        assertEquals(0, ArrayLinearBinarySearch.linearSearch(scores, 120));
    }

    @Test
    void linearSearchReturnsMinusOneWhenMissing() {
        assertEquals(-1, ArrayLinearBinarySearch.linearSearch(new int[]{1, 2, 3}, 4));
        assertEquals(-1, ArrayLinearBinarySearch.linearSearch(new int[]{}, 1));
    }

    @Test
    void binarySearchFindsEveryElementInSortedArray() {
        int[] sorted = {120, 135, 140, 210, 275};

        for (int i = 0; i < sorted.length; i++) {
            assertEquals(i, ArrayLinearBinarySearch.binarySearch(sorted, sorted[i]));
        }
    }

    @Test
    void binarySearchReturnsMinusOneWhenMissing() {
        int[] sorted = {120, 135, 140, 210, 275};

        assertEquals(-1, ArrayLinearBinarySearch.binarySearch(sorted, 100));
        assertEquals(-1, ArrayLinearBinarySearch.binarySearch(sorted, 150));
        assertEquals(-1, ArrayLinearBinarySearch.binarySearch(sorted, 300));
        assertEquals(-1, ArrayLinearBinarySearch.binarySearch(new int[]{}, 1));
    }
}
