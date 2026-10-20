package org.codesignal.multidimensional.arrays;

import org.codesignal.tutorials.arrays.multidimensional.MatrixBoundaryConcatenation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixBoundaryConcatenationTest {

    @Test
    void extractsRequestedLayersInClockwiseOrder() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        assertEquals(
                List.of(1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10),
                MatrixBoundaryConcatenation.extract(matrix, 2));
    }

    @Test
    void extractsSingleRowAndSingleColumnWithoutDuplicates() {
        assertEquals(
                List.of(1, 2, 3, 4),
                MatrixBoundaryConcatenation.extract(new int[][]{{1, 2, 3, 4}}, 2));
        assertEquals(
                List.of(1, 2, 3, 4),
                MatrixBoundaryConcatenation.extract(new int[][]{{1}, {2}, {3}, {4}}, 2));
    }

    @Test
    void returnsEmptyForInvalidInputOrNoRequestedLayers() {
        assertEquals(List.of(), MatrixBoundaryConcatenation.extract(null, 1));
        assertEquals(List.of(), MatrixBoundaryConcatenation.extract(new int[0][], 1));
        assertEquals(List.of(), MatrixBoundaryConcatenation.extract(new int[][]{{1}}, 0));
    }
}
