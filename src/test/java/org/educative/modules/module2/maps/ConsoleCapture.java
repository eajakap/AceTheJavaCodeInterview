package org.educative.modules.module2.maps;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/** Captures what a {@code main} method prints so tests can assert on it. */
final class ConsoleCapture {
    private ConsoleCapture() {
    }

    static List<String> linesPrintedBy(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return Arrays.asList(output.toString(StandardCharsets.UTF_8).split("\\R"));
    }
}
