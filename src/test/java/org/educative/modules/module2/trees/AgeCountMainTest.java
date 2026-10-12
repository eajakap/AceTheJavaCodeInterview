package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgeCountMainTest {
    private static volatile String mockResponse = "{\"data\":\"age=50, age=49, age=77\"}";
    private static volatile IOException mockFailure;

    static {
        URL.setURLStreamHandlerFactory(new MockHttpsFactory());
    }

    @Test
    void mainCountsAgesAtLeastFiftyAndTransformsTheTokenizedOutput() {
        mockFailure = null;
        mockResponse = "{\"data\":\"name=bob, age=50, age=49, age=abc, age=77, age=51\"}";

        String output = captureOutput(() -> AgeCountMain.main(new String[0]));
        String[] lines = normalizedLines(output);

        assertEquals(2, lines.length);
        assertEquals("3", lines[0]);
        assertEquals("3fXvwX7hX9b", lines[1]);
    }

    @Test
    void mainPrintsIoExceptionWhenTheRemoteReadFails() {
        mockFailure = new IOException("boom");

        String output = captureOutput(() -> AgeCountMain.main(new String[0]));

        assertTrue(output.contains("java.io.IOException: boom"));
        mockFailure = null;
    }

    private static String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").trim();
    }

    private static String[] normalizedLines(String output) {
        if (output.isEmpty()) {
            return new String[0];
        }
        return output.split("\n");
    }

    private static final class MockHttpsFactory implements URLStreamHandlerFactory {
        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            if (!"https".equals(protocol)) {
                return null;
            }
            return new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) {
                    return new URLConnection(url) {
                        @Override
                        public void connect() {
                        }

                        @Override
                        public InputStream getInputStream() throws IOException {
                            if ("coderbyte.com".equals(url.getHost())
                                    && "/api/challenges/json/age-counting".equals(url.getPath())) {
                                if (mockFailure != null) {
                                    throw mockFailure;
                                }
                                return new ByteArrayInputStream(mockResponse.getBytes(StandardCharsets.UTF_8));
                            }
                            throw new IOException("Unexpected URL: " + url);
                        }
                    };
                }
            };
        }
    }
}
