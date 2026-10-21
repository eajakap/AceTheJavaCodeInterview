package org.educative.modules.module2.linklists.lrucache;

import org.educative.modules.module2.linklists.LRUCacheMain;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
                Arrays.asList(null, null, null, 10, null, -1, 30, "clear", -1, null, 40),
                results
        );
    }

    @Test
    void runLRUCacheUpdatesExistingValueWithoutEvictingAnotherEntry() {
        List<Object> results = LRUCacheMain.runLRUCache(
                new String[]{"LRUCache", "put", "put", "put", "get", "get"},
                new int[][]{{2}, {1, 10}, {2, 20}, {1, 11}, {1}, {2}}
        );

        assertEquals(Arrays.asList(null, null, null, null, 11, 20), results);
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
        assertTrue(printed.contains("\tResult: [null, null, null, -1, 200, clear]"));
        assertTrue(printed.contains("5.\tInput operations:"));
        assertTrue(printed.contains("\tResult: [null, null, null, null, 2, null, null, -1, 2, 4, 5, clear]"));
    }
}
