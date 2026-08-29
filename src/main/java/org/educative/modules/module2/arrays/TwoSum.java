package org.educative.modules.module2.arrays;

import java.util.Arrays;

public class TwoSum {
    // Time complexity - O(n^2)
    // Space Complexity - O(1)
    public static int[] twosum(int[]nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            // linear search for the complement of nums[i] in the rest of the array
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    // Time complexity - O(n log n)
    // Space Complexity - O(1)
    public static int[] twosumV2(int[]nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        // sort the array first
        Arrays.sort(nums);
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                // we found the pair, return their indices
                return new int[]{left, right};
            } else if (nums[left] + nums[right] < target) {
                // less than target, move left pointer to the right to increase the sum
                left++;
            } else {
                // greater than target, move right pointer to the left to decrease the sum
                right--;
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 5, 7, 9, 11};
        int target = 11;
        int[] result = twosum(nums, target);
        if (result.length > 0) {
            System.out.println("Indices: " + result[0] + ", " + result[1]);
        } else {
            System.out.println("No two sum solution found.");
        }

        int[] nums2 = {1, 2, 3, 4, 5, 7, 9, 11};
        int target2 = 11;
        int[] result2 = twosum(nums2, target2);
        if (result2.length > 0) {
            System.out.println("Indices: " + result2[0] + ", " + result2[1]);
        } else {
            System.out.println("No two sum solution found.");
        }

        // 2d - Array
        int[][][] testCases = {
                {{1, 5, 3, 7}, {8}},
                {{10, -2, 3, 4, 5}, {8}},
                {{0, 4, 3, 0}, {0}},
                {{-1, -2, -3, -4, -5}, {-8}},
                {{1000000000, -1000000000, 3, 4}, {0}}
        };
//        System.out.println("-".repeat(100));

        int y = 1;
        for (int[][] tc : testCases) {
            int[] nums4 = tc[0];
            int target4 = tc[1][0];
            System.out.println("-".repeat(100));
            int ctr = y++;
            System.out.println(ctr + ".\tLinear Approach: Input array: " + Arrays.toString(nums4));
            int[] result4 = twosum(nums4, target4);
            System.out.println("\tTarget: " + target4);
            System.out.println("\n \tResult: " + Arrays.toString(result4));
            System.out.println("-".repeat(100));
            System.out.println(ctr + ".\tTwo Pointer Approach: Input array: " + Arrays.toString(nums4));
            int[] result5 = twosumV2(nums4, target4);
            System.out.println("\tTarget: " + target4);
            System.out.println("\n \tResult: " + Arrays.toString(result5));
        }

    }

}
