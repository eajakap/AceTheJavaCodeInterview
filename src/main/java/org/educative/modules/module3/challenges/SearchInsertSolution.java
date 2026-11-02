package org.educative.modules.module3.challenges;

/**
 * Demonstrates approaches for finding where a target belongs in a sorted array.
 */
public class SearchInsertSolution {

    /**
     * Finds an insertion position by scanning the array from left to right.
     */
    static class BruteForceSearchInsert {
        /**
         * Returns the target's index if present, or the index where it should be inserted.
         *
         * @param arr the array sorted in ascending order
         * @param target the value to find or insert
         * @return the target's index or its insertion position; returns {@code 0} for an empty array
         */
        public static int insertPosition(int arr[], int target)
        {
            // Track the position after the last value smaller than the target.
            int expectedIndex = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == target) {
                    return i;
                } else if (arr[i] < target) {
                    expectedIndex = i + 1;
                } else {
                    // Sorted order means the target belongs before this value.
                    break;
                }
            }
            return expectedIndex;
        }

        /**
         * Runs examples of the linear-scan approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[])
        {
            int[]arr = {0, 1, 2, 3, 5, 6};

            // Example 1
            System.out.println("Brute Force Approach:");
            System.out.println("Index to Insert " + "\"5\" is " + insertPosition(arr, 5));

            // Example 2
            System.out.println("Index to Insert " + "\"3\" is " + insertPosition(arr, 3));

            // Example 3
            System.out.println("Index to Insert " + "\"7\" is " + insertPosition(arr, 7));
        }
    }

    /**
     * Finds an insertion position by binary-searching the sorted array.
     */
    class ModifiedBinarySearchInsert {
        /**
         * Returns the target's index if present, or the index where it should be inserted.
         *
         * @param arr the array sorted in ascending order
         * @param target the value to find or insert
         * @return the target's index or insertion position; returns {@code -1} for an empty array
         */
        public static int insertPosition(int[] arr, int target)
        {
            int arrSize = arr.length;
            // This implementation uses -1 to indicate that an empty array has no position.
            if(arrSize < 1)
                return -1;
            int start = 0;
            int end = arrSize - 1;
            int mid = 0, pos = 0;
            while(start <= end)
            {
                // Calculate the midpoint without adding start and end directly.
                mid = start + (end-start)/2;
                if(arr[mid] == target) {
                    return mid;
                } else if(arr[mid] > target) {
                    // The target belongs at or before mid, so discard the right half.
                    end = mid - 1;
                    pos = mid;
                } else {
                    // The target belongs after mid, so discard the left half.
                    start = mid + 1;
                    pos = mid + 1;
                }
            }
            return pos;
        }

        /**
         * Runs examples of the binary-search approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[])
        {
            int[]arr = {0, 1, 2, 3, 5, 6};

            // Example 1
            System.out.println("Modified Binary Search Approach:");
            System.out.println("Index to Insert " + "\"5\" is " + insertPosition(arr, 5));

            // Example 2
            System.out.println("Index to Insert " + "\"3\" is " + insertPosition(arr, 3));

            // Example 3
            System.out.println("Index to Insert " + "\"7\" is " + insertPosition(arr, 7));
        }
    }
    /**
     * Runs examples of both approaches.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        BruteForceSearchInsert.main(args);
        ModifiedBinarySearchInsert.main(args);
    }
}
