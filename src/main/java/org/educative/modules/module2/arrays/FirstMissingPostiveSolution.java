package org.educative.modules.module2.arrays;

/**
 * FirstMissingPostiveSolution class provides a method to find the first missing positive integer in an unsorted array.
 * Given an unsorted integer array nums, find and return the smallest positive integer that is not present in nums.
 * Time Complexity: O(n), where n is the length of the input array. We traverse the array a constant number of times.
 * Space Complexity: O(1), as we are using a constant amount of space for variables
 */
public class FirstMissingPostiveSolution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // Step 1: Place each number in its correct index position (value i at index i-1)
        // Using cyclic sort approach
        for (int i = 0; i < n; i++) {
            // Keep swapping nums[i] to its correct position while possible
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // Swap nums[i] with nums[nums[i] - 1]
                int correctIdx = nums[i] - 1;
                int temp = nums[correctIdx];
                nums[correctIdx] = nums[i];
                nums[i] = temp;
            }
        }
        // Step 2: Find first index where nums[i] != i + 1
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // All positions 1..n are filled, so answer is n + 1
        return n + 1;
    }

    public static void main(String[] args) {
        FirstMissingPostiveSolution solution = new FirstMissingPostiveSolution();
        int[] nums = {3, 4, -1, 1};
        System.out.println(solution.firstMissingPositive(nums)); // Output: 2
    }
}
