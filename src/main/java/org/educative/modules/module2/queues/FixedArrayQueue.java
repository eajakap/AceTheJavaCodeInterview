package org.educative.modules.module2.queues;

public class FixedArrayQueue {
    private int capacity;    // Capacity of the queue
    private int[] data;      // Array to store the data
    private int front;       // Initially -1 to indicate empty queue
    private int rear;        // Initially -1 to indicate empty queue
    private int size;        // Size of the queue

    public FixedArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public FixedArrayQueue() {
        this(10);   // Default capacity of 10
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public boolean isFull() {
        return this.size == this.capacity;
    }
    public boolean enqueue(int value) {
        if (this.isFull()) {
            System.out.println("Queue is full. Cannot enqueue " + value);
            return false;
        }
        this.rear++;
        this.data[this.rear] = value;
        this.size++;
        return true;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow! Cannot dequeue from empty queue.");
            return -1;
        }
        int value = data[front];
        front++;
        size--;
        if (this.size == 0) {
            this.front = 0;
            this.rear = -1;
        }
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty! No element to peek.");
            return -1;
        }
        return data[front];
    }

    public int getSize() {
        // Return the number of elements in the queue
        return size;   // Simply return the size counter
    }

    public static void main(String[] args) {
        FixedArrayQueue queue = new FixedArrayQueue(10);

        // Enqueue elements one by one: [88, 95, 72, 80, 91]
        System.out.println("Enqueue 88");
        queue.enqueue(88);
        System.out.println("Enqueue 95");
        queue.enqueue(95);
        System.out.println("Enqueue 72");
        queue.enqueue(72);
        System.out.println("Enqueue 80");
        queue.enqueue(80);
        System.out.println("Enqueue 91");
        queue.enqueue(91);

        System.out.println("Peek at the front of the queue:" + queue.peek());    // Output: 88, Queue still: [88, 95, 72, 80, 91]
        System.out.println("Peek at the front of the queue:" + queue.peek());    // Output: 88, Queue still: [88, 95, 72, 80, 91]

        System.out.println("The size of the queue is: " + queue.size());  // Output: 5

        // Dequeue elements
        System.out.println("Dequeue an element: " + queue.dequeue());  // Output: 88, Queue now: [95, 72, 80, 91]
        System.out.println("Dequeue an element: " + queue.dequeue());  // Output: 95, Queue now: [72, 80, 91]
        System.out.println("Dequeue an element: " + queue.dequeue());  // Output: 72, Queue now: [80, 91]
        System.out.println("The size of the queue is: " + queue.size());  // Output: 2
    }

    }