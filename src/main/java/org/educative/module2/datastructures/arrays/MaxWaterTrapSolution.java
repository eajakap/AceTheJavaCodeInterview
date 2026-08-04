package org.educative.module2.datastructures.arrays;

public class MaxWaterTrapSolution {
    // Time Complexity: O(n), Space Complexity: O(1)
    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int width = right - left;
            maxArea = Math.max(maxArea, minHeight * width);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public int maxWater(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        int leftMax=0;
        int rightMax=0;

        while (left < right) {
           if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    maxArea += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    maxArea += rightMax - height[right];
                }
                right--;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        MaxWaterTrapSolution solution = new MaxWaterTrapSolution();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int result = solution.maxArea(height);
        System.out.println("Max area: " + result); // Expected output: 49
        int[] height2 = {2, 9, 5, 3, 7, 4, 9, 6, 8};
        int result2 = solution.maxWater(height2);
        System.out.println("Max water trapped: " + result2); // Expected output: 23
    }
}
