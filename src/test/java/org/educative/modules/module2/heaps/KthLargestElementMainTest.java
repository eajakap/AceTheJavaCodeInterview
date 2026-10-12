package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthLargestElementMainTest {
    @Test
    void kthLargestElement2ReturnsExpectedValuesForTypicalCases() {
        assertEquals(5, KthLargestElementMain.kthLargestElement2(2, new int[]{3, 2, 1, 5, 6, 4}));
        assertEquals(5, KthLargestElementMain.kthLargestElement2(4, new int[]{9, 4, 7, 1, -2, 6, 5}));
    }

    @Test
    void kthLargestElement2HandlesDuplicatesNegativesAndEmptyInput() {
        assertEquals(5, KthLargestElementMain.kthLargestElement2(1, new int[]{5, 5, 5, 5}));
        assertEquals(-3, KthLargestElementMain.kthLargestElement2(3, new int[]{-1, -2, -3, -4}));
        assertEquals(0, KthLargestElementMain.kthLargestElement2(1, new int[0]));
    }

    @Test
    void streamingConstructorAndAddTrackKthLargestAcrossUpdates() {
        KthLargestElementMain kthLargest = new KthLargestElementMain(3, new int[]{4, 5, 8, 2});

        assertEquals(4, kthLargest.add(3));
        assertEquals(5, kthLargest.add(5));
        assertEquals(5, kthLargest.add(10));
        assertEquals(8, kthLargest.add(9));
        assertEquals(8, kthLargest.add(4));
    }
}
