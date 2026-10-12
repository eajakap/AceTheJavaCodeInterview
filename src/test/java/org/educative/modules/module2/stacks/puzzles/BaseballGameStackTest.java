package org.educative.modules.module2.stacks.puzzles;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseballGameStackTest {

    @Test
    void calPointsHandlesTypicalAndEdgeCases() {
        assertEquals(30, calPoints("5", "2", "C", "D", "+"));
        assertEquals(27, calPoints("5", "-2", "4", "C", "D", "9", "+", "+"));
        assertEquals(0, calPoints("100", "200", "C", "C"));
        assertEquals(15, calPoints("15"));
    }

    @Test
    void calPointsThrowsWhenOperationsRequireMissingHistory() {
        assertThrows(NoSuchElementException.class, () -> calPoints("C"));
        assertThrows(NoSuchElementException.class, () -> calPoints("4", "+"));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureStdOut(() -> BaseballGameStack.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: [3, 6, +, D, C]"));
        assertTrue(printed.contains("\tResult: 18"));
        assertTrue(printed.contains("5.\tInput array: [8, 4, +, D, +, C]"));
        assertTrue(printed.contains("\tResult: 48"));
    }

    private int calPoints(String... operations) {
        Object solution = newSolution(BaseballGameStack.class.getName() + "$Solution");
        return (int) invoke(solution, "calPoints", new Class<?>[]{String[].class}, (Object) operations);
    }

    private Object newSolution(String className) {
        try {
            Class<?> solutionClass = Class.forName(className);
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
