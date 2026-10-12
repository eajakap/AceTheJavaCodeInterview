package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LastStoneWeightMainTest {
    @Test
    void returnsExpectedWeightForTypicalInputs() {
        assertEquals(1, LastStoneWeightMain.lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}));
        assertEquals(2, LastStoneWeightMain.lastStoneWeight(new int[]{3, 7, 2}));
    }

    @Test
    void handlesEmptyAndSingleStoneInputs() {
        assertEquals(0, LastStoneWeightMain.lastStoneWeight(new int[0]));
        assertEquals(9, LastStoneWeightMain.lastStoneWeight(new int[]{9}));
    }

    @Test
    void crushesAllEqualStonesToZero() {
        assertEquals(0, LastStoneWeightMain.lastStoneWeight(new int[]{5, 5, 5, 5}));
    }

    @Test
    void handlesLargeValuesAndRepeatedResidualSmashes() {
        assertEquals(0, LastStoneWeightMain.lastStoneWeight(new int[]{1000, 999, 1, 1, 1}));
    }
}
