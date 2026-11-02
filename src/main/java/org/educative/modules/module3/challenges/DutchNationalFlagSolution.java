package org.educative.modules.module3.challenges;

import java.util.Arrays;

/**
 * Demonstrates sorting an array of Dutch National Flag values (0, 1, and 2).
 */
public class DutchNationalFlagSolution {

    /**
     * Sorts the values using selection sort.
     */
    static class BruteForceDutchFlag {
        /**
         * Sorts {@code arr} in ascending order in place.
         *
         * @param arr an array containing values to sort
         * @return the same array, sorted in ascending order
         */
        public static int[] selectionSort(int[] arr) {
            int size = arr.length;
            int index = 0;
            for (int i = 0; i < size; i++) {
                // Find the smallest value in the unsorted suffix.
                index = find_min(arr, i, size);
                // Put that value into its final position at i.
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
            return arr;
        }

        /**
         * Finds the index of the smallest value in the half-open range {@code [start, end)}.
         *
         * @param arr the array to inspect
         * @param start the first index in the range
         * @param end the exclusive end index of the range
         * @return the index of a minimum value in the range
         */
        private static int find_min(int[] arr, int start, int end) {
            int min = arr[start];
            int index = start;

            for (int i = start; i < end; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                    index = i;
                }
            }
            return index;
        }

        /**
         * Runs an example of selection sort.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int[] arr = {2, 0, 0, 1, 2, 1};
            System.out.println("Brute Force Approach:");
            System.out.println(Arrays.toString(selectionSort(arr)));
        }
    }

    /**
     * Sorts values 0, 1, and 2 by partitioning the array into three regions.
     */
    static class ThreeWayPartitionDutchFlag {
        /**
         * Sorts the 0s, 1s, and 2s in {@code arr} in place.
         *
         * @param arr an array containing only {@code 0}, {@code 1}, and {@code 2}
         * @return the same array, sorted in ascending order
         */
        public static int[] sortColors(int[] arr) {
            int low = 0;
            int mid = 0;
            int high = arr.length - 1;

            // Maintain regions of 0s, 1s, unknown values, and 2s using the three pointers.
            while (mid <= high) {
                switch (arr[mid]) {
                    case 0:
                        // Move this 0 into the low region; the mid region advances too.
                        swap(arr, low, mid);
                        low++;
                        mid++;
                        break;
                    case 1:
                        // A 1 is already in the middle region.
                        mid++;
                        break;
                    case 2:
                        // Move this 2 into the high region; inspect the swapped-in value next.
                        swap(arr, mid, high);
                        high--;
                        break;
                }
            }
            return arr;
        }

        /**
         * Swaps the values at indices {@code i} and {@code j} in {@code arr}.
         *
         * @param arr the array to modify
         * @param i the first index
         * @param j the second index
         */
        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        /**
         * Runs an example of the three-way partition approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int[] arr = {2, 0, 0, 1, 2, 1};
            System.out.println("Three-Way Partition Approach:");
            System.out.println(Arrays.toString(sortColors(arr)));
        }
    }

    /**
     * Runs the examples for both sorting approaches.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        BruteForceDutchFlag.main(args);
        ThreeWayPartitionDutchFlag.main(args);
    }
}
