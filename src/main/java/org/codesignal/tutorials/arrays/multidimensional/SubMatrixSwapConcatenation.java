package org.codesignal.tutorials.arrays.multidimensional;

public class SubMatrixSwapConcatenation {

    public static void submatrixSwap(int[][] matrix, int[] coord_S1, int[] coord_S2) {
        int startRowA = coord_S1[0];
        int endRowA = coord_S1[1] - 1;
        int startColA = coord_S1[2];
        int endColA = coord_S1[3] - 1;
        int startRowB = coord_S2[0];
        int endRowB = coord_S2[1] - 1;
        int startColB = coord_S2[2];
        int endColB = coord_S2[3] - 1;

        int numRows = endRowA - startRowA + 1;
        int numColsA = endColA - startColA + 1;
        int numColsB = endColB - startColB + 1;

        int[][] submatrixA = new int[numRows][numColsA];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColsA; j++) {
                submatrixA[i][j] = matrix[startRowA + i][startColA + j];
            }
        }

        int[][] submatrixB = new int[numRows][numColsB];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColsB; j++) {
                submatrixB[i][j] = matrix[startRowB + i][startColB + j];
            }
        }

        // Put submatrixB into submatrixA's original location
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColsA; j++) {
                matrix[startRowA + i][startColA + j] = submatrixB[i][j];
            }
        }
        // Put submatrixA into submatrixB's original location
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColsB; j++) {
                matrix[startRowB + i][startColB + j] = submatrixA[i][j];
            }
        }
    }

    public static int[][] submatrixSwapConcatenation(int[][] matrix, int[][] submatrixCoords) {
        // extracted submatrix coordinates from matrix
        int [] submatrixA = submatrixCoords[0];
        int [] submatrixB = submatrixCoords[1];
        submatrixSwap(matrix, submatrixA, submatrixB);
        return matrix;
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
        int[][] matrix = {{1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};

        int[][] submatrixCoords = {
                {0, 2, 2, 4}, // Submatrix A coordinates: rows 0-1 and cols 2-3
                {2, 4, 0, 2}  // Submatrix B coordinates: rows 2-3 and cols 0-1
        };

        printMatrix(matrix);
        printMatrix(submatrixCoords);

        int[][] result = submatrixSwapConcatenation(matrix, submatrixCoords);
        printMatrix(result);
    }
}
