package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void newStackIsEmpty() {
        assertTrue(new MyStack().empty());
    }

    @Test
    void popsInLastInFirstOutOrder() {
        MyStack stack = new MyStack();
        stack.push(9);
        stack.push(3);
        stack.push(1);
        assertEquals(1, stack.pop());
        assertEquals(3, stack.top());
        assertEquals(3, stack.pop());
        assertEquals(9, stack.pop());
        assertTrue(stack.empty());
    }

    @Test
    void topDoesNotRemoveElement() {
        MyStack stack = new MyStack();
        stack.push(5);
        stack.push(7);
        assertEquals(7, stack.top());
        assertEquals(7, stack.top());
        assertFalse(stack.empty());
    }

    @Test
    void pushAfterPopKeepsOrder() {
        MyStack stack = new MyStack();
        stack.push(6);
        assertEquals(6, stack.pop());
        stack.push(8);
        stack.push(9);
        assertEquals(9, stack.top());
        assertFalse(stack.empty());
    }

    @Test
    void interleavedPushPopTop() {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        stack.push(3);
        assertEquals(3, stack.top());
        assertEquals(3, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.empty());
    }

    @Test
    void handlesManyElements() {
        MyStack stack = new MyStack();
        for (int i = 0; i < 500; i++) {
            stack.push(i);
        }
        for (int i = 499; i >= 0; i--) {
            assertEquals(i, stack.pop());
        }
        assertTrue(stack.empty());
    }

    @Test
    void popAndTopOnEmptyStackThrow() {
        MyStack stack = new MyStack();
        assertThrows(NullPointerException.class, stack::pop);
        assertThrows(NullPointerException.class, stack::top);
    }
}
