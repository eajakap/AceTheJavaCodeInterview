package org.educative.modules.module2.maps;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityOrderSolutionTest {
    @Test
    void processOrderReturnsLowestPriorityNumberFirstAndRemovesIt() {
        PriorityOrderSolution manager = new PriorityOrderSolution();
        manager.addOrder("John", 2);
        manager.addOrder("Alice", 1);

        assertEquals("1, Alice", manager.processOrder());
        assertEquals("2, John", manager.processOrder());
        assertEquals("No orders", manager.processOrder());
    }

    @Test
    void processOrderOnEmptyManagerReportsNoOrders() {
        assertEquals("No orders", new PriorityOrderSolution().processOrder());
    }

    @Test
    void addingSamePriorityReplacesPreviousCustomer() {
        PriorityOrderSolution manager = new PriorityOrderSolution();
        manager.addOrder("John", 1);
        manager.addOrder("Alice", 1);

        assertEquals("1, Alice", manager.processOrder());
        assertEquals("No orders", manager.processOrder());
    }

    @Test
    void mainPrintsHighestPriorityOrder() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> PriorityOrderSolution.main(new String[0]));

        assertEquals(List.of("1, Alice"), lines);
    }
}
