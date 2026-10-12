package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiFoQueueSolutionTest {
    @Test
    void stringQueueMaintainsFifoOrderAndSize() {
        FiFoQueueSolution<String> queue = new FiFoQueueSolution<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue("first");
        queue.enqueue("second");

        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals("first", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("second", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void dequeueFromEmptyQueueThrows() {
        FiFoQueueSolution<String> queue = new FiFoQueueSolution<>();

        IllegalStateException exception = assertThrows(IllegalStateException.class, queue::dequeue);

        assertEquals("Queue is empty", exception.getMessage());
    }

    @Test
    void playerQueuePreservesPlayerObjectsAndOrder() {
        FiFoQueueSolution<FiFoQueueSolution.Player> queue = new FiFoQueueSolution<>();
        FiFoQueueSolution.Player first = new FiFoQueueSolution.Player("Avery", 900);
        FiFoQueueSolution.Player second = new FiFoQueueSolution.Player("Blake", 1000);

        queue.enqueue(first);
        queue.enqueue(second);

        assertSame(first, queue.dequeue());
        assertSame(second, queue.dequeue());
    }

    @Test
    void enqueueNullThrows() {
        FiFoQueueSolution<String> queue = new FiFoQueueSolution<>();

        assertThrows(NullPointerException.class, () -> queue.enqueue(null));
    }

    @Test
    void queueCanBeReusedAfterBeingDrained() {
        FiFoQueueSolution<Integer> queue = new FiFoQueueSolution<>();
        queue.enqueue(1);
        queue.dequeue();
        queue.enqueue(2);

        assertEquals(1, queue.size());
        assertEquals(2, queue.dequeue());
        assertThrows(IllegalStateException.class, queue::dequeue);
    }

    @Test
    void playerEqualityAndToString() {
        FiFoQueueSolution.Player player = new FiFoQueueSolution.Player("Avery", 900);

        assertEquals(player, player);
        assertFalse(player.equals(null));
        assertFalse(player.equals("Avery"));
        assertFalse(player.equals(new FiFoQueueSolution.Player("Avery", 901)));
        assertFalse(player.equals(new FiFoQueueSolution.Player("Blake", 900)));
        assertEquals("(Avery, 900)", player.toString());
    }

    @Test
    void playerOrderingUsesScoreThenName() {
        FiFoQueueSolution.Player lowerScore = new FiFoQueueSolution.Player("Zoe", 900);
        FiFoQueueSolution.Player higherScore = new FiFoQueueSolution.Player("Avery", 1000);
        FiFoQueueSolution.Player sameScoreEarlierName = new FiFoQueueSolution.Player("Avery", 900);

        assertTrue(lowerScore.compareTo(higherScore) < 0);
        assertTrue(sameScoreEarlierName.compareTo(lowerScore) < 0);
        assertEquals(new FiFoQueueSolution.Player("Avery", 900), sameScoreEarlierName);
        assertEquals(new FiFoQueueSolution.Player("Avery", 900).hashCode(), sameScoreEarlierName.hashCode());
    }
}
