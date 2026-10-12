package org.educative.modules.module1.big_o_notation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayLinearSearchTest {
    private static final int[] UNSORTED = {20, 35, -15, 7, 55, 1, -22};

    @Test
    void findsPresentValues() {
        assertTrue(ArrayLinearSearch.linearSearch(UNSORTED, -15));
        assertTrue(ArrayLinearSearch.linearSearch(UNSORTED, 1));
        assertTrue(ArrayLinearSearch.linearSearch(UNSORTED, -22));
    }

    @Test
    void findsFirstAndLastElements() {
        assertTrue(ArrayLinearSearch.linearSearch(UNSORTED, 20));
        assertTrue(ArrayLinearSearch.linearSearch(UNSORTED, -22));
    }

    @Test
    void missingValuesAreNotFound() {
        assertFalse(ArrayLinearSearch.linearSearch(UNSORTED, 8888));
        assertFalse(ArrayLinearSearch.linearSearch(UNSORTED, 0));
    }

    @Test
    void emptyArrayNeverContainsValue() {
        assertFalse(ArrayLinearSearch.linearSearch(new int[0], 1));
    }

    @Test
    void singleElementArray() {
        assertTrue(ArrayLinearSearch.linearSearch(new int[]{3}, 3));
        assertFalse(ArrayLinearSearch.linearSearch(new int[]{3}, 4));
    }

    @Test
    void arrayWithDuplicates() {
        assertTrue(ArrayLinearSearch.linearSearch(new int[]{2, 2, 2}, 2));
    }

    @Test
    void handlesExtremeIntValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertTrue(ArrayLinearSearch.linearSearch(array, Integer.MIN_VALUE));
        assertFalse(ArrayLinearSearch.linearSearch(array, 0));
    }
}
