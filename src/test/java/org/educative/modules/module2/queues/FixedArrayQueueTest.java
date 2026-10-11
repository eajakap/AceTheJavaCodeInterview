package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedArrayQueueTest {
    @Test
    void enqueuePeekAndDequeueMaintainFifoOrder() {
        FixedArrayQueue queue = new FixedArrayQueue(3);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
        assertTrue(queue.enqueue(11));
        assertTrue(queue.enqueue(22));
        assertEquals(11, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(11, queue.dequeue());
        assertEquals(22, queue.dequeue());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void fullQueueRejectsAdditionalElementsAndCanBeReusedAfterDrain() {
        FixedArrayQueue queue = new FixedArrayQueue(1);
        queue.enqueue(7);

        String output = ConsoleOutputCapture.capture(() -> assertFalse(queue.enqueue(8)));
        assertTrue(output.contains("Queue is full. Cannot enqueue 8"));
        assertTrue(queue.isFull());
        assertEquals(7, queue.dequeue());
        assertTrue(queue.isEmpty());

        assertTrue(queue.enqueue(9));
        assertEquals(9, queue.peek());
    }

    @Test
    void emptyPeekAndDequeueReturnSentinelAndPrintMessages() {
        FixedArrayQueue queue = new FixedArrayQueue(2);

        String output = ConsoleOutputCapture.capture(() -> {
            assertEquals(-1, queue.peek());
            assertEquals(-1, queue.dequeue());
        });

        assertTrue(output.contains("Queue is empty! No element to peek."));
        assertTrue(output.contains("Queue Underflow! Cannot dequeue from empty queue."));
    }

    @Test
    void defaultConstructorCreatesCapacityOfTen() {
        FixedArrayQueue queue = new FixedArrayQueue();

        for (int value = 0; value < 10; value++) {
            assertTrue(queue.enqueue(value));
        }
        assertTrue(queue.isFull());
        assertEquals(10, queue.getSize());
    }
}
