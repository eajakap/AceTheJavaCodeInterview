package org.codesignal.debugging.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Finds a pair of integers in an array whose sum equals a target value.
 *
 * Given a list of n integers and a target, find a pair of integers whose sum equals the target.
 * If several pairs qualify, choose the one whose last element appears earliest in the array.
 * If no pair sums to the target, return an empty array.
 *
 * Constraints: n is between 1 and 1,000,000; each element is between -1,000,000 and 1,000,000;
 * the target is between -2,000,000 and 2,000,000. The program should work within 1 second.
 *
 * Example: for arr = [2, 13, 4, 7, 5, 15] and target = 9, the result is [2, 7]. The pair [4, 5] also sums to 9,
 * but its last element appears later in the array.
 */
public class TwoSumEarliestPair {

    /**
     * Finds the pair summing to the target whose last element has the lowest index.
     *
     * Steps:
     * 1. Scan the array from left to right, remembering the values already seen in a set.
     * 2. For each element, check whether its complement (target - element) was seen earlier.
     * 3. The first element with a seen complement ends the pair with the lowest last index,
     *    so return the complement followed by that element.
     * 4. If the scan finishes without a match, return an empty array.
     * 5. Time Complexity: O(n)
     * 6. Space Complexity: O(n)
     *
     * @param arr    the input integers
     * @param target the required sum
     * @return the two values in array order, or an empty array if no pair sums to the target
     */
    public int[] findTwoSumEarliestPair(int[] arr, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int value : arr) {
            int complement = target - value;
            if (seen.contains(complement)) {
                return new int[]{complement, value};
            }
            seen.add(value);
        }
        return new int[]{};
    }

    public int[] findTwoSumEarliestPairTwoPtr(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        Arrays.sort(arr);
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                return new int[]{arr[left], arr[right]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        TwoSumEarliestPair app = new TwoSumEarliestPair();
        int[] arr = {2, 13, 4, 7, 5, 15};
        int target = 9;
        int[] result = app.findTwoSumEarliestPair(arr, target);
        System.out.println(Arrays.toString(result));
        int[] result2 = app.findTwoSumEarliestPairTwoPtr(arr, target);
        System.out.println(Arrays.toString(result2));

    }
}