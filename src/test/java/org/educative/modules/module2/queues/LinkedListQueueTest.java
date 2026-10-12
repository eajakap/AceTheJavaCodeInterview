package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListQueueTest {
    @Test
    void enqueueAndDequeueMaintainFifoOrderAndClearLastNode() {
        LinkedListQueue queue = new LinkedListQueue();

        assertTrue(queue.isEmpty());
        assertEquals(-1, queue.peek());
        assertEquals(-1, queue.dequeue());

        queue.enqueue(4);
        queue.enqueue(8);
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals(4, queue.peek());
        assertEquals(4, queue.dequeue());
        assertEquals(8, queue.dequeue());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());

        queue.enqueue(12);
        assertEquals(12, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void largeQueueKeepsFifoOrder() {
        LinkedListQueue queue = new LinkedListQueue();
        for (int value = 0; value < 1000; value++) {
            queue.enqueue(value);
        }

        for (int value = 0; value < 1000; value++) {
            assertEquals(value, queue.dequeue());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void interleavedEnqueueAndDequeue() {
        LinkedListQueue queue = new LinkedListQueue();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        queue.enqueue(3);

        assertEquals(2, queue.peek());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(-1, queue.dequeue());
    }

    @Test
    void negativeValuesAreStoredNormally() {
        LinkedListQueue queue = new LinkedListQueue();
        queue.enqueue(-5);

        assertEquals(-5, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void linkedQueueIsNotCapacityBounded() {
        LinkedListQueue queue = new LinkedListQueue();

        assertFalse(queue.isFull());
        for (int value = 0; value < 100; value++) {
            queue.enqueue(value);
        }
        assertEquals(100, queue.getSize());
    }
}
