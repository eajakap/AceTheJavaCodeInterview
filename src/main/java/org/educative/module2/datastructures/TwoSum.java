package org.educative.module2.datastructures;

import java.util.Arrays;

public class TwoSum {
    // Time complexity - O(n2)
    // Space Complexity - O(1)
    public static int[] twosum(int[]nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
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
        System.out.println("-".repeat(100));

        int y = 1;
        for (int[][] tc : testCases) {
            int[] nums4 = tc[0];
            int target4 = tc[1][0];
            int[] result4 = twosum(nums4, target4);

            System.out.println(y++ + ".\tInput array: " + Arrays.toString(nums4));
            System.out.println("\tTarget: " + target4);
            System.out.println("\n \tResult: " + Arrays.toString(result4));
            System.out.println("-".repeat(100));
        }

    }

}
