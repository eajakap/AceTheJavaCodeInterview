package org.educative.modules.module2.queues;

/**
 * Demonstrates a bounded FIFO queue implemented with a circular array.
 */
public class CircularArrayQueue {
    /**
     * A fixed-capacity queue of integers backed by a circular array.
     *
     * <p>The {@code front} and {@code rear} indexes advance modulo the array capacity,
     * allowing freed slots to be reused without shifting elements.</p>
     */
    private static class CircularQueue {
        /** Maximum number of elements the queue can hold. */
        private int capacity;
        /** Array containing the queue elements. */
        private int[] queue;
        /** Index of the next element to be removed. */
        int front;
        /** Index where the next element will be inserted. */
        int rear;
        /** Number of elements currently stored in the queue. */
        int size;

        /**
         * Creates an empty queue with the specified backing-array capacity.
         *
         * @param capacity maximum number of elements the queue can hold
         */
        public CircularQueue(int capacity) {
            this.capacity = capacity;
            this.queue = new int[capacity];
            this.front = 0;
            this.rear = 0;
            this.size = 0;
        }

        /**
         * Checks whether the queue contains no elements.
         *
         * @return {@code true} if the queue is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks whether the queue has reached its capacity.
         *
         * @return {@code true} if the queue is full; {@code false} otherwise
         */
        public boolean isFull() {
            return size == capacity;
        }

        /**
         * Adds an element to the rear of the queue.
         *
         * <p>If the queue is full, an overflow message is printed and the queue is
         * left unchanged.</p>
         *
         * @param element value to add
         */
        public void enqueue(int element) {
            if (isFull()) {
                System.out.println("Overflow: Queue is full");
                return;
            }
            queue[rear] = element;
            // Wrap to the beginning when the rear reaches the end of the array.
            rear = (rear + 1) % capacity;
            size++;
        }

        /**
         * Removes and returns the element at the front of the queue.
         *
         * <p>If the queue is empty, an underflow message is printed and {@code -1}
         * is returned.</p>
         *
         * @return the removed element, or {@code -1} if the queue is empty
         */
        public int dequeue() {
            if (isEmpty()) {
                System.out.println("Underflow: Queue is empty");
                return -1;
            }
            int element = queue[front];
            queue[front] = 0;
            // Advance the front, wrapping around to reuse the circular array.
            front = (front + 1) % capacity;
            size--;
            return element;
        }

        /**
         * Returns the element at the front without removing it.
         *
         * <p>If the queue is empty, a message is printed and {@code -1} is returned.</p>
         *
         * @return the front element, or {@code -1} if the queue is empty
         */
        public int peek() {
            if (isEmpty()) {
                System.out.println("Queue is empty");
                return -1;
            }
            return queue[front];
        }

        /**
         * Returns the elements in front-to-rear order.
         *
         * @return a string representation of the queue
         */
        @Override
        public String toString() {
            if (isEmpty()) {
                return "Queue: []";
            }
            StringBuilder sb = new StringBuilder();
            int i = front;
            for (int count = 0; count < size; count++) {
                sb.append(queue[i]);
                if (count < size - 1) sb.append(", ");
                // Iterate in logical queue order, including across the array boundary.
                i = (i + 1) % capacity;
            }
            return "Queue (front to rear): [" + sb + "]";
        }
    }

    /**
     * Runs a demonstration of queue operations, including underflow, overflow,
     * and circular wrap-around.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Circular Queue");
        System.out.println("-".repeat(100));

        CircularQueue cq = new CircularQueue(5);

        // --- Basic state checks on an empty queue ---
        System.out.println("\n--- Initial state ---");
        System.out.println("isEmpty(): " + cq.isEmpty());   // true
        System.out.println("isFull():  " + cq.isFull());    // false
        System.out.println(cq);                             // Queue: []

        // --- Peek on empty queue ---
        System.out.println("\n--- Peek on empty queue ---");
        cq.peek();                                          // Queue is empty

        // --- Dequeue on empty queue ---
        System.out.println("\n--- Dequeue on empty queue ---");
        cq.dequeue();                                       // Underflow: Queue is empty

        // --- Enqueue elements ---
        System.out.println("\n--- Enqueue 88, 95, 70, 82, 91 ---");
        cq.enqueue(88);
        cq.enqueue(95);
        cq.enqueue(70);
        cq.enqueue(82);
        cq.enqueue(91);
        System.out.println(cq);
        System.out.println("isEmpty(): " + cq.isEmpty());   // false
        System.out.println("isFull():  " + cq.isFull());    // true

        // --- Enqueue on a full queue ---
        System.out.println("\n--- Enqueue on full queue ---");
        cq.enqueue(60);                                     // Overflow: Queue is full

        // --- Peek ---
        System.out.println("\n--- Peek ---");
        System.out.println("peek(): " + cq.peek());         // 88

        // --- Dequeue elements ---
        System.out.println("\n--- Dequeue twice ---");
        System.out.println("dequeue(): " + cq.dequeue());   // 88
        System.out.println("dequeue(): " + cq.dequeue());   // 95
        System.out.println(cq);
        System.out.println("size: " + cq.size);
        System.out.println("front index: " + cq.front);
        System.out.println("rear index:  " + cq.rear);

        // --- Enqueue after dequeue — wrap-around ---
        System.out.println("\n--- Enqueue 60, 70 — rear wraps around ---");
        cq.enqueue(60);
        cq.enqueue(70);
        System.out.println(cq);
        System.out.println("isFull():  " + cq.isFull());
        System.out.println("front index: " + cq.front);
        System.out.println("rear index:  " + cq.rear);

        // --- Dequeue all elements ---
        System.out.println("\n--- Dequeue all remaining elements ---");
        System.out.println("dequeue(): " + cq.dequeue());
        System.out.println("dequeue(): " + cq.dequeue());
        System.out.println("dequeue(): " + cq.dequeue());
        System.out.println("dequeue(): " + cq.dequeue());
        System.out.println("dequeue(): " + cq.dequeue());
        System.out.println(cq);
        System.out.println("isEmpty(): " + cq.isEmpty());   // true
    }
}
