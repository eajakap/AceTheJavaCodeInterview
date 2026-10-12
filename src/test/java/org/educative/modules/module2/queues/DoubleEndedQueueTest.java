package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoubleEndedQueueTest {
    @Test
    void createDequeFromStringsPreservesInputOrder() {
        var deque = DoubleEndedQueue.createDeque(new String[]{"Cherry", "Apple", "Banana"});

        assertEquals(List.of("Cherry", "Apple", "Banana"), new ArrayList<>(deque));
    }

    @Test
    void createDequeFromIntegersPreservesInputOrder() {
        var deque = DoubleEndedQueue.createDeque(new int[]{10, 20, 30});

        assertEquals(List.of(10, 20, 30), new ArrayList<>(deque));
    }

    @Test
    void createDequeFromEmptyArraysIsEmpty() {
        assertTrue(DoubleEndedQueue.createDeque(new String[0]).isEmpty());
        assertTrue(DoubleEndedQueue.createDeque(new int[0]).isEmpty());
    }

    @Test
    void returnedDequeSupportsOperationsAtBothEnds() {
        var deque = DoubleEndedQueue.createDeque(new int[]{10, 20, 30});

        deque.addFirst(5);
        assertEquals(30, deque.removeLast());
        assertEquals(5, deque.removeFirst());
        assertEquals(10, deque.peekFirst());
        assertEquals(20, deque.peekLast());
        assertEquals(2, deque.size());
    }

    @Test
    void rotatingRightByOnePlaceMovesLastElementToFront() {
        var deque = DoubleEndedQueue.createDeque(new String[]{"Apple", "Banana", "Cherry"});

        deque.addFirst(deque.removeLast());

        assertEquals(List.of("Cherry", "Apple", "Banana"), new ArrayList<>(deque));
    }
}
