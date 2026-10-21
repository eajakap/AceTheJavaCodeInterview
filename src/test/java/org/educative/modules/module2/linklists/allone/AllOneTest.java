package org.educative.modules.module2.linklists.allone;

import org.educative.modules.module2.linklists.AllOne;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AllOneTest {
    @Test
    void incDecAndQueriesTrackKeyCountsAcrossLifecycle() {
        AllOne allOne = new AllOne();

        assertEquals("", allOne.getMaxKey());
        assertEquals("", allOne.getMinKey());

        allOne.inc("apple");
        assertEquals("apple", allOne.getMaxKey());
        assertEquals("apple", allOne.getMinKey());

        allOne.inc("banana");
        allOne.inc("apple");
        assertEquals("apple", allOne.getMaxKey());
        assertEquals("banana", allOne.getMinKey());

        allOne.dec("apple");
        assertTrue(Set.of("apple", "banana").contains(allOne.getMaxKey()));
        assertTrue(Set.of("apple", "banana").contains(allOne.getMinKey()));

        allOne.dec("apple");
        assertEquals("banana", allOne.getMaxKey());
        assertEquals("banana", allOne.getMinKey());

        allOne.dec("banana");
        assertEquals("", allOne.getMaxKey());
        assertEquals("", allOne.getMinKey());
    }

    @Test
    void reinsertingAfterCountsDropToZeroWorks() {
        AllOne allOne = new AllOne();

        allOne.inc("x");
        allOne.inc("x");
        allOne.dec("x");
        assertEquals("x", allOne.getMaxKey());
        assertEquals("x", allOne.getMinKey());

        allOne.dec("x");
        assertEquals("", allOne.getMaxKey());
        assertEquals("", allOne.getMinKey());

        allOne.inc("y");
        allOne.inc("z");
        allOne.inc("z");

        assertEquals("z", allOne.getMaxKey());
        assertEquals("y", allOne.getMinKey());
    }

    @Test
    void mainPrintsBuiltInExamples() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            AllOne.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("Test Case 1:"));
        assertTrue(printed.contains("getMaxKey() -> \"apple\""));
        assertTrue(printed.contains("Test Case 5:"));
        assertTrue(printed.contains("Result: [null, null, null, null, null, null, \"c\", \"c\"]"));
    }
}
