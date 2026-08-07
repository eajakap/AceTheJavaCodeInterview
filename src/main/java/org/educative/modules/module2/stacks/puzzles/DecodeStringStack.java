package org.educative.modules.module2.stacks.puzzles;

import java.util.*;

public class DecodeStringStack {
    private static class Pair {
        public String previousString;
        public int repeatCount;
        public Pair(String previousString, int repeatCount) {
            this.previousString = previousString;
            this.repeatCount = repeatCount;
        }
    }

    private static class Solution {
        private boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        public String decodeStringAjay(String s) {
            Deque <Pair> stack = new ArrayDeque<>();
            int currentNum = 0;
            String currentStr = "";
            for (char c : s.toCharArray()) {
                if (isDigit(c)) {
                    currentNum = Character.digit(c, 10);
                } else if (c == '[') {
                    stack.push(new Pair(currentStr, currentNum));
                    currentStr = "";
                    currentNum = 0;
                } else if (c == ']') {
                    Pair pair = stack.pop();
                    StringBuilder sb = new StringBuilder(pair.previousString);
                    for (int i = 0; i < pair.repeatCount; i++) {
                        sb.append(currentStr);
                    }
                    currentStr = sb.toString();
                } else {
                    currentStr += c;
                }
            }
            return currentStr;
        }

        public String decodeString(String s) {
            // Stack to store (previousString, repeatCount) when we encounter '['
            Deque<String> strStack = new ArrayDeque<>();
            Deque<Integer> numStack = new ArrayDeque<>();
            // Current string being built
            String currentStr = "";
            // Current number being parsed
            int currentNum = 0;

            for (char ch : s.toCharArray()) {
                if (Character.isDigit(ch)) {
                    // Build multi-digit number
                    currentNum = currentNum * 10 + (ch - '0');
                } else if (ch == '[') {
                    // Push current state onto stack and reset
                    strStack.push(currentStr);
                    numStack.push(currentNum);
                    currentStr = "";
                    currentNum = 0;
                } else if (ch == ']') {
                    // Pop previous string and repeat count
                    int repeatCount = numStack.pop();
                    String prevStr = strStack.pop();
                    // Repeat current string and append to previous
                    currentStr = prevStr + currentStr.repeat(repeatCount);
                } else {
                    // Regular lowercase letter, append to current string
                    currentStr += ch;
                }
            }
            return currentStr;
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            String[][] testCases = {
                    {"10[a]", "aaaaaaaaaa"},
                    {"2[a3[b]]", "abbbabbb"},
                    {"xyz", "xyz"},
                    {"1[ab1[cd]]", "abcd"},
                    {"2[2[ab]c]", "ababcababc"}
            };

            int y = 1;
            for (String[] tc : testCases) {
                String inputStr = tc[0];
                String expected = tc[1];
                String result = sol.decodeString(inputStr);
                System.out.println(y++ + ".\tInput array: [\"" + inputStr + "\"]");
                System.out.println("\tTarget: \"" + expected + "\"");
                System.out.println("\tResult: \"" + result + "\"");
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
