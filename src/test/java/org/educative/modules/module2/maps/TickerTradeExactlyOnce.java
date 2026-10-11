package org.educative.modules.module2.maps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates ensuring that each ticker trade is processed exactly once. Returns a list of trades that have been traded exactly once.
 *
 * Steps:
 * 1. Count how many times each trade appears.
 * 2. Trades that appear more than once are duplicates and are ignored entirely.
 * 3. Only trades that appear exactly once are processed, in their original order.
 */
public class TickerTradeExactlyOnce {
    public static String[] processTrades(String[] trades) {
        // LinkedHashMap keeps first-seen order while counting occurrences
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String trade : trades) {
            counts.merge(trade, 1, Integer::sum);
        }

        List<String> uniqueTrades = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            // A trade seen more than once is a duplicate and must be ignored, not processed once
            if (entry.getValue() == 1) {
                uniqueTrades.add(entry.getKey());
            }
        }

        return uniqueTrades.toArray(new String[0]);
    }
    
    public static void main(final String[] args) {
        // Example usage of the TickerTradeExactlyOnce class
        // This is a placeholder for the actual implementation of trade processing logic
        System.out.println("Processing trades to ensure each trade is processed exactly once.");
        String[] trades = {"AAPL", "GOOG", "AAPL", "MSFT"};
        String[] uniqueTrades = processTrades(trades);
        System.out.println("Unique trades: " + java.util.Arrays.toString(uniqueTrades));
    }
}
