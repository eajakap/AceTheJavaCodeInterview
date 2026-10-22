package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of integers and a number k, find the maximum length of a subarray that sums to k.
 * Return all the elements between the start and end indices of the subarray.
 * You are given an array of n positive integers, where n ranges from $1$ to $500000$, inclusive,
 * and each number in the array is between $1$ and $1000$.
 * Your task is to find the longest subarray such that the sum of its elements equals a predetermined number, k.
 *
 * You have to write a Java method getLongestSubarray that will receive the list of integers,
 * array, and the predetermined number, k, as inputs, and will return a List representing the longest subarray
 * whose sum of elements equals k. The length of the LongestSubarray should be maximized.
 *
 * If no such subarray is found, the function should return an empty List.
 * If there are multiple longest subarrays with a sum equal to k, return the one that occurs earlier.
 *
 * For example, if the given array is {1, 2, 3, 4, 5} and k is 5, the method should return {2, 3}
 * because this subarray is the longest that sums up to the k value.
 *
 * When solving this task, you can use a two-pointer technique where both pointers move towards the right.
 * This approach will help to solve the task using linear time complexity, achieving the expected time complexity of O(n).
 * Try to construct your solution keeping these key points in mind; your solution should be both efficient and effective.
 */
public class LongestSubArray {
    public List<Integer> getLongestSubarray(List<Integer> array, int k) {
        // TODO: implement function
        // dynamic sliding window
        int left = 0;
        int current_sum = 0;
        int start=0, end=0;
        int maxLength=0;
        List<Integer> result = new ArrayList<>();
        for (int right = 0; right < array.size(); right++) {
            current_sum += array.get(right);
            while (current_sum > k) {
                current_sum-=array.get(left);
                left++;
            }
            if (current_sum == k) {
                int subarraylength = right - left + 1;
                if (subarraylength > maxLength) {
                    start = left;
                    end = right;
                    maxLength = subarraylength;
                }
            }
        }
        while (start <= end && maxLength > 0 ) {
            result.add(array.get(start));
            start++;
        }
        return result;
    }

    public static void main(String[] args) {
        LongestSubArray app = new LongestSubArray();
        List<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        System.out.println(app.getLongestSubarray(array, 5));
    }
}
