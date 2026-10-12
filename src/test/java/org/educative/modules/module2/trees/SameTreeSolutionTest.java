package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SameTreeSolutionTest {
    @Test
    void buildTreeReturnsNullForNullEmptyAndNullRootInputs() throws Exception {
        Object solution = newSolution();

        assertNull(buildTree(solution, null));
        assertNull(buildTree(solution, new Integer[0]));
        assertNull(buildTree(solution, new Integer[]{null, 1, 2}));
    }

    @Test
    void isSameTreeReturnsTrueForIdenticalTrees() throws Exception {
        Object solution = newSolution();
        Object left = buildTree(solution, new Integer[]{10, -5, 20, null, 8});
        Object right = buildTree(solution, new Integer[]{10, -5, 20, null, 8});

        assertTrue(isSameTree(left, right));
    }

    @Test
    void isSameTreeReturnsFalseForDifferentValues() throws Exception {
        Object solution = newSolution();
        Object left = buildTree(solution, new Integer[]{1, 2, 3});
        Object right = buildTree(solution, new Integer[]{1, 2, 4});

        assertFalse(isSameTree(left, right));
    }

    @Test
    void isSameTreeReturnsFalseForDifferentStructure() throws Exception {
        Object solution = newSolution();
        Object left = buildTree(solution, new Integer[]{1, 2, null, 3});
        Object right = buildTree(solution, new Integer[]{1, null, 2, null, 3});

        assertFalse(isSameTree(left, right));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> SameTreeSolution.main(new String[0]));

        assertTrue(printed.contains("1.\tInput tree p: [1]"));
        assertTrue(printed.contains("\tResult: true"));
        assertTrue(printed.contains("4.\tInput tree p: [1,2,3]"));
        assertTrue(printed.contains("\tResult: false"));
    }

    private static Object newSolution() throws Exception {
        Constructor<?> constructor = solutionClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object buildTree(Object solution, Integer[] values) throws Exception {
        Method method = solutionClass().getDeclaredMethod("buildTree", Integer[].class);
        method.setAccessible(true);
        return method.invoke(solution, new Object[]{values});
    }

    private static boolean isSameTree(Object left, Object right) throws Exception {
        Method method = solutionClass().getDeclaredMethod("isSameTree", nodeClass(), nodeClass());
        method.setAccessible(true);
        return (Boolean) method.invoke(null, left, right);
    }

    private static Class<?> solutionClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.SameTreeSolution$Solution");
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.SameTreeSolution$TreeNode");
    }

    private static String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }
}
