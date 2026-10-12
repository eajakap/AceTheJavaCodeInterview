package org.educative.modules.module2.stacks.puzzles;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReverseStringTest {

    @Test
    void reverseStringHandlesEmptySingleAndTypicalInputs() {
        assertEquals("", ReverseString.reverseString(""));
        assertEquals("a", ReverseString.reverseString("a"));
        assertEquals("OLLEH", ReverseString.reverseString("HELLO"));
        assertEquals("! dlroW ,olleH", ReverseString.reverseString("Hello, World !"));
    }

    @Test
    void reverseStringPreservesPalindromes() {
        assertEquals("level", ReverseString.reverseString("level"));
    }

    @Test
    void mainPrintsReversedString() {
        String printed = captureStdOut(() -> ReverseString.main(new String[0]));

        assertTrue(printed.contains("Input: HELLO Reverse: OLLEH"));
    }

    private String captureStdOut(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }
}
