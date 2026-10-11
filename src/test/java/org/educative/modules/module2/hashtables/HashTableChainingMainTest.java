package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableChainingMainTest {
    // With size 7, "ab", "ba" and "alice" all hash to bucket 6
    private static HashTableChainingMain.HashTableChaining table() {
        return new HashTableChainingMain.HashTableChaining(7);
    }

    @Test
    void firstInsertIntoBucketIsNotACollision() {
        assertArrayEquals(new int[]{6, 0}, table().insert("ab", "1"));
    }

    @Test
    void sharedBucketIsReportedAsCollisionAndChained() {
        HashTableChainingMain.HashTableChaining ht = table();
        ht.insert("ab", "1");

        assertArrayEquals(new int[]{6, 2}, ht.insert("ba", "2"));
        assertEquals(2, ht.getTable().get(6).size());
    }

    @Test
    void insertingExistingKeyUpdatesValueWithoutGrowingChain() {
        HashTableChainingMain.HashTableChaining ht = table();
        ht.insert("ab", "1");

        assertArrayEquals(new int[]{6, 1}, ht.insert("ab", "new"));
        assertEquals(1, ht.getTable().get(6).size());
        assertEquals("new", ht.search("ab")[1]);
    }

    @Test
    void searchFindsChainedKeysAndReportsMissingOnes() {
        HashTableChainingMain.HashTableChaining ht = table();
        ht.insert("ab", "1");
        ht.insert("ba", "2");

        assertEquals("2", ht.search("ba")[1]);
        assertEquals(6, ht.search("ba")[0]);
        assertNull(ht.search("alice")[1]);
    }

    @Test
    void deleteRemovesOnlyTheMatchingEntry() {
        HashTableChainingMain.HashTableChaining ht = table();
        ht.insert("ab", "1");
        ht.insert("ba", "2");

        assertArrayEquals(new int[]{6, 1}, ht.delete("ab"));
        assertNull(ht.search("ab")[1]);
        assertEquals("2", ht.search("ba")[1]);
        assertArrayEquals(new int[]{6, 0}, ht.delete("ab"));
    }

    @Test
    void mainShowsCollisionsAndSearchResults() {
        String output = ConsoleOutputCapture.capture(() -> HashTableChainingMain.main(new String[0]));

        assertTrue(output.contains("Insert \"ab\" -> index 6: No collision"));
        assertTrue(output.contains("Insert \"ba\" -> index 6: Collision occurred"));
        assertTrue(output.contains("Index 6: (ab, 1) -> (ba, 2) -> (alice, 91)"));
        assertTrue(output.contains("Search \"alice\" -> index 6: Found value 91"));
        assertTrue(output.contains("Search \"bob\" -> index"));
        assertTrue(output.contains(": Not found"));
    }
}
