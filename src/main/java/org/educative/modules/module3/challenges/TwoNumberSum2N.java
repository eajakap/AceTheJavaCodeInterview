package org.educative.modules.module3.challenges;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates three approaches for finding two array values whose sum equals a target.
 */
public class TwoNumberSum2N {
    /**
     * Finds a matching pair by checking every pair of distinct array positions.
     */
    static class BruteForceCheckSum {
        /**
         * Searches all pairs in the input array for values that sum to {@code n}.
         *
         * @param arr the array of values to search
         * @param n the target sum
         * @return the first matching pair, or a zero-initialized two-element array if none is found
         */
        public static int[] findSum(int[] arr, int n) {
            int[] result = new int[2];
            // Compare each element with the elements that follow it.
            for (int i = 0; i < arr.length; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] + arr[j] == n) {
                        result[0] = arr[i];
                        result[1] = arr[j];
                        return result;
                    }
                }
            }
            return result;
        }

        /**
         * Runs the brute-force example with a sample array and target.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int n = 9;
            int[] arr1 = {2, 4, 5, 7, 8};
            int[] arr2 = findSum(arr1, n);
            int num1 = arr2[0];
            int num2 = arr2[1];

            if ((num1 + num2) != n)
                System.out.println("Results not found!");
            else
                System.out.println("Sum of " + n + " found: " + num1 + " and " + num2);
        }
    }

    /**
     * Finds a matching pair by tracking the complements needed to reach the target.
     */
    static class HashMapCheckSum {
        /**
         * Searches for two values that sum to {@code n} using a hash map.
         *
         * @param arr the array of values to search
         * @param n the target sum
         * @return the first matching pair, or a zero-initialized two-element array if none is found
         */
        public static int[] findSum(int[] arr, int n) {
            int[] result = new int[2];
            Map<Integer, Boolean> hmap = new HashMap<Integer, Boolean>();

            for (int i = 0; i < arr.length; i++) {
                // A previously stored complement matches the current value.
                if (hmap.containsKey(arr[i])) {
                    result[0] = arr[i];
                    result[1] = n - arr[i];
                    return result;
                } else {
                    // Store the value needed to pair with the current array element.
                    hmap.put(n - arr[i], true);
                }
            }
            return result;
        }

        /**
         * Runs the hash-map example with a sample array and target.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {

            int n = 9;
            int[] arr1 = {2, 4, 5, 7, 8};
            int[] arr2 = findSum(arr1, n);
            int num1 = arr2[0];
            int num2 = arr2[1];

            if ((num1 + num2) != n)
                System.out.println("Results not found!");
            else
                System.out.println("Sum of " + n + " found: " + num1 + " and " + num2);
        }
    }

    /**
     * Finds a matching pair by sorting the array and moving two pointers inward.
     */
    static class ArraySortCheckSum {
        /**
         * Sorts the input array and searches for two values that sum to {@code n}.
         *
         * @param arr the array of values to sort and search; its order is changed
         * @param n the target sum
         * @return the first matching pair found, or a zero-initialized two-element array if none is found
         */
        public static int[] findSum(int[] arr, int n) {
            int[] result = new int[2];
            Arrays.sort(arr);
            int left = 0, right = arr.length - 1;
            while (left < right) {
                int sum = arr[left] + arr[right];
                if (sum == n) {
                    result[0] = arr[left];
                    result[1] = arr[right];
                    break;
                } else if (sum > n) {
                    // The largest value is too large, so try a smaller one.
                    right--;
                } else {
                    // The smallest value is too small, so try a larger one.
                    left++;
                }
            }
            return result;
        }

        /**
         * Runs the sorting example with a sample array and target.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {

            int n = 9;
            int[] arr1 = {2, 4, 5, 7, 8};
            int[] arr2 = findSum(arr1, n);
            int num1 = arr2[0];
            int num2 = arr2[1];

            if ((num1 + num2) != n)
                System.out.println("Results not found!");
            else
                System.out.println("Sum of " + n + " found: " + num1 + " and " + num2);
        }
    }

}