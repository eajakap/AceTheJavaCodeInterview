package org.educative.modules.module2.stacks.linkedlist;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedListStack {
    private static class StackNode {
        int data;
        StackNode next;

        public StackNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private StackNode top;

    public LinkedListStack() {
        this.top = null;
    }

    // Stack operations (push, pop, peek, etc.) will be discussed in the next lesson
    public boolean isEmpty() {
        return top == null;
    }

    public StackNode peek() {
        return top;
    }

    public int top() {
        if (top == null) {
            return -1; // or throw an exception
        }
        return top.data;
    }

    public int size() {
        int size = 0;
        StackNode current = top;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    public void push(int val) {
        if (top == null) {
            top = new StackNode(val);
        } else {
            StackNode newNode = new StackNode(val);
            newNode.next = top;
            top = newNode;
        }
    }

    public int pop() {
        if (top == null) {
            return -1;
        }
        int val = top.data;
        top = top.next;
        return val;
    }

    public static void main(String[] args) {
        LinkedListStack stack;

        String[][] testCases = {
                {"push", "push", "push", "pop", "top", "empty"},
                {"push", "push", "top", "pop", "empty"},
                {"push", "empty", "pop", "empty"},
                {"push", "push", "push", "top", "pop", "pop", "empty"},
                {"push", "pop", "push", "push", "top", "empty"}
        };

        // Only push arguments, in order
        int[][] pushArgs = {
                {9, 3, 1},
                {5, 7},
                {4},
                {1, 2, 3},
                {6, 8, 9}
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            stack = new LinkedListStack();
            List<String> results = new ArrayList<>();
            results.add("null");
            int pushIdx = 0;

            System.out.println(y++ + ".\tOperations: " + Arrays.toString(testCases[t]));

            for (int i = 0; i < testCases[t].length; i++) {
                String op = testCases[t][i];
                switch (op) {
                    case "push":
                        stack.push(pushArgs[t][pushIdx++]);
                        results.add("null");
                        break;
                    case "pop":
                        results.add(String.valueOf(stack.pop()));
                        break;
                    case "top":
                        results.add(String.valueOf(stack.top()));
                        break;
                    case "empty":
                        results.add(String.valueOf(stack.isEmpty()));
                        break;
                }
            }

            System.out.println("\tResult: [" + String.join(", ", results) + "]");
            System.out.println("-".repeat(100));
        }
    }
}