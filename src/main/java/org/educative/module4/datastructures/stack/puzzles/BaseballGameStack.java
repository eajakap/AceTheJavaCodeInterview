package org.educative.module4.datastructures.stack.puzzles;

import java.util.*;

public class BaseballGameStack {
    private static class Solution {
        public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String op : operations) {
            if (op.equals("C")) {
                // Invalidate and remove the most recent score
                stack.pop();
            } else if (op.equals("D")) {
                // Double the most recent score and push it
                stack.push(stack.peek() * 2);
            } else if (op.equals("+")) {
                // Sum of the two most recent scores
                int top = stack.pop();
                int newScore = top + stack.peek();
                stack.push(top);
                stack.push(newScore);
            } else {
                // It's an integer score, push onto the stack
                stack.push(Integer.parseInt(op));
            }
        }

        // Return the sum of all remaining scores
        int sum = 0;
        for (int score : stack) {
            sum += score;
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        String[][] testCases = {
                {"3", "6", "+", "D", "C"},
                {"10", "-5", "D", "+", "C", "C"},
                {"-3", "7", "+", "C", "D"},
                {"100", "200", "C", "C"},
                {"8", "4", "+", "D", "+", "C"}
        };

        int y = 1;
        for (String[] ops : testCases) {
            int result = sol.calPoints(ops);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(ops));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}

    public static void main(String[] args) {
        Solution.main(args);
    }

}
