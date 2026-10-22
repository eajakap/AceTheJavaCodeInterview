package org.codesignal.debugging.arrays;

import java.util.*;

/**
 * You have been offered n different types of chocolates, where n is an integer that can range anywhere from
 * $1$ to $2 \cdot 10^5$, inclusive. For each type of chocolate, you are given its sweetness level, an integer ranging
 * from $-10^5$ to $10^5$, inclusive. Your task is to use the two-pointer algorithm technique to identify all the
 * pairs of chocolate types whose total sweetness level equals zero.
 *
 * As orders might vary, it has been decided that the sweetness level in each pair should be presented in decreasing order.
 * In the case of multiple pairs, they should overall be sorted in increasing order by the first element in the pair.
 *
 * Write a function findChocPairs(int[] sweetness) that will take an array of n integers, representing the sweetness
 * levels of the chocolates, and should return a list of pairs. Each pair should contain the pair of chocolates whose
 * sweetness levels sum up to zero, sorted in decreasing order of sweetness levels.
 *
 * For example, if the given array is {4, 3, -5, 5, -3, -4}, the function should return {(3, -3), (4, -4), (5, -5)}.
 *
 * In this instance, the pairs of chocolate types whose total sweetness levels sum up to zero are: 5 and -5, 4 and -4,
 * and 3 and -3. After sorting each pair in decreasing order and sorting all pairs by the first element in each pair,
 * the result is {(3, -3), (4, -4), (5, -5)}.
 *
 * As an edge case, if there are no pairs of chocolates whose sweetness level totals zero, your function should return an empty array.
 */
public class FindChoclatePairs {
    public List<int[]> findChocPairs(int[] sweetness) {
        int[] sorted = sweetness.clone(); // don't mutate the caller's array
        Arrays.sort(sorted); // sort the array in ascending order

        // Pairs are found with the largest positive first, so pushing onto a stack
        // yields them in increasing order of their first element without a reverse pass.
        List<int[]> pairs = new ArrayList<>();
        int left = 0;
        int right = sorted.length - 1;

        // Once either pointer crosses zero, no remaining pair can sum to zero.
        while (left < right && sorted[left] <= 0 && sorted[right] >= 0) {
            int total = sorted[left] + sorted[right];
            if (total == 0) {
                // Add the pair in decreasing order of sweetness levels ( right, left )
                // sorted[right] - larger value first, sorted[left] - smaller value second
                pairs.add(new int[] {sorted[right], sorted[left]});
                left++;
                right--;
            } else if (total < 0) {
                left++;
            } else {
                right--;
            }
        }
        Collections.reverse(pairs);
        return new ArrayList<>(pairs);
    }

    public static void main(String[] args) {
        int[] sweetness = {4, 3, -5, 5, -3, -4};
        FindChoclatePairs finder = new FindChoclatePairs();
        for (int[] pair : finder.findChocPairs(sweetness)) {
            System.out.println(Arrays.toString(pair));
        }
    }
}
