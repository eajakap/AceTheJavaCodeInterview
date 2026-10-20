package org.codesignal.multidimensional.arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatrixBoundaryConcatenation {

    public static List<Integer> extract(int[][] matrix, int n) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }

        int rows = matrix.length, cols = matrix[0].length;
        List<Integer> values = new ArrayList<>();

        for (int layer = 0; layer < n; layer++) {
            int top = layer;
            int bottom = rows - 1 - layer;
            int left = layer;
            int right = cols - 1 - layer;

            if (top > bottom || left > right) {
                break;
            }

            values.addAll(extractLayer(matrix, top, bottom, left, right));
        }

        return values;
    }

    private static List<Integer> extractLayer(
            int[][] matrix, int top, int bottom, int left, int right) {
        List<Integer> values = new ArrayList<>();
        extractTopRow(matrix, top, left, right, values);
        extractRightColumn(matrix, right, top + 1, bottom, values);

        if (top < bottom) {
            extractBottomRow(matrix, bottom, left, right - 1, values);
        }
        if (left < right) {
            extractLeftColumn(matrix, left, bottom - 1, top + 1, values);
        }
        return values;
    }

    private static void extractTopRow(
            int[][] matrix, int row, int startColumn, int endColumn, List<Integer> values) {
        for (int col = startColumn; col <= endColumn; col++) {
            values.add(matrix[row][col]);
        }
    }

    private static void extractRightColumn(
            int[][] matrix, int col, int startRow, int endRow, List<Integer> values) {
        for (int row = startRow; row <= endRow; row++) {
            values.add(matrix[row][col]);
        }
    }

    private static void extractBottomRow(
            int[][] matrix, int row, int startColumn, int endColumn, List<Integer> values) {
        for (int col = endColumn; col >= startColumn; col--) {
            values.add(matrix[row][col]);
        }
    }

    private static void extractLeftColumn(
            int[][] matrix, int col, int startRow, int endRow, List<Integer> values) {
        for (int row = startRow; row >= endRow; row--) {
            values.add(matrix[row][col]);
        }
    }

    public static List<Integer> matrixBoundaryConcatenation(
                int[][] matrixA,
                int[][] matrixB,
                int n) {
        List<Integer> boundaryListA = extract(matrixA, n);
        List<Integer> boundaryListB = extract(matrixB, n);
        List<Integer> concatenatedList = new ArrayList<>();
        concatenatedList.addAll(boundaryListA);
        concatenatedList.addAll(boundaryListB);
        return concatenatedList;
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println("_".repeat(100));
        System.out.println("Inputs:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrixA = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        int[][] matrixB = {
                {17, 18, 19, 20},
                {21, 22, 23, 24},
                {25, 26, 27, 28},
                {29, 30, 31, 32}
        };

        printMatrix(matrixA);
        printMatrix(matrixB);

        List<Integer> result = matrixBoundaryConcatenation(matrixA, matrixB, 2);
        System.out.println(result);
    }
}
