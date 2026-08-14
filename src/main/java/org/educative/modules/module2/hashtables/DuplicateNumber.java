package org.educative.modules.module2.hashtables;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DuplicateNumber {
    public boolean containsDuplicate(int[] nums) {
        // Use a HashSet to track seen elements
        Set<Integer> seen = new HashSet<>();
        // Iterate through each number in the array
        for (int num : nums) {
            // If the number is already in the set, a duplicate exists
            if (seen.contains(num)) {
                return true;
            }
            // Otherwise, add the number to the set
            seen.add(num);
        }
        // No duplicates found
        return false;
    }

    public static void main(String[] args) {
        DuplicateNumber solution = new DuplicateNumber();
        int[][] testCases = {
                {5, 3, 7, 5, 2},
                {10, 20, 30, 40, 50},
                {-1000000000, 1000000000, -1000000000},
                {42},
                {8, 6, 4, 2, 0, -2, -4, 6}
        };

        int y = 1;
        for (int[] nums : testCases) {
            boolean result = solution.containsDuplicate(nums);
            System.out.println(y++ + ".\tnums: " + Arrays.toString(nums));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
