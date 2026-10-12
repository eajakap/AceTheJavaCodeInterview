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

class LargestRectangeAreaHistogramTest {

    @Test
    void largestRectangleAreaHandlesCommonShapesAndBoundaries() {
        assertEquals(10, largestRectangleArea(2, 1, 5, 6, 2, 3));
        assertEquals(9, largestRectangleArea(1, 2, 3, 4, 5));
        assertEquals(9, largestRectangleArea(5, 4, 3, 2, 1));
        assertEquals(4, largestRectangleArea(1, 1, 1, 1));
        assertEquals(9, largestRectangleArea(0, 9, 0, 9, 0));
        assertEquals(7, largestRectangleArea(7));
        assertEquals(0, largestRectangleArea());
    }

    @Test
    void mainPrintsBuiltInExample() {
        String printed = captureStdOut(() -> LargestRectangeAreaHistogram.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: [3, 6, 5, 7, 4, 8, 1, 0]"));
        assertTrue(printed.contains("\tResult: 20"));
    }

    private int largestRectangleArea(int... heights) {
        Object solution = newSolution();
        return (int) invoke(solution, "largestRectangleArea", new Class<?>[]{int[].class}, (Object) heights);
    }

    private Object newSolution() {
        try {
            Class<?> solutionClass = Class.forName(LargestRectangeAreaHistogram.class.getName() + "$Solution");
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
