package org.educative.modules.module2.queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class DoubleEndedQueue {
    public static void main(String[] args) {
        Main.main(args);
    }
}

class Main {
    public static void main(String[] args) {
        // Create an empty deque
        Deque<Integer> dq = new ArrayDeque<>();

        // Insert at rear
        dq.addLast(10);
        dq.addLast(20);
        dq.addLast(30);

        // Insert at front
        dq.addFirst(5);

        // Delete from rear
        dq.removeLast();

        // Delete from front
        dq.removeFirst();

        // Peek at front
        int frontElement = dq.peekFirst();
        System.out.println("Front element is: " + frontElement);

        // Peek at rear
        int rearElement = dq.peekLast();
        System.out.println("Rear element is: " + rearElement);

        // Check if empty
        boolean isEmpty = dq.isEmpty();
        System.out.println("Is the queue empty? " + isEmpty);

        // Get size
        int size = dq.size();
        System.out.println("Size: " + size);
    }
}