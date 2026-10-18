package org.codesignal.debugging.problems;

import java.util.*;

public class TicketManagementSystem {
    private Map<String, Map<String, Object>> tickets;
    private static final boolean NOT_GROUP = false;
    private static final boolean IS_GROUP = true;

    public TicketManagementSystem() {
        this.tickets = new HashMap<>();
    }

    public String sellTicket(String journeyId, double price) {
        String transId = UUID.randomUUID().toString();
        tickets.put(transId, createTicket(journeyId, price, NOT_GROUP, new ArrayList<>()));
        return transId;
    }

    public List<String> sellGroupTicket(String journeyId, int numTickets, double pricePerTicket) {
        List<String> transIds = new ArrayList<>();
        for (int i = 0; i < numTickets; i++) {
            String transId = sellTicket(journeyId, pricePerTicket);
            tickets.get(transId).put("isGroup", IS_GROUP);
            transIds.add(transId);
        }
        for (String transId : transIds) {
            tickets.get(transId).put("groupTransIds", transIds);
        }
        return transIds;
    }

    public boolean refundTicket(String transId) {
        if (tickets.containsKey(transId)) {
            tickets.remove(transId);
            return true;
        }
        return false;
    }

    public Map<String, Object> getTransactionInfo(String transId) {
        return tickets.getOrDefault(transId, null);
    }

    public double totalSales() {
        return tickets.values().stream()
                .mapToDouble(ticket -> (double) ticket.get("price"))
                .sum();
    }

    public Map<String, Integer> journeySalesSummary() {
        Map<String, Integer> summary = new HashMap<>();
        for (Map<String, Object> ticket : tickets.values()) {
            String journeyId = (String) ticket.get("journeyId");
            summary.put(journeyId, summary.getOrDefault(journeyId, 0) + 1);
        }
        return summary;
    }

    private Map<String, Object> createTicket(String journeyId, double price, boolean isGroup, List<String> groupTransIds) {
        Map<String, Object> ticket = new HashMap<>();
        ticket.put("journeyId", journeyId);
        ticket.put("price", price);
        ticket.put("isGroup", isGroup);
        ticket.put("groupTransIds", groupTransIds);
        return ticket;
    }

    public String mergeTickets(List<String> transIds) {
        if (transIds == null || transIds.isEmpty()) {
            return null;
        }
        // refund the ticksets and sell as individual and set Merge flag
        String firstTransId = transIds.get(0);
        if (!tickets.containsKey(firstTransId)) {
            return null;
        }

        double totalPrice = 0;
        List<String> matchedTransIds = new ArrayList<>();
        String firstjourneyId = (String)tickets.get(firstTransId).get("journeyId");
        for (String transId: transIds){
            if (tickets.containsKey(transId)) {
                String journeyId = (String) tickets.get(transId).get("journeyId");
                if (journeyId.equals(firstjourneyId)) {
                    double price = (double) tickets.get(transId).get("price");
                    totalPrice += price;
                    matchedTransIds.add(transId);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        if (matchedTransIds.size() < 2) {
            return null;
        }
        String mergedTransId = sellTicket(firstjourneyId, totalPrice);
        Map<String,Object> mergedTicket = tickets.get(mergedTransId);
        mergedTicket.put("isMerged", true);
        mergedTicket.put("originalTransIds", matchedTransIds);


        // get the price
        for(String mtxnId: matchedTransIds) {
            refundTicket(mtxnId);
        }
        return mergedTransId;
    }

    public List<String> splitTicket(String transId, int numSplits) {
        List<String> result = new ArrayList<>();
        if (!tickets.containsKey(transId) || (numSplits < 2)) {
            return null;
        }

        Map<String,Object> splitTicket = tickets.get(transId);
        String journeyId = (String) tickets.get(transId).get("journeyId");
        double splitTicketPrice = (double) tickets.get(transId).get("price");
        double splitPricePerTicket = splitTicketPrice / numSplits;
        for (int i = 0; i < numSplits; i++) {
            result.add(sellTicket(journeyId, splitPricePerTicket));
        }
        refundTicket(transId);
        return result;
    }

    public static void main(String[] args) {
        TicketManagementSystem solution = new TicketManagementSystem();
        String transId1 = solution.sellTicket("J1", 100.0);
        String transId2 = solution.sellTicket("J1", 150.0);
        String transId3 = solution.sellTicket("J2", 200.0);

        System.out.println("Total Sales: " + solution.totalSales());
        System.out.println("Journey Sales Summary: " + solution.journeySalesSummary());

        List<String> groupTransIds = solution.sellGroupTicket("J3", 3, 50.0);
        System.out.println("Group Transaction IDs: " + groupTransIds);

        String mergedTransId = solution.mergeTickets(Arrays.asList(transId1, transId2));
        System.out.println("Merged Transaction ID: " + mergedTransId);
        System.out.println("Total Sales after merge: " + solution.totalSales());

        List<String> splitTransIds = solution.splitTicket(mergedTransId, 2);
        System.out.println("Split Transaction IDs: " + splitTransIds);
        System.out.println("Total Sales after split: " + solution.totalSales());
    }
}