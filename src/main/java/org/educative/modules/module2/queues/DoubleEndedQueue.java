package org.educative.modules.module2.queues;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.educative.modules.module2.queues.DoubleEndedQueue.createDeque;

public class DoubleEndedQueue {
    public static Deque<String> createDeque(String[] elements) {
        Deque<String> d = new ArrayDeque<>();
        for (String element : elements) {
            d.addLast(element);
        }
        System.out.println(d);  // Expects [Cherry, Apple, Banana]
        return d;
    }

    public static Deque<Integer> createDeque(int[] elements) {
        Deque<Integer> d = new ArrayDeque<>();
        for (int element : elements) {
            d.addLast(element);
        }
        System.out.println(d);  // Expects [10, 20, 30]
        return d;
    }

    public static void main(String[] args) {
        // Create an empty deque
        Deque<Integer> dq = createDeque(new int[]{10,20,30});

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

        // Create a deque
        Deque<String> d = createDeque(new String[]{"Apple", "Banana", "Cherry"});

        // Rotate the deque to the right by 1 place
        for (int i = 0; i < 1; i++) {
            d.addFirst(d.removeLast());
        }
        System.out.println(d);  // Expects [Cherry, Apple, Banana]

    }
}