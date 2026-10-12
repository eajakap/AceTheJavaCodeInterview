package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovingAverageTest {
    private static final double DELTA = 1e-9;

    @Test
    void averagesOverSlidingWindow() {
        MovingAverage average = new MovingAverage(2);
        assertEquals(4.0, average.next(4), DELTA);
        assertEquals(6.0, average.next(8), DELTA);
        assertEquals(7.0, average.next(6), DELTA);
        assertEquals(8.0, average.next(10), DELTA);
    }

    @Test
    void averageUsesAvailableValuesBeforeWindowFills() {
        MovingAverage average = new MovingAverage(5);
        assertEquals(1.0, average.next(1), DELTA);
        assertEquals(1.5, average.next(2), DELTA);
        assertEquals(2.0, average.next(3), DELTA);
    }

    @Test
    void windowOfOneReturnsEachValue() {
        MovingAverage average = new MovingAverage(1);
        assertEquals(7.0, average.next(7), DELTA);
        assertEquals(-3.0, average.next(-3), DELTA);
        assertEquals(5.0, average.next(5), DELTA);
    }

    @Test
    void windowLargerThanStreamAveragesEverything() {
        MovingAverage average = new MovingAverage(100);
        average.next(2);
        average.next(4);
        assertEquals(4.0, average.next(6), DELTA);
    }

    @Test
    void handlesNegativeAndLargeValues() {
        MovingAverage average = new MovingAverage(3);
        assertEquals(100000.0, average.next(100000), DELTA);
        assertEquals(0.0, average.next(-100000), DELTA);
        assertEquals(50000.0 / 3, average.next(50000), DELTA);
        assertEquals(-100000.0 / 3, average.next(-50000), DELTA);
        assertEquals(0.0, average.next(0), DELTA);
    }
}
