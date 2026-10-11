package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashFunctionTest {
    @Test
    void studentsWithSameIdAreEqualWithSameHash() {
        HashFunction.Student first = new HashFunction.Student(42, "apple");
        HashFunction.Student second = new HashFunction.Student(42, "banana");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(Objects.hash(42), first.hashCode());
    }

    @Test
    void studentsWithDifferentIdsAreNotEqual() {
        HashFunction.Student first = new HashFunction.Student(1, "apple");

        assertNotEquals(first, new HashFunction.Student(2, "apple"));
        assertNotEquals(first.hashCode(), new HashFunction.Student(2, "apple").hashCode());
    }

    @Test
    void equalsHandlesIdentityNullAndOtherTypes() {
        HashFunction.Student student = new HashFunction.Student(1, "apple");

        assertTrue(student.equals(student));
        assertFalse(student.equals(null));
        assertFalse(student.equals("1"));
    }

    @Test
    void mainPrintsHashCodes() {
        String output = ConsoleOutputCapture.capture(() -> HashFunction.main(new String[0]));

        assertTrue(output.contains("\"apple\".hashCode() = " + "apple".hashCode()));
        assertTrue(output.contains("Integer.hashCode(42) = 42"));
        assertTrue(output.contains("\"hello\".hashCode() = " + "hello".hashCode()));
        assertTrue(output.contains("student.hashCode() = " + Objects.hash(42)));
    }
}
