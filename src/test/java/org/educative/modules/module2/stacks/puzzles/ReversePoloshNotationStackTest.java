package org.educative.modules.module2.stacks.puzzles;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReversePoloshNotationStackTest {

    @Test
    void evalRpnHandlesArithmeticPrecedenceEncodedByPostfixNotation() {
        assertEquals(7, evalRpn("3", "4", "+"));
        assertEquals(4, evalRpn("5", "3", "-", "2", "*"));
        assertEquals(-3, evalRpn("-10", "3", "/"));
        assertEquals(14, evalRpn("5", "1", "2", "+", "4", "*", "+", "3", "-"));
    }

    @Test
    void ajayyEvalRpnMatchesStandardCasesAndHandlesEmptyInput() {
        assertEquals(6, ajayyEvalRpn("4", "13", "5", "/", "+"));
        assertEquals(22, ajayyEvalRpn("10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"));
        assertEquals(-1, ajayyEvalRpn());
    }

    @Test
    void evaluationThrowsForInvalidTokensOrMissingOperands() {
        assertThrows(NumberFormatException.class, () -> evalRpn("2", "x", "+"));
        assertThrows(java.util.NoSuchElementException.class, () -> ajayyEvalRpn("+"));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureStdOut(() -> ReversePoloshNotationStack.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: [3, 4, +]"));
        assertTrue(printed.contains("\tResult: 7"));
        assertTrue(printed.contains("5.\tInput array: [5, 1, 2, +, 4, *, +, 3, -]"));
        assertTrue(printed.contains("\tResult: 14"));
    }

    private int evalRpn(String... tokens) {
        Object solution = newSolution();
        return (int) invoke(solution, "evalRPN", new Class<?>[]{String[].class}, (Object) tokens);
    }

    private int ajayyEvalRpn(String... tokens) {
        Object solution = newSolution();
        return (int) invoke(solution, "ajayyEvalRPN", new Class<?>[]{String[].class}, (Object) tokens);
    }

    private Object newSolution() {
        try {
            Class<?> solutionClass = Class.forName(ReversePoloshNotationStack.class.getName() + "$Solution");
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
