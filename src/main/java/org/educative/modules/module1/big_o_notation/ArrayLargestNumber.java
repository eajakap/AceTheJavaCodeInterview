package org.educative.modules.module1.big_o_notation;

/**
 * Time Complexity: O(n), where n is the length of the array.
 * We traverse through the array once.
 * Space Complexity: O(1), as we are using a constant amount of space.
 * Given an integer array, return the largest number in the array.
 */
public class ArrayLargestNumber {
    public static int  largestNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
//            if (numbers[i] > max) {
//                max = numbers[i];
//            }
            max = Math.max(numbers[i], max);
        }
        return max;
    }
    public static void main(String[] args) {
        System.out.println(largestNumber(new int[] { 1, 2, 3, 4, 5 }));
    }
}
