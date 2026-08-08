package org.educative.modules.module2.queues.puzzles;

import java.util.*;


public class MaxSlidingWindow {

    private static class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            Deque<Integer> dq = new ArrayDeque<>();
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                // Remove indices that are out of the current window
                if (!dq.isEmpty() && dq.peekFirst() < i - k + 1) {
                    dq.pollFirst();
                }

                // Remove indices whose values are smaller than or equal to nums[i]
                while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                    dq.pollLast();
                }

                dq.addLast(i);

                // Once we've processed at least k elements, record the max (front of deque)
                if (i >= k - 1) {
                    result.add(nums[dq.peekFirst()]);
                }
            }

            return result.stream().mapToInt(Integer::intValue).toArray();
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][] arrays = {
                    {9,8,7,6,5,4,3,2,1},
                    {4, 3, 2, 1},
                    {1, 2, 3, 4, 5},
                    {-1, -3, -5, -2, -4},
                    {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                    {5, 5, 5, 5, 5}
            };

            int[] kValues = {1, 2, 3, 2, 4, 3};

            int y = 1;
            for (int t = 0; t < arrays.length; t++) {
                int[] result = sol.maxSlidingWindow(arrays[t], kValues[t]);
                System.out.println(y++ + ".\tInput array: " + Arrays.toString(arrays[t]));
                System.out.println("\tTarget: " + kValues[t]);
                System.out.println("\tResult: " + Arrays.toString(result));
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        MaxSlidingWindow.Solution.main(args);
    }

}