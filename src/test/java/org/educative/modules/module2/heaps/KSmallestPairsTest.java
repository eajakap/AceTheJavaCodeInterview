package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KSmallestPairsTest {
    @Test
    void returnsEmptyListForEmptyInputsOrZeroK() {
        KSmallestPairs solver = new KSmallestPairs();

        assertTrue(solver.kSmallestPairs(new int[0], new int[]{1, 2}, 3).isEmpty());
        assertTrue(solver.kSmallestPairs(new int[]{1, 2}, new int[0], 3).isEmpty());
        assertTrue(solver.kSmallestPairs(new int[]{1, 2}, new int[]{3, 4}, 0).isEmpty());
    }

    @Test
    void returnsPairsWithSmallestSumsForTypicalInput() {
        KSmallestPairs solver = new KSmallestPairs();

        List<List<Integer>> result = solver.kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3);

        assertEquals(List.of(List.of(1, 2), List.of(1, 4), List.of(1, 6)), result);
    }

    @Test
    void preservesDuplicatePairsWhenMultipleSmallestPairsRepeat() {
        KSmallestPairs solver = new KSmallestPairs();

        List<List<Integer>> result = solver.kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2);

        assertEquals(List.of(List.of(1, 1), List.of(1, 1)), result);
    }

    @Test
    void handlesNegativeValuesAndKGreaterThanAvailablePairs() {
        KSmallestPairs solver = new KSmallestPairs();

        List<List<Integer>> result = solver.kSmallestPairs(new int[]{-1, 0, 2}, new int[]{-2, 3}, 10);

        assertEquals(
                sortPairs(List.of(
                        List.of(-1, -2),
                        List.of(0, -2),
                        List.of(2, -2),
                        List.of(-1, 3),
                        List.of(0, 3),
                        List.of(2, 3)
                )),
                sortPairs(result)
        );
    }

    private List<List<Integer>> sortPairs(List<List<Integer>> pairs) {
        return pairs.stream()
                .map(List::copyOf)
                .sorted(Comparator.comparingInt((List<Integer> pair) -> pair.get(0))
                        .thenComparingInt(pair -> pair.get(1)))
                .toList();
    }
}
