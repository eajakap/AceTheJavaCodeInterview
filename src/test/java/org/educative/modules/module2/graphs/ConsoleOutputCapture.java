package org.educative.modules.module2.graphs;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/** Captures what an action prints to {@code System.out} so tests can assert on it. */
final class ConsoleOutputCapture {
    private ConsoleOutputCapture() {
    }

    static String capture(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    static List<String> lines(Runnable action) {
        return Arrays.asList(capture(action).split("\n"));
    }
}
