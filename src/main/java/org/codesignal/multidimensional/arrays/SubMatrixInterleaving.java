package org.codesignal.multidimensional.arrays;

public class SubMatrixInterleaving {

        public static int[][] submatrixConcatenation(
                int[][] matrixA,
                int[][] matrixB,
                int[][] submatrixCoords) {

            int startRowA = submatrixCoords[0][0];
            int endRowA = submatrixCoords[0][1];
            int startColA = submatrixCoords[0][2];
            int endColA = submatrixCoords[0][3];
            int startRowB = submatrixCoords[1][0];
            int endRowB = submatrixCoords[1][1];
            int startColB = submatrixCoords[1][2];
            int endColB = submatrixCoords[1][3];

            int numRows = endRowA - startRowA + 1;
            int numColsA = endColA - startColA + 1;
            int numColsB = endColB - startColB + 1;

            int[][] submatrixA = new int[numRows][numColsA];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColsA; j++) {
                    submatrixA[i][j] = matrixA[startRowA + i][startColA + j];
                }
            }

            int[][] submatrixB = new int[numRows][numColsB];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColsB; j++) {
                    submatrixB[i][j] = matrixB[startRowB + i][startColB + j];
                }
            }

            // The part for the concatenation process
            int[][] resultMatrix = new int[numRows][numColsA + numColsB];
            for (int i = 0; i < numRows; i++) {
                int m = 0;
                for (int j = 0; j < numColsA; j++) {
                    resultMatrix[i][m] = submatrixA[i][j];
                    m += 2;
                }
                int n=1;
                for (int j = 0; j < numColsB; j++) {
                    resultMatrix[i][n] = submatrixB[i][j];
                    n += 2;
                }
            }

            // At this point, we have extracted submatrices from matrixA and matrixB
            return resultMatrix; // Placeholder return statement
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
                    {9, 10, 11, 12}
            };

            int[][] matrixB = {
                    {13, 14, 15},
                    {16, 17, 18},
                    {19, 20, 21}
            };

            int[][] submatrixCoords = {
                    {1, 2, 2, 3}, // Submatrix from matrixA: rows 1-2 and cols 2-3
                    {1, 2, 1, 2}  // Submatrix from matrixB: rows 1-2 and cols 1-2
            };

            printMatrix(matrixA);
            printMatrix(matrixB);
            printMatrix(submatrixCoords);

            int[][] result = submatrixConcatenation(matrixA, matrixB, submatrixCoords);
            printMatrix(result);
        }
}
