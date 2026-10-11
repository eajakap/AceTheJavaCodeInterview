package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateNumberTest {
    private final DuplicateNumber solution = new DuplicateNumber();

    @Test
    void detectsDuplicates() {
        assertTrue(solution.containsDuplicate(new int[]{5, 3, 7, 5, 2}));
        assertTrue(solution.containsDuplicate(new int[]{-1000000000, 1000000000, -1000000000}));
        assertTrue(solution.containsDuplicate(new int[]{1, 1}));
    }

    @Test
    void reportsNoDuplicatesForDistinctValues() {
        assertFalse(solution.containsDuplicate(new int[]{10, 20, 30, 40, 50}));
        assertFalse(solution.containsDuplicate(new int[]{42}));
        assertFalse(solution.containsDuplicate(new int[]{}));
    }

    @Test
    void mainPrintsResultForEachCase() {
        String output = ConsoleOutputCapture.capture(() -> DuplicateNumber.main(new String[0]));

        assertEquals(3, output.split("Result: true", -1).length - 1);
        assertEquals(2, output.split("Result: false", -1).length - 1);
    }
}
