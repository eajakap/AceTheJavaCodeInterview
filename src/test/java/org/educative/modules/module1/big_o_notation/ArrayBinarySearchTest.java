package org.educative.modules.module1.big_o_notation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayBinarySearchTest {
    private static final int[] SORTED = {-22, -15, 1, 7, 20, 35, 55};

    @Test
    void findsPresentValues() {
        assertTrue(ArrayBinarySearch.binarySearch(SORTED, -15));
        assertTrue(ArrayBinarySearch.binarySearch(SORTED, 35));
        assertTrue(ArrayBinarySearch.binarySearch(SORTED, 1));
    }

    @Test
    void findsFirstAndLastElements() {
        assertTrue(ArrayBinarySearch.binarySearch(SORTED, -22));
        assertTrue(ArrayBinarySearch.binarySearch(SORTED, 55));
    }

    @Test
    void missingValuesAreNotFound() {
        assertFalse(ArrayBinarySearch.binarySearch(SORTED, 8888));
        assertFalse(ArrayBinarySearch.binarySearch(SORTED, -100));
        assertFalse(ArrayBinarySearch.binarySearch(SORTED, 8));
    }

    @Test
    void emptyArrayNeverContainsValue() {
        assertFalse(ArrayBinarySearch.binarySearch(new int[0], 1));
    }

    @Test
    void singleElementArray() {
        assertTrue(ArrayBinarySearch.binarySearch(new int[]{5}, 5));
        assertFalse(ArrayBinarySearch.binarySearch(new int[]{5}, 4));
    }

    @Test
    void arrayWithDuplicates() {
        assertTrue(ArrayBinarySearch.binarySearch(new int[]{1, 2, 2, 2, 3}, 2));
        assertFalse(ArrayBinarySearch.binarySearch(new int[]{1, 2, 2, 2, 3}, 4));
    }

    @Test
    void everyElementOfLargeArrayIsFound() {
        int[] array = new int[1001];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 2;
        }
        for (int i = 0; i < array.length; i++) {
            assertTrue(ArrayBinarySearch.binarySearch(array, i * 2));
            assertFalse(ArrayBinarySearch.binarySearch(array, i * 2 + 1));
        }
    }

    @Test
    void handlesExtremeIntValues() {
        int[] array = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertTrue(ArrayBinarySearch.binarySearch(array, Integer.MIN_VALUE));
        assertTrue(ArrayBinarySearch.binarySearch(array, Integer.MAX_VALUE));
    }
}
