package org.educative.modules.module3.challenges;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Demonstrates several approaches for finding repeated values in an array.
 */
public class DuplicatesInArray {
    /**
     * Finds duplicates by comparing every pair of array positions.
     */
    static class BruteForceDuplicates {
        /**
         * Compares each element with all later elements and records equal values.
         *
         * <p>A value that occurs {@code k} times is included {@code k * (k - 1) / 2}
         * times because every matching pair is recorded.</p>
         *
         * @param arr the array to inspect
         * @return the list of matching values, with one entry per matching pair
         */
        public static ArrayList< Integer > findDuplicates(int arr[]) {
            int arrSize = arr.length;
            ArrayList < Integer > duplicates = new ArrayList < Integer > ();

            // Start the second scan after i so each pair is examined only once.
            for (int i = 0; i < arrSize; i++) {
                for (int j = i + 1; j < arrSize; j++) {
                    if (arr[i] == arr[j] && i != j)
                        duplicates.add(arr[i]);
                }
            }
            return duplicates;
        }

        /**
         * Runs examples of the pairwise comparison approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {
            int arr[] = {7, 5, 4, 3, 5, 11, 7, 11, 3, 11};
            System.out.println("Brute Force Approach:");
            ArrayList< Integer > dupes = findDuplicates(arr);
            System.out.println("Duplicates in " + Arrays.toString(arr) + " are " + dupes);

            int arr1[] = {6, 5, 17, 17};
            dupes = findDuplicates(arr1);
            System.out.println("Duplicates in " + Arrays.toString(arr1) + " are " + dupes);
        }

    }

    /**
     * Finds duplicates by keeping track of values already encountered in a set.
     */
    static class HashSetDuplicates {
        /**
         * Records each occurrence of a value after its first occurrence.
         *
         * @param arr the array to inspect
         * @return the list of duplicate occurrences in encounter order
         */
        public static ArrayList< Integer > findDuplicates(int arr[]) {
            int arrSize = arr.length;
            ArrayList < Integer > duplicates = new ArrayList < Integer > ();
            java.util.HashSet< Integer > set = new java.util.HashSet< Integer > ();

            for (int i = 0; i < arrSize; i++) {
                if (set.contains(arr[i])) {
                    duplicates.add(arr[i]);
                } else {
                    // Remember first occurrences so subsequent ones are recognized.
                    set.add(arr[i]);
                }
            }
            return duplicates;
        }

        /**
         * Runs examples of the set-based approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {
            int arr[] = {7, 5, 4, 3, 5, 11, 7, 11, 3, 11};
            System.out.println("Hash Set Approach:");
            ArrayList< Integer > dupes = findDuplicates(arr);
            System.out.println("Duplicates in " + Arrays.toString(arr) + " are " + dupes);

            int arr1[] = {6, 5, 17, 17};
            dupes = findDuplicates(arr1);
            System.out.println("Duplicates in " + Arrays.toString(arr1) + " are " + dupes);
        }

    }

    /**
     * Finds duplicate occurrences by tracking values in a map.
     */
    static class HashMapDuplicates {
        /**
         * Adds a value to the result whenever it has already appeared in the input.
         *
         * @param arr the array to inspect
         * @return the list of duplicate occurrences in encounter order
         */
        public static ArrayList< Integer > findDuplicates(int arr[]) {
            int arrSize = arr.length;
            ArrayList < Integer > duplicates = new ArrayList < Integer > ();
            java.util.HashMap< Integer, Integer > map = new java.util.HashMap< Integer, Integer > ();

            // The map's keys identify values already seen in the input.
            for (int i = 0; i < arrSize; i++) {
                if (map.containsKey(arr[i])) {
                    duplicates.add(arr[i]);
                } else {
                    map.put(arr[i], 1);
                }
            }
            return duplicates;
        }

        /**
         * Runs examples of the map-based approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {
            int arr[] = {7, 5, 4, 3, 5, 11, 7, 11, 3, 11};
            System.out.println("Hash Map Approach:");
            ArrayList< Integer > dupes = findDuplicates(arr);
            System.out.println("Duplicates in " + Arrays.toString(arr) + " are " + dupes);

            int arr1[] = {6, 5, 17, 17};
            dupes = findDuplicates(arr1);
            System.out.println("Duplicates in " + Arrays.toString(arr1) + " are " + dupes);
        }

    }

    /**
     * Finds duplicates using the array itself as a marker table.
     */
    static class InPlaceDuplicates {
        /**
         * Marks visited values by negating the array entry at the corresponding index.
         *
         * <p>Every input value must be in the range {@code 1..arr.length}. The input
         * array is restored before this method returns. Each occurrence after the first
         * is included in the result.</p>
         *
         * @param arr the array to inspect and temporarily modify
         * @return the list of duplicate occurrences in encounter order
         * @throws IllegalArgumentException if any value is outside {@code 1..arr.length}
         */
        public static ArrayList< Integer > findDuplicates(int arr[]) {
            int arrSize = arr.length;
            ArrayList < Integer > duplicates = new ArrayList < Integer > ();

            for (int value : arr) {
                if (value < 1 || value > arrSize)
                    throw new IllegalArgumentException(
                            "In-place approach requires values in 1.." + arrSize + ", found " + value);
            }

            // Index value v at v - 1; a negative entry means v has been seen already.
            for (int i = 0; i < arrSize; i++) {
                int index = Math.abs(arr[i]) - 1;
                if (arr[index] < 0) {
                    duplicates.add(Math.abs(arr[i]));
                } else {
                    arr[index] = -arr[index];
                }
            }

            // Remove the temporary markers so callers retain their original input.
            for (int i = 0; i < arrSize; i++)
                arr[i] = Math.abs(arr[i]);

            return duplicates;
        }

        /**
         * Runs examples of the in-place approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {
            System.out.println("In-Place Approach:");
            int arr[] = {7, 5, 4, 3, 5, 10, 7, 10, 3, 10};
            ArrayList< Integer > dupes = findDuplicates(arr);
            System.out.println("Duplicates in " + Arrays.toString(arr) + " are " + dupes);

            int arr1[] = {4, 2, 3, 3};
            dupes = findDuplicates(arr1);
            System.out.println("Duplicates in " + Arrays.toString(arr1) + " are " + dupes);
        }

    }

    public static void main(String[] args) {
        BruteForceDuplicates.main(args);
        HashSetDuplicates.main(args);
        HashMapDuplicates.main(args);
        InPlaceDuplicates.main(args);
    }
}
