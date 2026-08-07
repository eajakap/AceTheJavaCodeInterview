package org.educative.modules.module2.queues;

public class LinkedListQueue {
    private static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public LinkedListQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public void enqueue(int data) {
        if (this.rear == null) {
            this.rear = new Node(data);
            this.front = this.rear;
        } else {
            this.rear.next = new Node(data);
            this.rear = this.rear.next;
        }
        this.size++;
    }
    public int dequeue() {
        if (this.isEmpty()) {
            return -1;
        }
        int data = this.front.data;
        this.front = this.front.next;
        this.size--;
        if (this.front == null) {
            this.rear = null;
        }
        return data;
    }

    public int peek() {
        if (this.isEmpty()) {
            return -1;
        }
        return this.front.data;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isFull() {
        return false;  // A linked list-based queue is never full unless memory is exhausted
    }

    public static void  main(String[] args) {
        LinkedListQueue queue = new LinkedListQueue();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        System.out.println(queue.dequeue());
        System.out.println(queue.peek());
    }
}
