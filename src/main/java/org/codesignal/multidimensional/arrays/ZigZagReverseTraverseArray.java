package org.codesignal.multidimensional.arrays;

/**
 * Provides row-wise and column-wise traversal orders for rectangular matrices.
 * Traversal methods return the visited values in a one-dimensional array and
 * print that array as a demonstration.
 */
public class ZigZagReverseTraverseArray {

    /**
     * Traverses rows from top to bottom, alternating the horizontal direction.
     * Even-indexed rows are visited left to right; odd-indexed rows are visited
     * right to left.
     *
     * @param matrix rectangular matrix to traverse
     * @return values in zig-zag row order, or an empty array for a null or empty matrix
     */
    public static int[] zigZagRowTraverse(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            if (i % 2 == 0) { // Even row from the top
                for (int j = 0; j < cols; j++) {
                    result[index++] = matrix[i][j];
                }
            } else { // Odd row from the top
                for (int j = cols - 1; j >= 0; j--) {
                    result[index++] = matrix[i][j];
                }
            }
        }
        System.out.print("ZigZag Row Traverse: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses rows from bottom to top, alternating the horizontal direction.
     * Starting at the bottom row, the traversal moves right to left, then
     * alternates between left to right and right to left.
     *
     * @param matrix rectangular matrix to traverse
     * @return values in reverse zig-zag row order, or an empty array for a null or empty matrix
     */
    public static int[] zigZagReverseRowTraverse(int[][] matrix) {
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
        System.out.print("ZigZag Reverse Row Traverse: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses every row from the first row to the last, visiting each row
     * from left to right.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in row-major order
     */
    public static int[] rowTraverseFirstRowToLastRow(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[index++] = matrix[i][j];
            }
        }
        System.out.print("Row Traverse: First Row to Last Row: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses rows from the last row to the first, visiting each row from
     * right to left.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in reverse row-major order
     */
    public static int[] rowTraverseLastRowToFirstRow(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                result[index++] = matrix[i][j];
            }
        }
        System.out.print("Row Traverse: Last Row to First Row: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses columns from the first column to the last, visiting each column
     * from top to bottom.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in column-major order
     */
    public static int[] columnTraverseFirstColumnToLastColumn(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                result[index++] = matrix[i][j];
            }
        }
        System.out.print("Column Traverse: First Column to Last Column: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses columns from the last column to the first, visiting each column
     * from bottom to top.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in reverse column-major order
     */
    public static int[] columnTraverseLastColumnToFirstColumn(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;
        for (int j = cols - 1; j >= 0; j--) {
            for (int i = rows - 1; i >= 0; i--) {
                result[index++] = matrix[i][j];
            }
        }
        System.out.print("Column Traverse: Last Column to First Column: ");
        printArray(result);
        return result;
    }

    /**
     * Traverses columns from left to right, alternating between top-to-bottom
     * and bottom-to-top directions. The first column is traversed top to bottom.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in zig-zag column order
     */
    public static int[] zigZagColumnTraverse(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        String direction = "down";
        int row = 0;
        int col = 0;
        int[] output = new int[rows * cols];
        int index = 0;

        while (index < rows * cols) {
            output[index++] = matrix[row][col];

            // Reverse direction at the current column's edge, then move to the next column.
            if (direction.equals("down")) {
                if (row + 1 == rows) {
                    direction = "up";
                    col += 1;
                } else {
                    row += 1;
                }
            } else {
                if (row - 1 < 0) {
                    direction = "down";
                    col += 1;
                } else {
                    row -= 1;
                }
            }
        }

        System.out.print("Zig Zag Column Traverse: ");
        printArray(output);
        return output;
    }

    /**
     * Traverses columns from right to left, alternating between bottom-to-top
     * and top-to-bottom directions. The last column is traversed bottom to top.
     *
     * @param matrix rectangular, non-empty matrix to traverse
     * @return values in reverse zig-zag column order
     */
    public static int[] zigZagReverseColumnTraverse(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        String direction = "up";
        int row = rows - 1;
        int col = cols - 1;
        int[] output = new int[rows * cols];
        int index = 0;

        while (index < rows * cols) {
            output[index++] = matrix[row][col];

            // Reverse direction at the current column's edge, then move left.
            if (direction.equals("up")) {
                if (row - 1 < 0) {
                    direction = "down";
                    col -= 1;
                } else {
                    row -= 1;
                }
            } else {
                if (row + 1 == rows) {
                    direction = "up";
                    col -= 1;
                } else {
                    row += 1;
                }
            }
        }

        System.out.print("Zig Zag Reverse Column Traverse: ");
        printArray(output);
        return output;
    }

    /**
     * Prints a matrix one row per line, with a space between adjacent values.
     *
     * @param matrix matrix to print
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints the values of an array on one line, separated by spaces.
     *
     * @param array array to print
     */
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * Runs the traversal demonstrations against a 4-by-4 matrix.
     */
    public static void testEvenMatrix() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        System.out.println("Even Matrix:");

        printMatrix(matrix);

        rowTraverseFirstRowToLastRow(matrix);

        rowTraverseLastRowToFirstRow(matrix);

        columnTraverseFirstColumnToLastColumn(matrix);

        columnTraverseLastColumnToFirstColumn(matrix);

        zigZagRowTraverse(matrix);

        zigZagReverseRowTraverse(matrix);

        zigZagColumnTraverse(matrix);

        zigZagReverseColumnTraverse(matrix);

    }

    /**
     * Runs the traversal demonstrations against a 3-by-4 matrix.
     */
    public static void testOddMatrix() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        System.out.println("Odd Matrix:");

        printMatrix(matrix);

        rowTraverseFirstRowToLastRow(matrix);

        rowTraverseLastRowToFirstRow(matrix);

        columnTraverseFirstColumnToLastColumn(matrix);

        columnTraverseLastColumnToFirstColumn(matrix);

        zigZagRowTraverse(matrix);

        zigZagReverseRowTraverse(matrix);

        zigZagColumnTraverse(matrix);

        zigZagReverseColumnTraverse(matrix);
    }

    /**
     * Runs the even- and odd-row-count matrix demonstrations.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println( "-".repeat(100));
        testEvenMatrix();
        System.out.println( "-".repeat(100));
        testOddMatrix();
    }

}
