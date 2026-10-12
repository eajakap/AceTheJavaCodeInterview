package org.educative.modules.module2.stacks.puzzles;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecodeStringStackTest {

    @Test
    void decodeStringHandlesPlainNestedAndMultiDigitInputs() {
        assertEquals("aaaaaaaaaa", decodeString("10[a]"));
        assertEquals("abbbabbb", decodeString("2[a3[b]]"));
        assertEquals("abccdcdcdxyz", decodeString("abc3[cd]xyz"));
        assertEquals("", decodeString(""));
    }

    @Test
    void decodeStringAjayMatchesExpectedDecoding() {
        assertEquals("abababababababababab", decodeStringAjay("10[ab]"));
        assertEquals("ababcababc", decodeStringAjay("2[2[ab]c]"));
        assertEquals("xyz", decodeStringAjay("xyz"));
        assertEquals("", decodeStringAjay(""));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureStdOut(() -> DecodeStringStack.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: [\"10[a]\"]"));
        assertTrue(printed.contains("\tTarget: \"aaaaaaaaaa\""));
        assertTrue(printed.contains("\tResult: \"ababcababc\""));
    }

    private String decodeString(String input) {
        Object solution = newSolution();
        return (String) invoke(solution, "decodeString", new Class<?>[]{String.class}, input);
    }

    private String decodeStringAjay(String input) {
        Object solution = newSolution();
        return (String) invoke(solution, "decodeStringAjay", new Class<?>[]{String.class}, input);
    }

    private Object newSolution() {
        try {
            Class<?> solutionClass = Class.forName(DecodeStringStack.class.getName() + "$Solution");
            Constructor<?> constructor = solutionClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (targetException instanceof Error error) {
                throw error;
            }
            throw new RuntimeException(targetException);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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
