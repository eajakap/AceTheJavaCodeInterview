package org.educative.modules.module2.heaps;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneWeightMain {

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) {
            maxHeap.add(s);
        }

        while (maxHeap.size() > 1) {
            int y = maxHeap.poll();
            int x = maxHeap.poll();

            if (x != y) {
                maxHeap.add(y - x);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    public static void main(String[] args) {
        LastStoneWeightMain sol = new LastStoneWeightMain();

        int[][] testCases = {
                {3, 7, 2},
                {10, 4, 2, 10},
                {5, 5, 5, 5},
                {1, 2, 3, 4, 5, 6},
                {1000, 999, 1, 1, 1}
        };

        int y = 1;
        for (int[] stones : testCases) {
            int result = sol.lastStoneWeight(stones);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(stones));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
