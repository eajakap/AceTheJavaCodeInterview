package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketManagementSystemTest {
    @Test
    void testSellIndividualTickets() {
        TicketManagementSystem solution = new TicketManagementSystem();

        String firstId = solution.sellTicket("J1", 100.0);
        String secondId = solution.sellTicket("J2", 75.5);

        assertNotNull(firstId);
        assertNotNull(secondId);
        assertFalse(firstId.equals(secondId));
        assertEquals(Map.of(
                "journeyId", "J1",
                "price", 100.0,
                "isGroup", false,
                "groupTransIds", List.of()
        ), solution.getTransactionInfo(firstId));
        assertEquals(Map.of(
                "journeyId", "J2",
                "price", 75.5,
                "isGroup", false,
                "groupTransIds", List.of()
        ), solution.getTransactionInfo(secondId));
    }

    @Test
    void testSellGroupTickets() {
        TicketManagementSystem solution = new TicketManagementSystem();

        List<String> transactionIds = solution.sellGroupTicket("J1", 3, 40.0);

        assertEquals(3, transactionIds.size());
        for (String transactionId : transactionIds) {
            assertEquals(Map.of(
                    "journeyId", "J1",
                    "price", 40.0,
                    "isGroup", true,
                    "groupTransIds", transactionIds
            ), solution.getTransactionInfo(transactionId));
        }
        assertEquals(120.0, solution.totalSales());
        assertEquals(Map.of("J1", 3), solution.journeySalesSummary());
    }

    @Test
    void testRefundTicket() {
        TicketManagementSystem solution = new TicketManagementSystem();
        String transactionId = solution.sellTicket("J1", 100.0);

        assertTrue(solution.refundTicket(transactionId));
        assertNull(solution.getTransactionInfo(transactionId));
        assertFalse(solution.refundTicket(transactionId));
        assertEquals(0.0, solution.totalSales());
    }

    @Test
    void testTotalSalesAndSummary() {
        TicketManagementSystem solution = new TicketManagementSystem();
        solution.sellTicket("J1", 100.0);
        solution.sellTicket("J1", 50.0);
        solution.sellTicket("J2", 75.0);

        assertEquals(225.0, solution.totalSales());
        assertEquals(Map.of("J1", 2, "J2", 1), solution.journeySalesSummary());
    }

    @Test
    void testMergeTickets() {
        TicketManagementSystem solution = new TicketManagementSystem();
        String firstId = solution.sellTicket("J1", 100.0);
        String secondId = solution.sellTicket("J1", 150.0);
        String unrelatedId = solution.sellTicket("J2", 50.0);

        String mergedId = solution.mergeTickets(List.of(firstId, secondId));

        assertNotNull(mergedId);
        assertNull(solution.getTransactionInfo(firstId));
        assertNull(solution.getTransactionInfo(secondId));
        assertEquals(Map.of(
                "journeyId", "J1",
                "price", 250.0,
                "isGroup", false,
                "groupTransIds", List.of(),
                "isMerged", true,
                "originalTransIds", List.of(firstId, secondId)
        ), solution.getTransactionInfo(mergedId));
        assertEquals(300.0, solution.totalSales());
        assertEquals(Map.of("J1", 1, "J2", 1), solution.journeySalesSummary());
        assertNotNull(solution.getTransactionInfo(unrelatedId));
    }

    @Test
    void testMergeTicketsRejectsInvalidInputs() {
        TicketManagementSystem solution = new TicketManagementSystem();
        String firstId = solution.sellTicket("J1", 100.0);
        String otherJourneyId = solution.sellTicket("J2", 50.0);

        assertNull(solution.mergeTickets(null));
        assertNull(solution.mergeTickets(List.of()));
        assertNull(solution.mergeTickets(List.of(firstId)));
        assertNull(solution.mergeTickets(List.of("missing", firstId)));
        assertNull(solution.mergeTickets(List.of(firstId, "missing")));
        assertNull(solution.mergeTickets(List.of(firstId, otherJourneyId)));

        assertNotNull(solution.getTransactionInfo(firstId));
        assertNotNull(solution.getTransactionInfo(otherJourneyId));
        assertEquals(150.0, solution.totalSales());
    }

    @Test
    void testSplitTickets() {
        TicketManagementSystem solution = new TicketManagementSystem();
        String transactionId = solution.sellTicket("J1", 120.0);

        List<String> splitIds = solution.splitTicket(transactionId, 3);

        assertNotNull(splitIds);
        assertEquals(3, splitIds.size());
        assertNull(solution.getTransactionInfo(transactionId));
        for (String splitId : splitIds) {
            assertEquals("J1", solution.getTransactionInfo(splitId).get("journeyId"));
            assertEquals(40.0, solution.getTransactionInfo(splitId).get("price"));
            assertEquals(false, solution.getTransactionInfo(splitId).get("isGroup"));
        }
        assertEquals(120.0, solution.totalSales());
        assertEquals(Map.of("J1", 3), solution.journeySalesSummary());
    }

    @Test
    void testSplitTicketsRejectsInvalidInputs() {
        TicketManagementSystem solution = new TicketManagementSystem();
        String transactionId = solution.sellTicket("J1", 120.0);

        assertNull(solution.splitTicket("missing", 2));
        assertNull(solution.splitTicket(transactionId, 0));
        assertNull(solution.splitTicket(transactionId, 1));
        assertNotNull(solution.getTransactionInfo(transactionId));
        assertEquals(120.0, solution.totalSales());
    }
}
