package org.educative.modules.module2.stacks.puzzles;

import java.util.Stack;

public class ReverseString {
    public static String reverseString(String input) {
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            stack.push(c);
        }

        StringBuilder reversedString = new StringBuilder();
        while (!stack.empty()) {
            reversedString.append(stack.pop());
        }

        return reversedString.toString();
    }

    public static void main(String[] args) {
        String input = "HELLO";
        System.out.println("Input: " + input + " Reverse: " +  reverseString(input)); // Outputs: OLLEH
    }
}