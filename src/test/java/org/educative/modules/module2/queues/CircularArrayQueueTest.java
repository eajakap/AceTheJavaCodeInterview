package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularArrayQueueTest {
    // CircularQueue is a private nested class, so it is driven through reflection.
    private static Object newQueue(int capacity) throws Exception {
        Class<?> type = Class.forName(CircularArrayQueue.class.getName() + "$CircularQueue");
        Constructor<?> constructor = type.getDeclaredConstructor(int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(capacity);
    }

    private static Object call(Object queue, String name, Object... args) throws Exception {
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = int.class;
        }
        Method method = queue.getClass().getDeclaredMethod(name, types);
        method.setAccessible(true);
        return method.invoke(queue, args);
    }

    @Test
    void newQueueIsEmptyAndNotFull() throws Exception {
        Object queue = newQueue(3);

        assertEquals(true, call(queue, "isEmpty"));
        assertEquals(false, call(queue, "isFull"));
        assertEquals("Queue: []", queue.toString());
    }

    @Test
    void dequeueAndPeekReturnElementsInFifoOrder() throws Exception {
        Object queue = newQueue(3);
        call(queue, "enqueue", 1);
        call(queue, "enqueue", 2);

        assertEquals(1, call(queue, "peek"));
        assertEquals(1, call(queue, "dequeue"));
        assertEquals(2, call(queue, "dequeue"));
        assertEquals(true, call(queue, "isEmpty"));
    }

    @Test
    void emptyDequeueAndPeekReturnMinusOne() throws Exception {
        Object queue = newQueue(2);

        String output = ConsoleOutputCapture.capture(() -> {
            try {
                assertEquals(-1, call(queue, "dequeue"));
                assertEquals(-1, call(queue, "peek"));
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        });

        assertTrue(output.contains("Underflow: Queue is empty"));
        assertTrue(output.contains("Queue is empty"));
    }

    @Test
    void enqueueOnFullQueueLeavesContentsUnchanged() throws Exception {
        Object queue = newQueue(2);
        call(queue, "enqueue", 1);
        call(queue, "enqueue", 2);

        String output = ConsoleOutputCapture.capture(() -> {
            try {
                call(queue, "enqueue", 3);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        });

        assertTrue(output.contains("Overflow: Queue is full"));
        assertEquals("Queue (front to rear): [1, 2]", queue.toString());
    }

    @Test
    void indexesWrapAroundAndFreedSlotsAreReused() throws Exception {
        Object queue = newQueue(3);
        call(queue, "enqueue", 1);
        call(queue, "enqueue", 2);
        call(queue, "enqueue", 3);
        call(queue, "dequeue");
        call(queue, "dequeue");
        call(queue, "enqueue", 4);
        call(queue, "enqueue", 5);

        assertEquals(true, call(queue, "isFull"));
        assertEquals("Queue (front to rear): [3, 4, 5]", queue.toString());
        assertEquals(3, call(queue, "dequeue"));
        assertEquals(4, call(queue, "dequeue"));
        assertEquals(5, call(queue, "dequeue"));
    }

    @Test
    void mainDemonstratesEmptyFullAndWrappedQueueStates() {
        String output = ConsoleOutputCapture.capture(() -> CircularArrayQueue.main(new String[0]));

        assertTrue(output.contains("isEmpty(): true"));
        assertTrue(output.contains("isFull():  true"));
        assertTrue(output.contains("Overflow: Queue is full"));
        assertTrue(output.contains("Underflow: Queue is empty"));
        assertTrue(output.contains("Queue (front to rear): [70, 82, 91, 60, 70]"));
        assertTrue(output.contains("Queue: []"));
    }
}
