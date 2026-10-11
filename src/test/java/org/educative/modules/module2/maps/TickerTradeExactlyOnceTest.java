package org.educative.modules.module2.maps;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TickerTradeExactlyOnceTest {
    @Test
    void ignoresTradesThatAppearMoreThanOnce() {
        assertArrayEquals(new String[]{"GOOG", "MSFT"},
                TickerTradeExactlyOnce.processTrades(new String[]{"AAPL", "GOOG", "AAPL", "MSFT"}));
    }

    @Test
    void keepsAllTradesWhenNoneRepeat() {
        assertArrayEquals(new String[]{"AAPL", "GOOG", "MSFT"},
                TickerTradeExactlyOnce.processTrades(new String[]{"AAPL", "GOOG", "MSFT"}));
    }

    @Test
    void returnsNothingWhenEveryTradeRepeats() {
        assertEquals(0, TickerTradeExactlyOnce.processTrades(new String[]{"AAPL", "AAPL", "GOOG", "GOOG"}).length);
    }

    @Test
    void tradeSeenThreeTimesIsStillIgnored() {
        assertArrayEquals(new String[]{"MSFT"},
                TickerTradeExactlyOnce.processTrades(new String[]{"AAPL", "AAPL", "MSFT", "AAPL"}));
    }

    @Test
    void preservesOriginalOrderOfSingleTrades() {
        assertArrayEquals(new String[]{"ZZZ", "AAA", "MMM"},
                TickerTradeExactlyOnce.processTrades(new String[]{"ZZZ", "DUP", "AAA", "DUP", "MMM"}));
    }

    @Test
    void emptyInputReturnsEmptyResult() {
        assertEquals(0, TickerTradeExactlyOnce.processTrades(new String[]{}).length);
    }

    @Test
    void tickersAreCaseSensitive() {
        assertArrayEquals(new String[]{"aapl", "AAPL"},
                TickerTradeExactlyOnce.processTrades(new String[]{"aapl", "AAPL"}));
    }

    @Test
    void mainPrintsOnlyTradesProcessedOnce() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> TickerTradeExactlyOnce.main(new String[0]));

        assertEquals(List.of(
                "Processing trades to ensure each trade is processed exactly once.",
                "Unique trades: [GOOG, MSFT]"), lines);
    }
}
