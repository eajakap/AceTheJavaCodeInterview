package org.educative.modules.module2.linklists.lrucache;

import org.educative.modules.module2.linklists.LRUCacheMain;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LRUCacheMainTest {
    @Test
    void runLRUCacheHandlesAccessUpdatesEvictionAndClear() {
        List<Object> results = LRUCacheMain.runLRUCache(
                new String[]{"LRUCache", "put", "put", "get", "put", "get", "get", "clear", "get", "put", "get"},
                new int[][]{{2}, {1, 10}, {2, 20}, {1}, {3, 30}, {2}, {3}, {}, {3}, {4, 40}, {4}}
        );

        assertEquals(
                List.of("capacity: {2}", "put: {1: 10}", "put: {2: 20}", "get: {1: 10}", "put: {3: 30}",
                        "get: {2: -1}", "get: {3: 30}", "clear", "get: {3: -1}", "put: {4: 40}", "get: {4: 40}"),
                results
        );
    }

    @Test
    void runLRUCacheUpdatesExistingValueWithoutEvictingAnotherEntry() {
        List<Object> results = LRUCacheMain.runLRUCache(
                new String[]{"LRUCache", "put", "put", "put", "get", "get"},
                new int[][]{{2}, {1, 10}, {2, 20}, {1, 11}, {1}, {2}}
        );

        assertEquals(
                List.of("capacity: {2}", "put: {1: 10}", "put: {2: 20}", "put: {1: 11}", "get: {1: 11}", "get: {2: 20}"),
                results
        );
    }

    @Test
    void runLRUCacheReportsInvalidOperations() {
        List<Object> results = LRUCacheMain.runLRUCache(
                new String[]{"unknown"},
                new int[][]{{}}
        );

        assertEquals(List.of("Invalid operation"), results);
    }

    @Test
    void mainPrintsTheBuiltInCacheExamples() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            LRUCacheMain.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("1.\tInput operations:"));
        assertTrue(printed.contains("\tResult: [capacity: {1}, put: {5: 100}, put: {6: 200}, get: {5: -1}, "
                + "get: {6: 200}, print: {6=200}, clear]"));
        assertTrue(printed.contains("5.\tInput operations:"));
        assertTrue(printed.contains("\tResult: [capacity: {3}, put: {100: 1}, put: {200: 2}, put: {300: 3}, "
                + "get: {200: 2}, put: {400: 4}, put: {500: 5}, get: {100: -1}, get: {200: 2}, get: {400: 4}, "
                + "get: {500: 5}, print: {"));
    }
}
