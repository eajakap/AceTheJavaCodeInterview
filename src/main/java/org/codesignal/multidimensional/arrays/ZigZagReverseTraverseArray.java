package org.codesignal.multidimensional.arrays;

public class ZigZagReverseTraverseArray {

    public static int[] zigZagReverseTraverse(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;

        for (int i = rows - 1; i >= 0; i--) {
            if ((rows - 1 - i) % 2 == 0) { // Even row from the bottom
                for (int j = cols - 1; j >= 0; j--) {
                    result[index++] = matrix[i][j];
                }
            } else { // Odd row from the bottom
                for (int j = 0; j < cols; j++) {
                    result[index++] = matrix[i][j];
                }
            }
        }
        System.out.print("ZigZag Reverse Traverse: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
        return result;
    }

    public static int[] rowTraverseFirstRowToLastRow(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // traverse the first row from left to right
        int[] result = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[index++] = matrix[i][j];
            }
        }
        // print the result array
        System.out.print("First Row Traverse: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
        return result;
    }

    public static int[] rowTraverseLastRowToFirstRow(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // traverse the last row from right to left
        int[] result = new int[rows * cols];
        int index = 0;
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                result[index++] = matrix[i][j];
            }
        }
        // print the result array
        System.out.print("Last Row Traverse: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
        return result;
    }

    public static int[] columnTraverseFirstColumnToLastColumn(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // traverse the column from the first row to the last row
        int[] result = new int[rows * cols];
        int index = 0;
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                result[index++] = matrix[i][j];
            }
        }
        // print the result array
        System.out.print("First Column Traverse: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
//        System.out.print("Reverse Column Traverse: ");
//        for (int i = 0; i < result.length; i++) {
//            System.out.print(result[i] + " ");
//        }
//        System.out.println();
//        for (int j = cols - 1; j >= 0; j--) {
//            for (int i = rows - 1; i >= 0; i--) {
//                result[index++] = matrix[i][j];
//            }
//        }
        return result;
    }

    public static int[] columnTraverseLastColumnToFirstColumn(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // traverse the column from the last row to the first row
        int[] result = new int[rows * cols];
        int index = 0;
        for (int j = cols - 1; j >= 0; j--) {
            for (int i = rows - 1; i >= 0; i--) {
                result[index++] = matrix[i][j];
            }
        }
        // print the result array
        System.out.print("Last Column Traverse: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        zigZagReverseTraverse(matrix);

        rowTraverseFirstRowToLastRow(matrix);

        rowTraverseLastRowToFirstRow(matrix);

        columnTraverseFirstColumnToLastColumn(matrix);

        columnTraverseLastColumnToFirstColumn(matrix);
    }

}
