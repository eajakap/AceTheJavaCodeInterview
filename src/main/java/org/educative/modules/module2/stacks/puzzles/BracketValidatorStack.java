package org.educative.modules.module2.stacks.puzzles;
import java.util.ArrayDeque;
import java.util.Deque;

public class BracketValidatorStack {

    private static class Solution {
        public boolean isValid(String s) {
            // Stack to keep track of opening brackets
            Deque<Character> stack = new ArrayDeque<>();

            for (char ch : s.toCharArray()) {
                // If we see an opening bracket, push the closing bracket we EXPECT to see later
                if (ch == '(') {
                    stack.push(')');
                } else if (ch == '{') {
                    stack.push('}');
                } else if (ch == '[') {
                    stack.push(']');
                }

                // If we see a closing bracket:
                // 1. The stack must not be empty (Underflow check)
                // 2. The top of the stack must match the current closing bracket
                else {
                    if (stack.isEmpty()) {
                        return false;
                    } else if (stack.pop() != ch) {
                        return false;
                    }
                }
            }

            // In the end, the stack should be empty if all brackets were matched correctly
            return stack.isEmpty();
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            String[] testCases = {
                    "{[()]}",
                    "(((",
                    "{()}[]",
                    "]))",
                    "{[}]"
            };

            int y = 1;
            for (String s : testCases) {
                boolean result = sol.isValid(s);
                System.out.println(y++ + ".\tInput string: \"" + s + "\"");
                System.out.println("\n\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
