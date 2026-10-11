package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableLinearProbingMainTest {
    // With size 11: "ab" and "ba" hash to 8 (195 % 11), "alice" hashes to 4 (510 % 11)
    private static HashTableLinearProbingMain.HashTableLinearProbing table() {
        return new HashTableLinearProbingMain.HashTableLinearProbing(11);
    }

    @Test
    void insertIntoEmptySlotDoesNotProbe() {
        assertArrayEquals(new int[]{8, 8, 0, 0}, table().insert("ab", "1"));
    }

    @Test
    void collisionIsResolvedByProbingToNextSlot() {
        HashTableLinearProbingMain.HashTableLinearProbing ht = table();
        ht.insert("ab", "1");

        assertArrayEquals(new int[]{8, 9, 1, 0}, ht.insert("ba", "2"));
        assertEquals("ba", ht.getTable()[9][0]);
    }

    @Test
    void insertingExistingKeyUpdatesValue() {
        HashTableLinearProbingMain.HashTableLinearProbing ht = table();
        ht.insert("ab", "1");

        assertArrayEquals(new int[]{8, 8, 0, 1}, ht.insert("ab", "updated"));
        assertEquals("updated", ht.getTable()[8][1]);
    }

    @Test
    void searchFindsKeysIncludingProbedOnes() {
        HashTableLinearProbingMain.HashTableLinearProbing ht = table();
        ht.insert("ab", "1");
        ht.insert("ba", "2");

        assertArrayEquals(new int[]{8, 8, 0, 1}, ht.search("ab"));
        assertArrayEquals(new int[]{8, 9, 1, 1}, ht.search("ba"));
    }

    @Test
    void searchReportsMissingKey() {
        HashTableLinearProbingMain.HashTableLinearProbing ht = table();
        ht.insert("ab", "1");

        int[] result = ht.search("alice");

        assertEquals(-1, result[3]);
        assertNull(ht.getTable()[result[1]]);
    }

    @Test
    void wrapsAroundAndRejectsInsertsWhenFull() {
        HashTableLinearProbingMain.HashTableLinearProbing ht = new HashTableLinearProbingMain.HashTableLinearProbing(2);
        ht.insert("a", "1");  // 97 % 2 = 1
        assertArrayEquals(new int[]{1, 0, 1, 0}, ht.insert("c", "2"));  // 99 % 2 = 1, wraps to 0

        assertThrows(RuntimeException.class, () -> ht.insert("e", "3"));
        assertEquals(-1, ht.search("g")[3]);
    }

    @Test
    void mainDemonstratesCollisionAndLookups() {
        String output = ConsoleOutputCapture.capture(() -> HashTableLinearProbingMain.main(new String[0]));

        assertTrue(output.contains("Collision at index 8"));
        assertTrue(output.contains("Inserted at index 9 after 1 probe(s)."));
        assertTrue(output.contains("Index 4: (alice, 91)"));
        assertTrue(output.contains("Found at index 9 after 1 probe(s)"));
        assertTrue(output.contains("Key not found after"));
    }
}
