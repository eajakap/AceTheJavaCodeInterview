package org.codesignal.tutorials.arrays.multidimensional;

import java.util.List;

/**
 * Class to find the maximum hourglass sum in a 2D array.
 * An hourglass sum is defined as the sum of array elements that form an hourglass shape.
 * An hourglass always uses a 3 × 3 area of the array and has the following pattern:
 * a b c
 *   d
 * e f g
 * where a, b, c, d, e, f, g are the elements of the array.
 * The Hourglass Sum problem is about finding the maximum sum of any hourglass pattern inside a 2D array.
 * Steps to find the maximum hourglass sum:
 * 1. Iterate through the 2D array, considering only the elements that can form an hourglass (i.e., elements that are not on the edges).
 * 2. For each valid center element, calculate the hourglass sum by summing the values of the hourglass shape.
 * 3. Keep track of the maximum hourglass sum found during the iteration.
 * 4. Return the maximum hourglass sum after checking all possible hourglass positions.
 * Pattern: 2D matrix traversal with a fixed pattern/window
 * Time complexity: O(n*m), where n is the number of rows and m is the number of columns in the 2D array,
 * since we need to check each possible hourglass position
 * Space complexity: O(1), as we are using a constant amount of extra space for variables to store the maximum sum and the current sum.
 */
public class HourGlassMax {

    /**
     * Method to calculate the maximum hourglass sum in a 2D array.
     * This method iterates through the 2D array, calculates the hourglass sum for each valid center element,
     * and keeps track of the maximum hourglass sum found.
     * Time complexity: O(n*m), where n is the number of rows and m is the number of columns in the 2D array,
     * since we need to check each possible hourglass position.
     * Space complexity: O(1), as we are using a constant amount of extra space for variables to store the maximum sum and the current sum.
     * Pattern: 2D matrix traversal with a fixed pattern/window
     * @param arr A 2D array of integers representing the input matrix.
     * @return The maximum hourglass sum found in the 2D array.
     */
    public static int hourglassSum(int[][] arr) {
        int maxSum = Integer.MIN_VALUE; // Initialize to the smallest possible integer value

        // Iterate through the 2D array, avoiding the edges
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 1; j < arr[i].length - 1; j++) {
                // Calculate the hourglass sum centered at arr[i][j]
                int currentSum = arr[i][j] + arr[i - 1][j - 1] + arr[i - 1][j] + arr[i - 1][j + 1]
                        + arr[i + 1][j - 1] + arr[i + 1][j] + arr[i + 1][j + 1];

                // Update maxSum if the current hourglass sum is greater
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }

    /**
     * Overloaded method to calculate the maximum hourglass sum from a List of Lists.
     * This method is useful when working with dynamic data structures like List<List<Integer>>.
     * The logic is similar to the 2D array version, but it uses List methods to access elements.
     * Time complexity: O(n*m), where n is the number of rows and m is the number of columns in the 2D array,
     * since we need to check each possible hourglass position.
     * Space complexity: O(1), as we are using a constant amount of extra space for variables to store the maximum sum and the current sum.
     * Pattern: 2D matrix traversal with a fixed pattern/window
     * @param arr A List of Lists representing the 2D array.
     * @return The maximum hourglass sum found in the 2D array.
     */
    public static int hourglassSum(List<List<Integer>> arr) {

        /*
                Matrix (R rows x C cols), window = 3x3 hourglass
                   ↓
                Init maxSum = Integer.MIN_VALUE   (values may be negative)
                   ↓
                Pick top-left anchor (row, col)
                   - row: 0 .. R-3, col: 0 .. C-3  (window must fit fully)
                   ↓
                Apply fixed shape (offsets from anchor)
                   (0,0) (0,1) (0,2)
                         (1,1)
                   (2,0) (2,1) (2,2)
                   ↓
                Calculate result -> sum of the 7 cells
                   ↓
                Update best      -> maxSum = max(maxSum, sum)
                   ↓
                Move right       -> col++
                   ↓
                When row finishes, move down -> row++, col = 0
                   ↓
                All anchors visited -> return maxSum
         */
        int maxSum = Integer.MIN_VALUE;
        for (int row = 0; row <= arr.size() - 3; row++) {
            for (int col = 0; col <= arr.get(row).size() - 3; col++) {
                int sum = arr.get(row).get(col)
                                + arr.get(row).get(col + 1)
                                + arr.get(row).get(col + 2)
                                + arr.get(row + 1).get(col + 1)
                                + arr.get(row + 2).get(col)
                                + arr.get(row + 2).get(col + 1)
                                + arr.get(row + 2).get(col + 2);
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[][] arr = {
                {1, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0},
                {0, 0, 2, 4, 4, 0},
                {0, 0, 0, 2, 0, 0},
                {0, 0, 1, 2, 4, 0}
        };

        int result = hourglassSum(arr);
        System.out.println("Maximum hourglass sum: " + result); // Output: Maximum hourglass sum: 19
        List<List<Integer>> arrList = List.of(
                List.of(1, 1, 1, 0, 0, 0),
                List.of(0, 1, 0, 0, 0, 0),
                List.of(1, 1, 1, 0, 0, 0),
                List.of(0, 0, 2, 4, 4, 0),
                List.of(0, 0, 0, 2, 0, 0),
                List.of(0, 0, 1, 2, 4, 0)
        );
        int resultList = hourglassSum(arrList);
        System.out.println("Maximum hourglass sum from List: " + resultList); // Output: Maximum hourglass sum from List: 19
    }
}
