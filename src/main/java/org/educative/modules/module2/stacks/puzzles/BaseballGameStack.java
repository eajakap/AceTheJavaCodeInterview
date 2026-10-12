package org.educative.modules.module2.stacks.puzzles;

import java.util.*;

public class BaseballGameStack {
    private static class Solution {
        public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String op : operations) {
            if (op.equals("C")) {
                stack.pop();
            } else if (op.equals("D")) {
                stack.push(requireRecentScore(stack) * 2);
            } else if (op.equals("+")) {
                int top = stack.pop();
                int newScore = top + requireRecentScore(stack);
                stack.push(top);
                stack.push(newScore);
            } else {
                stack.push(Integer.parseInt(op));
            }
        }

        int sum = 0;
        for (int score : stack) {
            sum += score;
        }
        return sum;
    }

        private int requireRecentScore(Deque<Integer> stack) {
            Integer score = stack.peek();
            if (score == null) {
                throw new NoSuchElementException("Not enough scores to apply the operation");
            }
            return score;
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
