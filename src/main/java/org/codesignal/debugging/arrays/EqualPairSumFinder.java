package org.codesignal.debugging.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given two arrays, arrA and arrB, each containing n integers.
 * Your task is to write a function that returns a pair of distinct indices [i, j] such that the sum of the
 * elements at i and j in array arrA equals the sum of the elements at the same indices in array arrB.
 * If there are multiple valid pairs, return the pair in which i is minimal.
 * If there is still ambiguity, choose the one with minimal j among the latter.
 *
 * Consider arrA = [2, 5, 1, 4] and arrB = [3, 6, 3, 2].
 * In this case, the output should be [2, 3] because arrA[2] + arrA[3] = arrB[2] + arrB[3], i.e., 1 + 4 = 3 + 2.
 *
 * Your solution should work within 1 second for each test.
 * Constraints:
 * 1 ≤ n ≤ 1000000
 * -1000000 ≤ arrA[i], arrB[i] ≤ 1000000 for all valid i
 * At least one pair [i, j] exists such that arrA[i] + arrA[j] = arrB[i] + arrB[j]
 */
public class EqualPairSumFinder {

    /**
     * Finds two distinct indices where the pair sums of the two arrays match.
     *
     * Steps:
     * 1. Let d[k] = arrA[k] - arrB[k]. The condition arrA[i] + arrA[j] = arrB[i] + arrB[j] becomes d[i] + d[j] = 0.
     * 2. Scan from right to left, keeping a map from each difference to the smallest index seen so far.
     * 3. For each index i, look up -d[i]. A hit gives the smallest j greater than i, so record [i, j].
     * 4. Later hits have a smaller i, so the final recorded pair has the minimal i and then the minimal j.
     * 5. Time Complexity: O(n)
     * 6. Space Complexity: O(n)
     * Notes:
     * arrA[i] + arrA[j] = arrB[i] + arrB[j]
     * (arrA[i] - arrB[i]) + (arrA[j] - arrB[j]) = 0
     * int diff = arrA[i] - arrB[i];
     * Integer j = smallestIndexOfDiff.get(-diff);
     * arrA[2] + arrA[3] = 1 + 4 = 5
     * arrB[2] + arrB[3] = 3 + 2 = 5
     *
     * @param arrA the first array
     * @param arrB the second array, with the same length as arrA
     * @return the pair [i, j] with i less than j, or [-1, -1] if no pair exists
     */
    public int[] findIndices(int[] arrA, int[] arrB) {
        Map<Integer, Integer> smallestIndexOfDiff = new HashMap<>();
        int[] best = {-1, -1};
        for (int i = arrA.length - 1; i >= 0; i--) {
            int diff = arrA[i] - arrB[i];
            Integer j = smallestIndexOfDiff.get(-diff);
            if (j != null) {
                best = new int[]{i, j};
            }
            smallestIndexOfDiff.put(diff, i);
        }
        return best;
    }

    /**
     * Runs the example from the problem statement.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        EqualPairSumFinder app = new EqualPairSumFinder();
        System.out.println(Arrays.toString(app.findIndices(new int[]{2, 5, 1, 4}, new int[]{3, 6, 3, 2})));
    }
}
