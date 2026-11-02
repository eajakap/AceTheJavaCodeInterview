package org.educative.modules.module3.challenges;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates several approaches for counting occurrences of a value in an array.
 */
public class CountOccurrences {
    /**
     * Counts occurrences by scanning every array element.
     */
    static class BruteForceCount {
        /**
         * Returns the number of elements equal to {@code key}.
         *
         * @param arr the array to inspect
         * @param key the value whose occurrences are counted
         * @return the number of times {@code key} appears in {@code arr}
         */
        public static int calcFreq(int arr[], int key) {
            int count = 0;
            // Inspect every element because the array is not required to be sorted.
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == key)
                    count++;
            }
            return count;
        }

        /**
         * Runs the brute-force counting example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int arr[] = {-5,-3,0,1,3,3,3,3,4,5};
            int key = 3;
            System.out.println("The key \"" + key + "\" occurs " + calcFreq(arr, key) + " times in the Array.");
        }
    }

    /**
     * Provides the binary search used by {@link BinarySearchCount}.
     */
    private static class Helper
    {
        /**
         * Searches the specified prefix of a sorted array for {@code key}.
         *
         * @param key the value to search for
         * @param arr the sorted array to search
         * @param arrSize the number of leading elements to search
         * @return an index containing {@code key}, or {@code -1} if it is absent
         */
        public int binarySearch(int key, int arr[], int arrSize)
        {
            int start = 0;
            int end = arrSize - 1;

            while (start <= end)
            {
                int mid = start + (end - start) / 2;

                if (key == arr[mid])
                    return mid;
                else if (key < arr[mid])
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            return -1; // If the element is not present in the array
        }
    }

    /**
     * Counts occurrences by finding one match, then scanning adjacent matches.
     */
    static class BinarySearchCount
    {
        /**
         * Counts {@code key} in a sorted array using binary search and local scans.
         *
         * @param arr the sorted array to inspect
         * @param key the value whose occurrences are counted
         * @return the number of times {@code key} appears in {@code arr}
         */
        public static int calcFreq(int[]arr, int key)
        {
            int arrSize = arr.length;
            Helper obj = new Helper();
            int index = obj.binarySearch(key, arr, arrSize);

            if (index == -1)   // If element is not present
                return 0;

            int count = 1; // Initialzing a count

            // Count consecutive matches to the left of the found index.
            int start = index - 1;
            while (start >= 0 && arr[start] == key) {
                count++;
                start--; // Decrement the start index if found
            }

            // Count consecutive matches to the right of the found index.
            int end = index + 1;
            while (end < arrSize && arr[end] == key) {
                count++;
                end++; // Increment the end index if found
            }
            return count;
        }

        /**
         * Runs the binary-search counting example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[])
        {
            int arr[] = {-5,-3,0,1,3,3,3,3,4,5};
            int key = 3;
            System.out.println("The key \""+ key + "\" occurs " + calcFreq(arr, key) + " times in the Array.");
        }
    }

    /**
     * Counts occurrences by binary-searching for the first and last matches.
     */
    static class ModifiedBinarySearchCountt
    {
        /**
         * Finds the first and last matching indices in a sorted array and calculates
         * the number of occurrences from their inclusive range.
         *
         * @param arr the sorted array to inspect
         * @param key the value whose occurrences are counted
         * @return the number of times {@code key} appears in {@code arr}
         */
        public static int calcFreq(int arr[], int key)
        {
            // Binary-search for the first occurrence, continuing left after a match.
            int arrSize = arr.length;
            int start = 0, end = arrSize - 1, mid, result = -1;
            while (start <= end) {
                mid = (start + end) / 2;
                if (arr[mid] == key) {
                    result = mid;
                    end = mid - 1;
                } else if (arr[mid] > key) {
                    end = mid - 1;
                } else if (arr[mid] < key) {
                    start = mid + 1;
                }
            }

            // Binary-search for the last occurrence, continuing right after a match.
            int start1 = 0, end1 = arrSize - 1, mid1, result1 = -1;
            while (start1 <= end1) {
                mid1 = (start1 + end1) / 2;
                if (arr[mid1] == key) {
                    result1 = mid1;
                    start1 = mid1 + 1;
                } else if (arr[mid1] > key) {
                    end1 = mid1 - 1;
                } else if (arr[mid1] < key) {
                    start1 = mid1 + 1;
                }
            }

            // If the key is absent, both boundaries remain -1 and the count is zero.
            if (result == -1 || result1 == -1)
                return (result1 - result);

            return (result1 - result + 1);
        }

        /**
         * Runs the two-boundary binary-search counting example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[])
        {
            int arr[] = {-5,-3,0,1,3,3,3,3,4,5};
            int key = 3;
            System.out.println("The key \""+ key + "\" occurs " + calcFreq(arr, key) + " times in the Array.");
        }
    }

    /**
     * Counts occurrences by building a map from values to their frequencies.
     */
    static class HashMapCount {
        /**
         * Builds a frequency map for the array and looks up {@code key}.
         *
         * @param arr the array to inspect
         * @param key the value whose occurrences are counted
         * @return the stored occurrence count for {@code key}
         * @throws NullPointerException if {@code key} is absent from the array
         */
        public static int calcFreq(int arr[], int key) {
            Map<Integer, Integer> countMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                // Start unseen values at one and increment existing frequencies.
                countMap.merge(arr[i], 1, Integer::sum);
            }
            // The Integer is unboxed to int, so a missing key causes a NullPointerException.
            return countMap.get(key);
        }

        /**
         * Runs the map-based counting example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
                int arr[] = {-5,-3,0,1,3,3,3,3,4,5};
                int key = 3;
                System.out.println("The key \"" + key + "\" occurs " + calcFreq(arr, key) + " times in the Array.");
        }
    }

    /**
     * Runs the examples for the counting approaches.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        BruteForceCount.main(args);
        BinarySearchCount.main(args);
        ModifiedBinarySearchCountt.main(args);
        HashMapCount.main(args);
    }
}
