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
    void linkedQueueIsNotCapacityBounded() {
        LinkedListQueue queue = new LinkedListQueue();

        assertFalse(queue.isFull());
        for (int value = 0; value < 100; value++) {
            queue.enqueue(value);
        }
        assertEquals(100, queue.getSize());
    }
}
