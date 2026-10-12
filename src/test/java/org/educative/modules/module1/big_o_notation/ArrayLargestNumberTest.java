package org.educative.modules.module1.big_o_notation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayLargestNumberTest {

    @Test
    void findsLargestInAscendingArray() {
        assertEquals(5, ArrayLargestNumber.largestNumber(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    void findsLargestInUnsortedArray() {
        assertEquals(9, ArrayLargestNumber.largestNumber(new int[]{3, 9, 2, 7}));
    }

    @Test
    void findsLargestWhenAllNegative() {
        assertEquals(-1, ArrayLargestNumber.largestNumber(new int[]{-5, -1, -8}));
    }

    @Test
    void singleElement() {
        assertEquals(42, ArrayLargestNumber.largestNumber(new int[]{42}));
        assertEquals(-42, ArrayLargestNumber.largestNumber(new int[]{-42}));
    }

    @Test
    void duplicateMaximums() {
        assertEquals(7, ArrayLargestNumber.largestNumber(new int[]{7, 7, 3, 7}));
    }

    @Test
    void emptyOrNullArrayReturnsZero() {
        assertEquals(0, ArrayLargestNumber.largestNumber(new int[0]));
        assertEquals(0, ArrayLargestNumber.largestNumber(null));
    }

    @Test
    void handlesExtremeIntValues() {
        assertEquals(Integer.MAX_VALUE,
                ArrayLargestNumber.largestNumber(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0}));
        assertEquals(Integer.MIN_VALUE, ArrayLargestNumber.largestNumber(new int[]{Integer.MIN_VALUE}));
    }
}
