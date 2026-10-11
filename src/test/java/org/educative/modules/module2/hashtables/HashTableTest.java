package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableTest {
    // In a table of size 10, single-character keys "a" (97) and "k" (107) both land in slot 7
    @Test
    void insertAndGetRoundTrip() {
        HashTable table = new HashTable(10);
        table.insert("alice", 91);
        table.insert("bob", 85);

        assertEquals(91, table.get("alice"));
        assertEquals(85, table.get("bob"));
    }

    @Test
    void defaultConstructorIsUsable() {
        HashTable table = new HashTable();
        table.insert("a", 1);

        assertEquals(1, table.get("a"));
    }

    @Test
    void insertingSameKeyUpdatesValue() {
        HashTable table = new HashTable(10);
        table.insert("alice", 91);
        table.insert("alice", 95);

        assertEquals(95, table.get("alice"));
    }

    @Test
    void getOfMissingKeyThrows() {
        HashTable table = new HashTable(10);

        RuntimeException e = assertThrows(RuntimeException.class, () -> table.get("missing"));

        assertEquals("Key 'missing' not found", e.getMessage());
    }

    @Test
    void collidingKeysAreRejectedByInsertGetAndDelete() {
        HashTable table = new HashTable(10);
        table.insert("a", 1);

        assertEquals("Collision detected - collision handling required",
                assertThrows(RuntimeException.class, () -> table.insert("k", 2)).getMessage());
        assertEquals("Collision detected - collision handling required",
                assertThrows(RuntimeException.class, () -> table.get("k")).getMessage());
        assertEquals("Collision detected - collision handling required",
                assertThrows(RuntimeException.class, () -> table.delete("k")).getMessage());
        assertEquals(1, table.get("a"));
    }

    @Test
    void deleteRemovesKeyAndFreesSlot() {
        HashTable table = new HashTable(10);
        table.insert("a", 1);

        table.delete("a");

        assertThrows(RuntimeException.class, () -> table.get("a"));
        table.insert("k", 2);
        assertEquals(2, table.get("k"));
    }

    @Test
    void deleteOfMissingKeyThrows() {
        HashTable table = new HashTable(10);

        assertThrows(RuntimeException.class, () -> table.delete("david"));
    }

    @Test
    void displayListsEverySlot() {
        HashTable table = new HashTable(3);
        table.insert("a", 1);  // 97 % 3 = 1

        String output = ConsoleOutputCapture.capture(table::display);

        assertTrue(output.contains("Index 0: null"));
        assertTrue(output.contains("Index 1: (a, 1)"));
        assertTrue(output.contains("Index 2: null"));
    }

    @Test
    void mainRunsAllScenarios() {
        String output = ConsoleOutputCapture.capture(() -> HashTable.main(new String[0]));

        assertTrue(output.contains("Value for 'alice': 91"));
        assertTrue(output.contains("Updated value for 'alice': 95"));
        assertTrue(output.contains("Error: Key 'bob' not found"));
        // "david" lands in a slot occupied by another key
        assertTrue(output.contains("Error: Collision detected - collision handling required"));
    }
}
