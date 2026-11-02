package org.educative.modules.module3.challenges;

import java.util.Arrays;

/**
 * Demonstrates ways to calculate the median of two sorted arrays.
 */
public class MedianOfTwoSortedArrays {

    /**
     * Finds the median by merging values logically until the middle is reached.
     */
    static class BruteForceMedian {
        /**
         * Reads the arrays in sorted order without creating a merged array.
         *
         * @param array1 the first sorted array
         * @param array2 the second sorted array
         * @return the median of the combined values
         */
        public static double getMedian(int array1[], int array2[]) {
            int sizeOfArray1 = array1.length;
            int sizeOfArray2 = array2.length;
            int arraySize = sizeOfArray1 + sizeOfArray2;
            int arraySizeMid = arraySize / 2;

            int i = 0;
            int j = 0;
            int count;

            int median = -1;
            int previousMedian = -1;

            // Advance through the merged ordering only as far as the middle position.
            for (count = 0; count <= arraySizeMid; count++) {
                // Keep the preceding value for the even-sized median calculation.
                previousMedian = median;
                if (i != sizeOfArray1 && j != sizeOfArray2) {
                    if (array1[i] > array2[j]) {
                        median = array2[j];
                        j++;
                    } else {
                        median = array1[i];
                        i++;
                    }
                } else if (i < sizeOfArray1) {
                    median = array1[i];
                    i++;
                } else {
                    median = array2[j];
                    j++;
                }
            }
            if (arraySize % 2 == 1) {
                return median;
            } else {
                return (median + previousMedian) / 2.0;
            }
        }

        /**
         * Runs examples of the partial-merge approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int array1[] = {4, 5, 7, 10};
            int array2[] = {12, 13, 15, 18, 20, 24};

            System.out.println("The median of " + Arrays.toString(array1) + " and " + Arrays.toString(array2) + " is " + getMedian(array1, array2));

            // Example 2
            int array3[] = {1, 2, 3, 4, 5};
            int array4[] = {100, 200, 300, 400};

            System.out.println("The median of " + Arrays.toString(array3) + " and " + Arrays.toString(array4) + " is " + getMedian(array3, array4));
        }
    }

    /**
     * Finds the median by fully merging the two input arrays.
     */
    static class MergedArrayMedian {
        /**
         * Merges the sorted inputs and calculates the median of the result.
         *
         * <p>If both inputs are empty, this implementation returns {@code 0.0}.
         * If only one input is empty, the median is calculated from the other.</p>
         *
         * @param array1 the first sorted array
         * @param array2 the second sorted array
         * @return the median of the combined values, or {@code 0.0} if both arrays are empty
         */
        public static double getMedian(int array1[], int array2[]) {
            // Handle the empty-input cases before allocating the merged array.
            if (array1.length == 0 && array2.length == 0) {
                return 0.0;
            }
            if (array1.length == 0) {
                return getMedianOfSingleArray(array2);
            }
            if (array2.length == 0) {
                return getMedianOfSingleArray(array1);
            }

            // Merge in sorted order by repeatedly taking the smaller current value.
            int[] mergedArray = new int[array1.length + array2.length];
            int i = 0, j = 0, k = 0;
            while (i < array1.length && j < array2.length) {
                if (array1[i] < array2[j]) {
                    mergedArray[k++] = array1[i++];
                } else {
                    mergedArray[k++] = array2[j++];
                }
            }
            // Append whichever input still has unconsumed values.
            while (i < array1.length) {
                mergedArray[k++] = array1[i++];
            }
            while (j < array2.length) {
                mergedArray[k++] = array2[j++];
            }
            return getMedianOfSingleArray(mergedArray);
        }

        /**
         * Calculates the median of one non-empty, sorted array.
         *
         * @param array the sorted values
         * @return the middle value for an odd length, or the mean of the two middle values for an even length
         */
        private static double getMedianOfSingleArray(int[] array) {
            int n = array.length;
            if (n % 2 == 0) {
                // An even-sized array has two central values.
                return (array[n / 2 - 1] + array[n / 2]) / 2.0;
            } else {
                // An odd-sized array has one central value.
                return array[n / 2];
            }
        }

        /**
         * Runs an example of the full-merge approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {
            int[] array1 = {4, 5, 7, 10};
            int[] array2 = {12, 13, 15, 18, 20, 24};

            System.out.println("The median of " + Arrays.toString(array1) + " and " + Arrays.toString(array2) + " is " + getMedian(array1, array2));
        }
    }

    public static void main(String[] args) {
        BruteForceMedian.main(args);
        MergedArrayMedian.main(args);
    }
}
