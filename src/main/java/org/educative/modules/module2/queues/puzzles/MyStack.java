package org.educative.modules.module2.queues.puzzles;

import java.util.*;

class MyStack {
    // Use a single queue to simulate stack behavior (LIFO)
    private Queue<Integer> queue;

    public MyStack() {
        this.queue = new LinkedList<>();
    }

    public void push(int x) {
        // Add the new element to the back of the queue
        queue.add(x);
        // Rotate the queue so the new element becomes the front
        // Move all previous elements behind the new element
        for (int i = 0; i < queue.size() - 1; i++) {
            // removes the element from the front of the queue and
            // adds it again at the back, effectively rotating the queue
            queue.add(queue.poll());
        }
        // After rotation: newest element is at front (stack top position)
    }

    public int pop() {
        // Remove and return the front element (which is the stack top)
        return queue.poll();
    }

    public int top() {
        // Return the front element without removing it
        return queue.peek();
    }

    public boolean empty() {
        // Check if the queue is empty
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        MyStack stack;

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
            stack = new MyStack();
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
                        results.add(String.valueOf(stack.empty()));
                        break;
                }
            }

            System.out.println("\tResult: [" + String.join(", ", results) + "]");
            System.out.println("-".repeat(100));
        }
    }
}