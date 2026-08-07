package org.educative.modules.module2.queues;

public class CircularArrayQueue {
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

class CircularQueue {
    private int capacity;
    private int[] queue;
    int front;
    int rear;
    int size;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void enqueue(int element) {
        if (isFull()) {
            System.out.println("Overflow: Queue is full");
            return;
        }
        queue[rear] = element;
        rear = (rear + 1) % capacity;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Underflow: Queue is empty");
            return -1;
        }
        int element = queue[front];
        queue[front] = 0;
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return queue[front];
    }

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
            i = (i + 1) % capacity;
        }
        return "Queue (front to rear): [" + sb + "]";
    }
}