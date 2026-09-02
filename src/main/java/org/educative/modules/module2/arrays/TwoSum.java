package org.educative.modules.module2.arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

        // Pair each number with its original index
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i]; // value
            arr[i][1] = i;       // original index
        }

        int left = 0;
        int right = n - 1;
        // sort the array first
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));
        while (left < right) {
            int sum = arr[left][0] + arr[right][0];
            if (sum == target) {
                // we found the pair, return their indices
                return new int[]{arr[left][1], arr[right][1]};
            } else if (sum < target) {
                // less than target, move left pointer to the right to increase the sum
                left++;
            } else {
                // greater than target, move right pointer to the left to decrease the sum
                right--;
            }
        }
        return new int[]{};
    }

    static class Pair {
        final int value;
        final int index;

        Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    // (1) Works only if numsSorted is sorted ascending.
    static int[] twoSumSortedTwoPointers(int[] numsSorted, int target) {
        if (numsSorted == null || numsSorted.length < 2) return new int[] { -1, -1 };

        int left = 0;
        int right = numsSorted.length - 1;
        while (left < right) {
            long sum = (long) numsSorted[left] + (long) numsSorted[right];
            if (sum == target) return new int[] { left, right }; // indices in the sorted array
            if (sum < target) left++;
            else right--;
        }
        return new int[] { -1, -1 };
    }

    // (2) For unsorted input: sort (value,index) pairs then use two pointers; returns original indices.
    static int[] twoSumUnsortedSortPairsTwoPointers(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[] { -1, -1 };

        Pair[] pairs = new Pair[nums.length];
        for (int i = 0; i < nums.length; i++) pairs[i] = new Pair(nums[i], i);

        Arrays.sort(pairs, Comparator.comparingInt(p -> p.value));

        int left = 0;
        int right = pairs.length - 1;
        while (left < right) {
            long sum = (long) pairs[left].value + (long) pairs[right].value;
            if (sum == target) {
                int i1 = pairs[left].index;
                int i2 = pairs[right].index;
                if (i1 == i2) { // defensive; should not happen because left < right
                    left++;
                    continue;
                }
                return new int[] { i1, i2 };
            }
            if (sum < target) left++;
            else right--;
        }
        return new int[] { -1, -1 };
    }

    // (3) For unsorted input: O(n) average using hash map value->index.
    static int[] twoSumUnsortedHashMap(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[] { -1, -1 };

        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            int need = target - x;
            Integer j = seen.get(need);
            if (j != null && j != i) return new int[] { j, i };

            // Store after check to avoid using the same element twice when target == 2*x.
            seen.putIfAbsent(x, i); // keep earliest index for duplicates
        }
        return new int[] { -1, -1 };
    }

    static void printResult(String label, int[] result) {
        System.out.println(label + ": " + Arrays.toString(result));
    }

    static void runCase(String name, int[] nums, int target, boolean isSorted) {
        System.out.println("=== " + name + " ===");
        System.out.println("Input nums: " + Arrays.toString(nums));
        System.out.println("Target: " + target);

        if (isSorted) {
            printResult("(1) sorted two-pointers (sorted indices)", twoSumSortedTwoPointers(nums, target));
        } else {
            printResult("(1) sorted two-pointers (skipped; input not sorted)", new int[] { -1, -1 });
        }

        printResult("(2) sort pairs + two-pointers (original indices)", twoSumUnsortedSortPairsTwoPointers(nums, target));
        printResult("(3) hash map (original indices)", twoSumUnsortedHashMap(nums, target));
        System.out.println();
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
                {{-4, -8, 0, -7,-3,-10}, {-15}},
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
