package org.educative.modules.module2.stacks.puzzles;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BracketValidatorStackTest {

    @Test
    void isValidRecognizesBalancedAndUnbalancedInputs() {
        assertTrue(isValid("{[()]}"));
        assertTrue(isValid(""));
        assertTrue(isValid("{()}[]"));
        assertFalse(isValid("((("));
        assertFalse(isValid("])"));
        assertFalse(isValid("{[}]"));
    }

    @Test
    void minRemovalsToBalanceCountsUnmatchedParentheses() {
        assertEquals(0, minRemovalsToBalance("(())"));
        assertEquals(3, minRemovalsToBalance("()))(()"));
        assertEquals(4, minRemovalsToBalance("))))"));
        assertEquals(2, minRemovalsToBalance("(("));
    }

    @Test
    void isParenBalancedHandlesNestedMixedAndTextCharacters() {
        assertTrue(isParenBalanced("a(b[c]{d}e)f"));
        assertTrue(isParenBalanced("{[()]}"));
        assertFalse(isParenBalanced("({[)}"));
        assertFalse(isParenBalanced("(()"));
    }

    @Test
    void mainPrintsExamplesAndUtilityResults() {
        String printed = captureStdOut(() -> BracketValidatorStack.main(new String[0]));

        assertTrue(printed.contains("1.\tInput string: \"{[()]}\""));
        assertTrue(printed.contains("\n\tResult: true"));
        assertTrue(printed.contains("5.\tInput string: \"{[}]\""));
        assertTrue(printed.trim().endsWith("3"));
    }

    private boolean isValid(String input) {
        Object solution = newSolution();
        return (boolean) invoke(solution, "isValid", new Class<?>[]{String.class}, input);
    }

    private int minRemovalsToBalance(String input) {
        return (int) invokeStatic("minRemovalsToBalance", new Class<?>[]{String.class}, input);
    }

    private boolean isParenBalanced(String input) {
        return (boolean) invokeStatic("isParenBalanced", new Class<?>[]{String.class}, input);
    }

    private Object newSolution() {
        try {
            Class<?> solutionClass = Class.forName(BracketValidatorStack.class.getName() + "$Solution");
            Constructor<?> constructor = solutionClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeStatic(String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Class<?> solutionClass = Class.forName(BracketValidatorStack.class.getName() + "$Solution");
            Method method = solutionClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(null, args);
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
