package org.educative.modules.module1.big_o_notation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Distinct3DigitsInRangeTest {
    private final Distinct3DigitsInRange solution = new Distinct3DigitsInRange();

    @Test
    void sampleRange() {
        assertEquals(3, solution.solution(876, 890));
    }

    @Test
    void singleNumberWithDistinctDigits() {
        assertEquals(1, solution.solution(123, 123));
    }

    @Test
    void singleNumberWithRepeatedDigits() {
        assertEquals(0, solution.solution(100, 100));
        assertEquals(0, solution.solution(121, 121));
        assertEquals(0, solution.solution(112, 112));
        assertEquals(0, solution.solution(999, 999));
    }

    @Test
    void fullRangeHas648DistinctNumbers() {
        assertEquals(648, solution.solution(100, 999));
    }

    @Test
    void rangeWithNoDistinctNumbers() {
        assertEquals(0, solution.solution(110, 110));
        assertEquals(0, solution.solution(111, 111));
    }

    @Test
    void firstHundredBlock() {
        assertEquals(72, solution.solution(100, 199));
    }

    @Test
    void boundariesAreInclusive() {
        assertEquals(2, solution.solution(102, 103));
        assertEquals(1, solution.solution(102, 102));
    }
}
