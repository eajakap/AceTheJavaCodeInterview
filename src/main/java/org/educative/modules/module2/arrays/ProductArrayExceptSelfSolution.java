package org.educative.modules.module2.arrays;

public class ProductArrayExceptSelfSolution {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int[] result = productExceptSelf(nums);

        for (int num : result) {
            System.out.print(num + " ");
        }
    }

    // Given an array nums of n integers where n > 1, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
    // Logically, output[i] = product of all elements to the left of i * product of all elements to the right of i.
    // Time Complexity - O(N), Space Complexity: O(1)
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Calculate the prefix products -
        // start with 1 since there are no elements to the left of the first element
        int prefixProduct = 1;
        for (int i = 0; i < n; i++) {
            result[i] = prefixProduct; // prefix product till index i-1
            prefixProduct *= nums[i];
        }

        // Calculate the suffix products and multiply with the prefix products -
        // start with 1 since there are no elements to the right of the last element
        int suffixProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= suffixProduct; // multiply with suffix product till index i+1
            suffixProduct *= nums[i];
        }

        return result;
    }
}
