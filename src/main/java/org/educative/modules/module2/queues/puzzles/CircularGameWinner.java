package org.educative.modules.module2.queues.puzzles;
import java.util.*;

public class CircularGameWinner {
    public static void main(String [] args) throws Exception {
        Solution.main(args);
    }
}

class Solution {
    public int findTheWinner(int n, int k) {
        // Initialize a queue with friends numbered 1 to n
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            queue.add(i);
        }

        // Eliminate friends one by one until one remains
        while (queue.size() > 1) {
            // Rotate the queue k-1 times to move the first k-1 friends to the back
            for (int i = 0; i < k - 1; i++) {
                queue.add(queue.poll());
            }
            // Remove the k-th friend (now at the front)
            queue.poll();
        }

        // The last remaining friend is the winner
        return queue.peek();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] testCases = {
                {4, 2},    // 4 friends, count by 2
                {7, 3},    // 7 friends, count by 3
                {1, 1},    // Edge case: single friend
                {10, 4},   // 10 friends, count by 4
                {8, 8}     // 8 friends, k equals n
        };

        int y = 1;
        for (int[] tc : testCases) {
            int n = tc[0];
            int k = tc[1];
            int result = sol.findTheWinner(n, k);
            System.out.println(y++ + ".\tInput n: " + n);
            System.out.println("\tK: " + k);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}