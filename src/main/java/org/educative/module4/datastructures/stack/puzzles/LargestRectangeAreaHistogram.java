package org.educative.module4.datastructures.stack.puzzles;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class LargestRectangeAreaHistogram {
    private static class Solution {
        public int largestRectangleArea(int[] heights) {
            // stores the indices of bars
            Deque<Integer> stack = new ArrayDeque<>();
            int barWidth = 1;
            int maxArea = 0;
            int n = heights.length;
            for (int i = 0; i <= n; i++) {
                int currentHeight = (i == n) ? 0 : heights[i];
                while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
                    int height = heights[stack.pop()];
                    int width;
                    if (stack.isEmpty()) {
                        width = i;
                    } else {
                        width = i - stack.peek() - 1;
                    }
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(i);
            }
            return maxArea;
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][] testCases = {
                    {1, 1, 1, 1, 1},
                    {5, 4, 3, 2, 1},
                    {1, 2, 3, 4, 5},
                    {3, 6, 5, 7, 4, 8, 1, 0},
                    {0, 9, 0, 9, 0}
            };

            int y = 1;
            for (int[] heights : testCases) {
                int result = sol.largestRectangleArea(heights);
                System.out.println(y++ + ".\tInput array: " + Arrays.toString(heights));
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
