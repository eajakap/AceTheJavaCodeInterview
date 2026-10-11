package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularArrayQueueTest {
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
