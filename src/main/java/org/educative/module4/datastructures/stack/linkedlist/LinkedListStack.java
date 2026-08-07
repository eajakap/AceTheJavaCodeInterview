package org.educative.module4.datastructures.stack.linkedlist;


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
}