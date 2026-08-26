package org.educative.modules.module2.heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
/**
 * This class provides a solution to find the k pairs with the smallest sums from two sorted integer arrays.
 * Given two integer arrays A and B sorted in ascending order, and an integer k.
 * Define a pair (u,v) which consists of one element from A and one element from B.
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 * Time Complexity: O(k log k), where k is the number of pairs to find. The priority queue operations take logarithmic time.
 * Space Complexity: O(k), as we store up to k pairs in the priority queue.
 */

public class KSmallestPairs {
    public List<List<Integer>> kSmallestPairs(int[] A, int[] B, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (A.length == 0 || B.length == 0 || k == 0) return result;

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (x, y) -> (A[x[0]] + B[x[1]]) - (A[y[0]] + B[y[1]])
        );

        // Push first column: (i, 0)
        for (int i = 0; i < Math.min(A.length, k); i++) {
            minHeap.offer(new int[]{i, 0});
        }

        while (k > 0 && !minHeap.isEmpty()) {
            int[] top = minHeap.poll();
            int i = top[0], j = top[1];

            result.add(Arrays.asList(A[i], B[j]));
            k--;

            // Move to next element in B
            if (j + 1 < B.length) {
                minHeap.offer(new int[]{i, j + 1});
            }
        }

        return result;
    }

    // Driver code to test the implementation
    public static void main(String[] args) {
        KSmallestPairs sol = new KSmallestPairs();
        int[][] testCasesA = {
                {1, 7, 11},
                {1, 1, 2},
                {1, 2},
                {1, 2, 3},
                {1, 2, 3}
        };
        int[][] testCasesB = {
                {2, 4, 6},
                {1, 2, 3},
                {3, 4},
                {1, 2, 3},
                {4, 5, 6}
        };
        int[] testCasesK = {3, 3, 3, 3, 3};

        for (int i = 0; i < testCasesA.length; i++) {
            int[] A = testCasesA[i];
            int[] B = testCasesB[i];
            int k = testCasesK[i];
            List<List<Integer>> result = sol.kSmallestPairs(A, B, k);
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("A: " + Arrays.toString(A));
            System.out.println("B: " + Arrays.toString(B));
            System.out.println("k: " + k);
            System.out.println("Result: " + result);
            System.out.println("-".repeat(50));
        }
    }

}
