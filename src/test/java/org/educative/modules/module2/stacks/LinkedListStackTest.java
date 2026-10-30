package org.educative.modules.module2.stacks;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListStackTest {

    @Test
    void emptyStackReportsSentinelValues() {
        LinkedListStack stack = new LinkedListStack();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertEquals(-1, stack.top());
        assertEquals(-1, stack.pop());
        assertNull(peekNode(stack));
    }

    @Test
    void pushPopAndPeekFollowLifoOrder() {
        LinkedListStack stack = new LinkedListStack();
        stack.push(9);
        stack.push(3);
        stack.push(1);

        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());
        assertEquals(1, stack.top());

        Object topNode = peekNode(stack);
        assertNotNull(topNode);
        assertEquals(1, nodeData(topNode));
        assertEquals(3, nodeData(nextNode(topNode)));

        assertEquals(1, stack.pop());
        assertEquals(3, stack.top());
        assertEquals(2, stack.size());
        assertEquals(3, stack.pop());
        assertEquals(9, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void stackHandlesNegativeAndLargeValues() {
        LinkedListStack stack = new LinkedListStack();
        stack.push(Integer.MAX_VALUE);
        stack.push(-42);

        assertEquals(-42, stack.top());
        assertEquals(-42, stack.pop());
        assertEquals(Integer.MAX_VALUE, stack.pop());
        assertEquals(-1, stack.pop());
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureStdOut(() -> LinkedListStack.main(new String[0]));

        assertTrue(printed.contains("1.\tOperations: [push, push, push, pop, top, empty]"));
        assertTrue(printed.contains("\tResult: [null, null, null, null, 1, 3, false]"));
        assertTrue(printed.contains("5.\tOperations: [push, pop, push, push, top, empty]"));
        assertTrue(printed.contains("\tResult: [null, null, 6, null, null, 9, false]"));
    }

    private Object peekNode(LinkedListStack stack) {
        try {
            Method peek = LinkedListStack.class.getMethod("peek");
            return peek.invoke(stack);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private int nodeData(Object node) {
        try {
            Field data = node.getClass().getDeclaredField("data");
            data.setAccessible(true);
            return data.getInt(node);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object nextNode(Object node) {
        try {
            Field next = node.getClass().getDeclaredField("next");
            next.setAccessible(true);
            return next.get(node);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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
