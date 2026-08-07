package org.educative.module4.datastructures.stack.array;

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

        // Simulate a full stack with elements: [88, 95, 72, 80, 91]
        stack.top = 4;  // top index is 4
        System.out.println("Is stack full? " + stack.isFull());  // Output: true (stack is full)

        System.out.println("Simulating popping one element from the stack");
        stack.top = 3;  // top index is now 3
        System.out.println("Is stack full? " + stack.isFull());  // Output: false (stack has space)

        // Push elements one by one
        stack.push(88);   // Stack: [88], top = 0
        stack.push(95);   // Stack: [88, 95], top = 1
        stack.push(72);   // Stack: [88, 95, 72], top = 2
        stack.push(80);   // Stack: [88, 95, 72, 80], top = 3
        stack.push(91);   // Stack: [88, 95, 72, 80, 91], top = 4

        System.out.println("Top index: " + stack.getTop());  // Output: Top index: 4
        // Peek at the top
        System.out.println("Top element of the stack is:" + stack.peek());  // Output: 91, Stack still: [88, 95, 72, 80, 91]
        System.out.println("Top element of the stack is:" + stack.peek());  // Output: 91, Stack still: [88, 95, 72, 80, 91]

        // Pop elements
        System.out.println(stack.pop());  // Output: 91, Stack now: [88, 95, 72, 80]
        System.out.println(stack.pop());  // Output: 80, Stack now: [88, 95, 72]
        System.out.println(stack.pop());  // Output: 72, Stack now: [88, 95]
        System.out.println("Top index after popping 3 elements: " + stack.top);  // Output: 1

    }
}

class Stack {
    int capacity;
    int[] data;
    int top;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
        this.top = -1;
    }

    public Stack() {
        this(10);
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        // Check if the stack is full
        return top == capacity - 1;
    }

    public int getTop() {
        return top;
    }

    public boolean push(int value) {
        // Check if stack is full
        if (isFull()) {
            System.out.println("Stack Overflow! Cannot push element.");
            return false;
        }

        // Increment top and add the element
        top++;
        data[top] = value;
        return true;
    }

    public int pop() {
        // Check if stack is empty
        if (isEmpty()) {
            System.out.println("Stack Underflow! Cannot pop from empty stack.");
            return -1;
        }

        // Get the top element
        int value = data[top];
        // Decrement top
        top--;
        return value;
    }

    public int peek() {
        // Check if stack is empty
        if (isEmpty()) {
            System.out.println("Stack is empty! No element to peek.");
            return -1;
        }

        // Return the top element without modifying top
        return data[top];
    }

}