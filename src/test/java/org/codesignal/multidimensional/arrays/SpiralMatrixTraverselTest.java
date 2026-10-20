package org.codesignal.multidimensional.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SpiralMatrixTraverselTest {

    private final SpiralMatrixTraversel traversal = new SpiralMatrixTraversel();

    @Test
    void returnsVowelPositionsInClockwiseSpiralOrder() {
        char[][] grid = {
                {'a', 'b', 'c', 'd'},
                {'e', 'f', 'g', 'h'},
                {'i', 'j', 'k', 'l'}
        };

        assertArrayEquals(new int[]{0, 8, 9}, traversal.spiralTraverseAndVowels(grid));
    }

    @Test
    void handlesSingleRowsAndColumns() {
        assertArrayEquals(new int[]{0, 2},
                traversal.spiralTraverseAndVowels(new char[][]{{'a', 'b', 'e'}}));
        assertArrayEquals(new int[]{1, 2},
                traversal.spiralTraverseAndVowels(new char[][]{{'b'}, {'e'}, {'i'}}));
    }

    @Test
    void handlesSingleCellAndGridsWithoutVowels() {
        assertArrayEquals(new int[]{0},
                traversal.spiralTraverseAndVowels(new char[][]{{'u'}}));
        assertArrayEquals(new int[0],
                traversal.spiralTraverseAndVowels(new char[][]{{'b', 'c'}, {'d', 'f'}}));
    }

    @Test
    void handlesEmptyGrid() {
        assertArrayEquals(new int[0], traversal.spiralTraverseAndVowels(new char[0][]));
    }
}
