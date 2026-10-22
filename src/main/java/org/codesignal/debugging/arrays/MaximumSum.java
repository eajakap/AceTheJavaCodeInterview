package org.codesignal.debugging.arrays;

/**
 * Finds the contiguous subarray of a fixed length {@code k} that has the maximum sum.
 *
 * Problem: Given an array of {@code n} integers ({@code 1 <= n <= 10^5}), where each value is in the
 * range {@code [-10^5, 10^5]}, find the contiguous subarray of length {@code k}
 * ({@code 1 <= k <= n}) with the largest sum, using the two-pointer technique.
 *
 * Return two integers: the largest sum of any {@code k}-length subarray and the index of its first element.
 * If several subarrays share the same maximum sum, the one with the lowest starting index is returned.
 *
 * The solution runs in linear time, {@code O(n)}.
 *
 * Example 1: For the input array {@code [2, 1, 5, 1, 3, 2]} and {@code k = 3}, the result is
 * a maximum sum of 9 and a starting index of 2. The subarrays are {@code [2, 1, 5]},
 * {@code [1, 5, 1]}, {@code [5, 1, 3]} and {@code [1, 3, 2]}.
 *
 * Example 2: For the same array and {@code k = 2}, the result is a maximum sum of 6 and a starting index of 1.
 * The subarrays are {@code [2, 1]}, {@code [1, 5]}, {@code [5, 1]}, {@code [1, 3]} and {@code [3, 2]}.
 *
 * Hint: Use the two-pointer technique, where both pointers only move to the right.
 */
public class MaximumSum {

    /**
     * Returns the maximum sum of any contiguous subarray of length {@code k}, along with its starting index.
     *
     * Approach: fixed-size sliding window. The {@code right} pointer walks through the array, adding each element
     * to a running sum. Once the window reaches {@code k} elements ({@code right >= k - 1}), its start is
     * {@code right - k + 1}. The window's sum is compared with the best seen so far, and then the element at the
     * window's start is subtracted so the window slides one position to the right on the next iteration.
     *
     * Because the best sum is only replaced on a strictly greater value, ties keep the earliest starting index.
     *
     * Time complexity: {@code O(n)}. Each element is added and removed at most once.
     * Space complexity: {@code O(1)}.
     *
     * @param numbers the input array; if {@code null} or empty, an array containing zero sum and index is returned
     * @param k       the window length, expected to satisfy {@code 1 <= k <= numbers.length}
     * @return a two-element array containing the largest sum and the starting index of a
     *         {@code k}-length subarray
     */
    public int[] maximumSum(int[] numbers, int k) {
        // Starting index of the best window found so far
        int left = 0;
        // Use long for the sums to avoid overflow while accumulating
        long maximum_sum = Long.MIN_VALUE;
        long current_sum = 0;
        if (numbers == null || numbers.length == 0) return new int[]{0, 0};
        // Slide a window of size k across the array
        for (int right = 0; right < numbers.length; right++) {
            // Expand the window by adding the element at the right pointer
            current_sum += numbers[right];
            // The window holds exactly k elements once right reaches index k - 1
            if (right >= k - 1) {
                // Record this window if it beats the best so far (strict '>' keeps the earliest index on ties)
                if (current_sum > maximum_sum) {
                    maximum_sum = current_sum;
                    left = right - k + 1;
                }
                // Drop the window's leftmost element so it slides one step right on the next iteration
                current_sum -= numbers[right - k + 1];
            }
        }

        return new int[]{(int) maximum_sum, left};
    }

    public static void main(String[] args) {
        MaximumSum app = new MaximumSum();
        int[] numbers = {2, 1, 5, 1, 3, 2};
        int k = 3;
        int[] result = app.maximumSum(numbers, k);
        // Expected output: "9 2"
        System.out.println(result[0] + " " + result[1]);
    }
}
