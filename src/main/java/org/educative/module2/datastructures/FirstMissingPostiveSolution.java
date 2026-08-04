package org.educative.module2.datastructures;

public class FirstMissingPostiveSolution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int correctIdx = nums[i] - 1;
                int temp = nums[correctIdx];
                nums[correctIdx] = nums[i];
                nums[i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return n + 1;
    }

    public static void main(String[] args) {
        FirstMissingPostiveSolution solution = new FirstMissingPostiveSolution();
        int[] nums = {3, 4, -1, 1};
        System.out.println(solution.firstMissingPositive(nums)); // Output: 2
    }
}
