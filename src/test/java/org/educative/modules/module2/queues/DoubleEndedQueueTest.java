package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
