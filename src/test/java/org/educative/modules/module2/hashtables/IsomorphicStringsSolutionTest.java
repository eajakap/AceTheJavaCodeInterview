package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsomorphicStringsSolutionTest {
    private final IsomorphicStringsSolution solution = new IsomorphicStringsSolution();

    @Test
    void acceptsConsistentOneToOneMappings() {
        assertTrue(solution.isIsomorphic("abcabc", "xyzxyz"));
        assertTrue(solution.isIsomorphic("abab", "cdcd"));
        assertTrue(solution.isIsomorphic("egg", "add"));
        assertTrue(solution.isIsomorphic("a", "z"));
        assertTrue(solution.isIsomorphic("", ""));
    }

    @Test
    void rejectsTwoCharactersMappingToTheSameTarget() {
        assertFalse(solution.isIsomorphic("ab", "aa"));
        assertFalse(solution.isIsomorphic("abcd", "aabb"));
    }

    @Test
    void rejectsOneCharacterMappingToTwoTargets() {
        assertFalse(solution.isIsomorphic("aa", "ab"));
        assertFalse(solution.isIsomorphic("foo", "bar"));
    }

    @Test
    void rejectsDifferentLengths() {
        assertFalse(solution.isIsomorphic("abc", "ab"));
        assertFalse(solution.isIsomorphic("", "a"));
    }

    @Test
    void mainPrintsResultForEachCase() {
        String output = ConsoleOutputCapture.capture(() -> IsomorphicStringsSolution.main(new String[0]));

        assertEquals(3, output.split("Result: true", -1).length - 1);
        assertEquals(2, output.split("Result: false", -1).length - 1);
    }
}
