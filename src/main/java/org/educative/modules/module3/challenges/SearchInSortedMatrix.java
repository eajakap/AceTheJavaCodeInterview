package org.educative.modules.module3.challenges;

/**
 * Demonstrates several approaches for searching for a value in a matrix.
 */
public class SearchInSortedMatrix {

    /**
     * Searches every matrix cell without relying on any ordering.
     */
    static class BruteForceSearchMatrix {
        /**
         * Checks each cell in the specified matrix dimensions for the target.
         *
         * @param matrix the matrix to search
         * @param numberOfRows the number of rows to inspect
         * @param numberOfColumns the number of columns to inspect in each row
         * @param target the value to find
         * @return {@code true} if the target is present, otherwise {@code false}
         */
        public static Object findKey(int[][] matrix, int numberOfRows, int numberOfColumns, int target) {
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfColumns; j++) {
                    if (matrix[i][j] == target)
                        return true;
                }
            }
            return false;
        }

        /**
         * Runs examples of the brute-force matrix search.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int[][] matrix = {
                    {10, 11, 12, 13},
                    {14, 15, 16, 17},
                    {27, 29, 30, 31},
                    {32, 33, 39, 50}
            };
            // Example 1
            System.out.println("Brute Force Search Approach:");
            Object x = findKey(matrix, 4, 4, 80);
            System.out.println("Search for 80 returned: " + x);

            // Example 2
            x = findKey(matrix, 4, 4, 15);
            System.out.println("Search for 15 returned: " + x);
        }
    }

    /**
     * Searches from the bottom-left corner of a row- and column-sorted matrix.
     */
    static class MovingDirectionsUpSearchMatrix {
        /**
         * Moves right or up to eliminate a row or column at each step.
         *
         * <p>Each row and column must be sorted in ascending order.</p>
         *
         * @param matrix the matrix to search
         * @param numberOfRows the number of rows to inspect
         * @param numberOfColumns the number of columns to inspect in each row
         * @param target the value to find
         * @return {@code true} if the target is present, otherwise {@code false}
         */
        public static Object findKey(int[][] matrix, int numberOfRows, int numberOfColumns, int target) {
            int row = numberOfRows - 1;
            int col = 0;

            while (row >= 0 && col < numberOfColumns) {
                if (matrix[row][col] == target) {
                    return true;
                }
                if (target > matrix[row][col]) {
                    // This column is too small at its bottom, so move to the next column.
                    col++; // Move right
                } else {
                    // This row is too large at its left edge, so move to the row above.
                    row--; // Move up
                }
            }
            return false;
        }

        /**
         * Runs examples of the bottom-left search.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {

            int[][] matrix = {
                    {10, 11, 12, 13},
                    {14, 15, 16, 17},
                    {27, 29, 30, 31},
                    {32, 33, 39, 50}
            };

            // Example 1
            System.out.println("Moving Directions Up Search Approach:");
            Object x = findKey(matrix, 4, 4, 80);
            System.out.println("Search for 80 returned: " + x);

            // Example 2
            x = findKey(matrix, 4, 4, 15);
            System.out.println("Search for 15 returned: " + x);
        }
    }

    /**
     * Searches from the top-right corner of a row- and column-sorted matrix.
     */
    static class MovingDirectionsDownSearchMatrix {
        /**
         * Moves down or left to eliminate a row or column at each step.
         *
         * <p>Each row and column must be sorted in ascending order.</p>
         *
         * @param matrix the matrix to search
         * @param numberOfRows the number of rows to inspect
         * @param numberOfColumns the number of columns to inspect in each row
         * @param target the value to find
         * @return {@code true} if the target is present, otherwise {@code false}
         */
        public static Object findKey(int[][] matrix, int numberOfRows, int numberOfColumns, int target) {
            int row = 0;
            int col = numberOfColumns - 1;

            while (row < numberOfRows && col >= 0) {
                if (matrix[row][col] == target) {
                    return true;
                }
                if (target > matrix[row][col]) {
                    // This row is too small at its right edge, so move to the row below.
                    row++; // Move down
                } else {
                    // This column is too large at its top, so move to the column on the left.
                    col--; // Move left
                }
            }
            return false;
        }

        /**
         * Runs examples of the top-right search.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {

            int[][] matrix = {
                    {10, 11, 12, 13},
                    {14, 15, 16, 17},
                    {27, 29, 30, 31},
                    {32, 33, 39, 50}
            };

            // Example 1
            System.out.println("Moving Directions Down Search Approach:");
            Object x = findKey(matrix, 4, 4, 80);
            System.out.println("Search for 80 returned: " + x);

            // Example 2
            x = findKey(matrix, 4, 4, 15);
            System.out.println("Search for 15 returned: " + x);
        }
    }

    /**
     * Searches a matrix that is sorted as one row-major sequence.
     */
    static class BinarySearchMatrix {
        /**
         * Binary-searches the matrix as if its rows were concatenated into one array.
         *
         * <p>For this ordering to be valid, every row must be sorted in ascending
         * order and the first value of each row must be greater than the last value
         * of the preceding row.</p>
         *
         * @param matrix the matrix to search
         * @param numberOfRows the number of rows to inspect
         * @param numberOfColumns the number of columns to inspect in each row
         * @param target the value to find
         * @return {@code true} if the target is present, otherwise {@code false}
         */
        public static Object findKey(int[][] matrix, int numberOfRows, int numberOfColumns, int target) {
            int min = 0;
            int max = numberOfRows * numberOfColumns - 1;

            while (min <= max)
            {
                int middle = (min + max) / 2;
                // Map the virtual one-dimensional index to its matrix coordinates.
                int row = middle / numberOfColumns;
                int col = middle % numberOfColumns;
                if (target < matrix[row][col])
                {
                    max = middle - 1;
                }
                else if (target > matrix[row][col])
                {
                    min = middle + 1;
                }
                else
                {
                    return true;
                }

            }
            return false;
        }

        /**
         * Runs examples of the row-major binary search.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int[][] matrix = {
                    {10, 11, 12, 13},
                    {14, 15, 16, 17},
                    {27, 29, 30, 31},
                    {32, 33, 39, 50}
            };

            // Example 1
            System.out.println("Binary Search Approach:");
            Object x = findKey(matrix, 4, 4, 80);
            System.out.println("Search for 80 returned: " + x);

            // Example 2
            x = findKey(matrix, 4, 4, 15);
            System.out.println("Search for 15 returned: " + x);
        }
    }

    /**
     * Runs the examples for all matrix-search approaches.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        BruteForceSearchMatrix.main(args);
        MovingDirectionsUpSearchMatrix.main(args);
        MovingDirectionsDownSearchMatrix.main(args);
        BinarySearchMatrix.main(args);
    }
}
