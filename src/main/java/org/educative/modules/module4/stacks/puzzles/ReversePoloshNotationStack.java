package org.educative.modules.module4.stacks.puzzles;

import java.util.*;

public class ReversePoloshNotationStack {

    private static class Solution {

        public int evalRPN(String[] tokens) {
            // Stack to hold operands
            Deque<Integer> stack = new ArrayDeque<>();
            // Set of valid operators
            Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

            for (String token : tokens) {
                if (operators.contains(token)) {
                    // Pop two operands; second popped is left operand
                    int rightOperand = stack.pop();
                    int leftOperand = stack.pop();
                    int result;

                    // Perform the operation based on the token
                    switch (token) {
                        case "+":
                            result = leftOperand + rightOperand;
                            break;
                        case "-":
                            result = leftOperand - rightOperand;
                            break;
                        case "*":
                            result = leftOperand * rightOperand;
                            break;
                        default:
                            // Truncate toward zero using Java integer division
                            result = leftOperand / rightOperand;
                            break;
                    }

                    // Push the result back onto the stack
                    stack.push(result);
                } else {
                    // Push the integer value of the token onto the stack
                    stack.push(Integer.parseInt(token));
                }
            }

            // The final result is the only element remaining on the stack
            return stack.peek();
        }

        public int ajayyEvalRPN(String[] tokens) {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "+":
                        int operand2 = stack.pop();
                        int operand1 = stack.pop();
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        operand2 = stack.pop();
                        operand1 = stack.pop();
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        operand2 = stack.pop();
                        operand1 = stack.pop();
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        operand2 = stack.pop();
                        operand1 = stack.pop();
                        stack.push(operand1 / operand2);
                        break;
                    default:
                        stack.push(Integer.parseInt(tokens[i]));
                }
            }
            // Replace this placeholder return statement with your code
            return (stack.isEmpty()) ? -1 : stack.peek();
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            String[][] testCases = {
                    {"3", "4", "+"},                                         // 3 + 4 = 7
                    {"5", "3", "-", "2", "*"},                               // (5 - 3) * 2 = 4
                    {"7", "2", "/"},                                         // 7 / 2 = 3 (truncate toward zero)
                    {"-10", "3", "/"},                                       // -10 / 3 = -3 (truncate toward zero)
                    {"5", "1", "2", "+", "4", "*", "+", "3", "-"}           // 5 + ((1 + 2) * 4) - 3 = 14
            };

            int y = 1;
            for (String[] tokens : testCases) {
                int result = sol.evalRPN(tokens);
                System.out.println(y++ + ".\tInput array: " + Arrays.toString(tokens));
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
