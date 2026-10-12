package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedianOfStreamTest {
    @Test
    void tracksMedianAfterEachInsertionForTypicalStream() {
        MedianOfStream stream = new MedianOfStream();

        stream.insertNum(35);
        assertEquals(35.0, stream.findMedian());
        stream.insertNum(22);
        assertEquals(28.5, stream.findMedian());
        stream.insertNum(30);
        assertEquals(30.0, stream.findMedian());
        stream.insertNum(25);
        assertEquals(27.5, stream.findMedian());
        stream.insertNum(1);
        assertEquals(25.0, stream.findMedian());
    }

    @Test
    void handlesNegativeValuesAndDuplicates() {
        MedianOfStream stream = new MedianOfStream();

        stream.insertNum(-5);
        assertEquals(-5.0, stream.findMedian());
        stream.insertNum(-1);
        assertEquals(-3.0, stream.findMedian());
        stream.insertNum(-1);
        assertEquals(-1.0, stream.findMedian());
        stream.insertNum(-2);
        assertEquals(-1.5, stream.findMedian());
    }

    @Test
    void computesMedianForLargeAscendingInput() {
        MedianOfStream stream = new MedianOfStream();

        for (int value = 1; value <= 1_000; value++) {
            stream.insertNum(value);
        }

        assertEquals(500.5, stream.findMedian());
    }
}
