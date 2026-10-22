package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FindAndReplace {

    public static int[] findAndReplace(int[] A, int[] B) {
        TreeMap<Integer, Integer> B_map = new TreeMap<>();
        for (int i = 0; i < B.length; i++) {
            B_map.put(B[i], i);
        }

        int[] B_sorted = B_map.keySet().stream().mapToInt(Integer::intValue).toArray();

        int j = 0;  // Initialize right pointer
        int[] res = new int[A.length];  // Initialize the result array
        for (int i = 0; i < B_sorted.length; i++) {
            int target = 2 * B_sorted[i];  // The target is twice the current number in the sorted B
            while (j < B_sorted.length - 1 && B_sorted[j + 1] < target) {
                j++;  // Move the right pointer to find a number smaller than or equal to the target
            }
            if (j < B_sorted.length - 1 &&
                    Math.abs(B_sorted[j + 1] - target) < Math.abs(target - B_sorted[j])) {
                j++;  // Move the right pointer one more step if the next number is closer to the target
            }
            res[B_map.get(B_sorted[i])] = A[B_map.get(B_sorted[j])];
            // Collect the corresponding element from A at the same index as the closest number in B_sorted
        }

        return res;
    }

    /**
     * Steps:
     * 1. Sort X in ascending order and keep track of the original indices.
     * 2. For each element in Y, perform a binary search on the sorted X to find the closest element to 2 * Y[i].
     * 3. Compare the closest elements found in the binary search to determine which one is closer to 2 * Y[i].
     * 4. If two elements are equally close, choose the one with the smaller index.
     * 5. Store the corresponding element from Y in the result array.
     * 6. Return the result array.
     * 7. Time Complexity: O(n log n)
     * 8. Space Complexity: O(n)
     *
     * @param X
     * @param Y
     * @return
     */
    public static int[] findAndReplace2(int[] X, int[] Y) {
        // Values in X are unique, so each value maps to exactly one index
        TreeMap<Integer, Integer> indexOf = new TreeMap<>();
        for (int i = 0; i < X.length; i++) {
            indexOf.put(X[i], i);
        }

        int[] res = new int[Y.length];
        for (int i = 0; i < Y.length; i++) {
            int target = Y[i];
            // Nearest values with 2 * x <= target and 2 * x >= target; compare 2 * x to avoid fractional halves
            Map.Entry<Integer, Integer> lower = indexOf.floorEntry(target / 2);
            Map.Entry<Integer, Integer> upper = indexOf.ceilingEntry((target + 1) / 2);

            int best;
            if (lower == null) {
                best = upper.getValue();
            } else if (upper == null) {
                best = lower.getValue();
            } else {
                int lowerDist = target - 2 * lower.getKey();
                int upperDist = 2 * upper.getKey() - target;
                best = lowerDist < upperDist ? lower.getValue()
                        : upperDist < lowerDist ? upper.getValue()
                        : Math.min(lower.getValue(), upper.getValue());
            }
            res[i] = Y[best];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] A = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110};
        int[] B = {4, 12, 3, 9, 6, 1, 5, 8, 37, 25, 100};
        System.out.println("A: " + Arrays.toString(A));
        System.out.println("B: " + Arrays.toString(B));

        int[] result = findAndReplace(A, B);
        System.out.println("Result: " + Arrays.toString(result));

        System.out.println("Result2: " + Arrays.toString(
                findAndReplace2(new int[]{4, 12, 9, 20}, new int[]{10, 20, 40, 50})));
    }
}