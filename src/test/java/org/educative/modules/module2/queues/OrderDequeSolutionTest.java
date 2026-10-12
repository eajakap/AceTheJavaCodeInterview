package org.educative.modules.module2.queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderDequeSolutionTest {
    @Test
    void servesOrdersInFifoOrder() {
        OrderDequeSolution queue = new OrderDequeSolution();
        queue.addOrder(101);
        queue.addOrder(102);
        queue.addOrder(103);

        assertEquals(101, queue.serveOrder());
        assertEquals(102, queue.serveOrder());
        assertEquals(103, queue.serveOrder());
    }

    @Test
    void servingAfterQueueIsDrainedThrowsAndQueueIsReusable() {
        OrderDequeSolution queue = new OrderDequeSolution();
        queue.addOrder(1);
        queue.serveOrder();

        assertThrows(IllegalStateException.class, queue::serveOrder);
        queue.addOrder(2);
        assertEquals(2, queue.serveOrder());
    }

    @Test
    void duplicateOrderIdsAreServedSeparately() {
        OrderDequeSolution queue = new OrderDequeSolution();
        queue.addOrder(7);
        queue.addOrder(7);

        assertEquals(7, queue.serveOrder());
        assertEquals(7, queue.serveOrder());
        assertThrows(IllegalStateException.class, queue::serveOrder);
    }

    @Test
    void servingFromEmptyQueueThrows() {
        OrderDequeSolution queue = new OrderDequeSolution();

        IllegalStateException exception = assertThrows(IllegalStateException.class, queue::serveOrder);

        assertEquals("No orders to serve", exception.getMessage());
    }
}
