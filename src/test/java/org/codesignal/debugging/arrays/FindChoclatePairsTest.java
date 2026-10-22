package org.codesignal.debugging.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FindChoclatePairsTest {
    private final FindChoclatePairs finder = new FindChoclatePairs();

    @Test
    void returnsPairsOrderedByFirstValue() {
        assertArrayEquals(
                new int[][] {{3, -3}, {4, -4}, {5, -5}},
                finder.findChocPairs(new int[] {4, 3, -5, 5, -3, -4}).toArray(new int[0][])
        );
    }

    @Test
    void returnsOnlyDisjointPairsWhenValuesRepeat() {
        assertArrayEquals(
                new int[][] {{0, 0}, {2, -2}},
                finder.findChocPairs(new int[] {2, 0, -2, 0, 0}).toArray(new int[0][])
        );
    }

    @Test
    void returnsEmptyArrayWhenThereAreNoPairs() {
        assertArrayEquals(new int[0][], finder.findChocPairs(new int[] {1, 2, 3}).toArray(new int[0][]));
    }

    @Test
    void doesNotModifyInput() {
        int[] input = {3, -3, 2, -2};
        int[] original = input.clone();

        finder.findChocPairs(input);

        assertArrayEquals(original, input);
    }
}
