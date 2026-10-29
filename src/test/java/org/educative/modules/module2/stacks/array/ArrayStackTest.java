package org.educative.modules.module2.stacks.array;

import org.educative.modules.module2.stacks.ArrayStack;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayStackTest {

    @Test
    void stackSupportsPushPopPeekSizeAndFullChecks() {
        ArrayStack.Stack stack = new ArrayStack.Stack(3);

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertEquals(-1, stack.getTop());

        assertTrue(stack.push(10));
        assertTrue(stack.push(20));
        assertTrue(stack.push(30));

        assertFalse(stack.isEmpty());
        assertTrue(stack.isFull());
        assertEquals(3, stack.size());
        assertEquals(2, stack.getTop());
        assertEquals(30, stack.peek());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.peek());
        assertEquals(2, stack.size());
        assertFalse(stack.isFull());
    }

    @Test
    void pushOnFullStackReturnsFalseAndKeepsExistingTop() {
        ArrayStack.Stack stack = new ArrayStack.Stack(1);
        stack.push(7);

        String output = captureStdOut(() -> assertFalse(stack.push(8)));

        assertEquals("Stack Overflow! Cannot push element.", output.trim());
        assertEquals(1, stack.size());
        assertEquals(7, stack.peek());
    }

    @Test
    void peekAndPopOnEmptyStackReturnMinusOneWithMessages() {
        ArrayStack.Stack stack = new ArrayStack.Stack(2);

        String output = captureStdOut(() -> {
            assertEquals(-1, stack.peek());
            assertEquals(-1, stack.pop());
        });

        String[] lines = output.strip().split("\\R");
        assertEquals("Stack is empty! No element to peek.", lines[0]);
        assertEquals("Stack Underflow! Cannot pop from empty stack.", lines[1]);
        assertTrue(stack.isEmpty());
    }

    @Test
    void defaultConstructorCreatesStackWithTenSlots() {
        ArrayStack.Stack stack = new ArrayStack.Stack();

        for (int i = 0; i < 10; i++) {
            assertTrue(stack.push(i));
        }

        assertTrue(stack.isFull());
        assertEquals(10, stack.size());
        assertFalse(stack.push(10));
    }

    private String captureStdOut(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }
}
