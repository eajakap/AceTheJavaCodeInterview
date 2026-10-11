package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArrayBuySellMaxProfitTest {
    private final ArrayBuySellMaxProfit solution = new ArrayBuySellMaxProfit();

    @Test
    void returnsMaximumProfitAcrossDays() {
        assertEquals(2, solution.maxProfit(new int[]{2, 4, 1}));
        assertEquals(4, solution.maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(7, solution.maxProfit(new int[]{10, 8, 2, 9, 1, 7}));
    }

    @Test
    void returnsZeroWhenNoProfitIsPossible() {
        assertEquals(0, solution.maxProfit(new int[]{3, 3, 3, 3, 3}));
        assertEquals(0, solution.maxProfit(new int[]{5, 4, 3, 2, 1}));
        assertEquals(0, solution.maxProfit(new int[]{5}));
    }

    @Test
    void emptyPricesAreRejected() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> solution.maxProfit(new int[]{}));
    }
}
