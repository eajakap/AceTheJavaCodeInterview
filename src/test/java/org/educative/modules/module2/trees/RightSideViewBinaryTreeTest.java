package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RightSideViewBinaryTreeTest {
    @Test
    void binaryTreeConstructorsHandleEmptyAndProvidedRoot() throws Exception {
        Object defaultTree = binaryTreeClass().getDeclaredConstructor().newInstance();
        Object root = newNode(10);
        Constructor<?> rootConstructor = binaryTreeClass().getDeclaredConstructor(nodeClass());
        Object providedRootTree = rootConstructor.newInstance(root);
        Constructor<?> listConstructor = binaryTreeClass().getDeclaredConstructor(List.class);
        Object nullRootTree = listConstructor.newInstance(List.of(null, newNode(1)));

        assertNull(rootField().get(defaultTree));
        assertSame(root, rootField().get(providedRootTree));
        assertNull(rootField().get(nullRootTree));
    }

    @Test
    void rightSideViewReturnsVisibleNodesForSparseTree() throws Exception {
        Object solution = newSolution();
        Object root = binaryTreeFromValues(1, 2, 3, null, 5, null, 4);

        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) rightSideViewMethod().invoke(solution, root);

        assertEquals(List.of(1, 3, 4), result);
    }

    @Test
    void rightSideViewHandlesLeftSkewedAndNegativeTrees() throws Exception {
        Object solution = newSolution();
        Object root = binaryTreeFromValues(-1, -2, null, -3);

        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) rightSideViewMethod().invoke(solution, root);

        assertEquals(List.of(-1, -2, -3), result);
    }

    @Test
    void displayTreePrintsNullForEmptyTree() throws Exception {
        Object solution = newSolution();

        assertTrue(captureDisplay(solution, null).contains("null"));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> RightSideViewBinaryTree.main(new String[0]));

        assertTrue(printed.contains("1. Binary tree:"));
        assertTrue(printed.contains("Right side view: [1, 3, 5]"));
        assertTrue(printed.contains("5. Binary tree:"));
    }

    private static Object newSolution() throws Exception {
        Constructor<?> constructor = solutionClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object binaryTreeFromValues(Integer... values) throws Exception {
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        Constructor<?> constructor = binaryTreeClass().getDeclaredConstructor(List.class);
        return rootField().get(constructor.newInstance(nodes));
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = nodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Method rightSideViewMethod() throws Exception {
        Method method = solutionClass().getDeclaredMethod("rightSideView", nodeClass());
        method.setAccessible(true);
        return method;
    }

    private static String captureDisplay(Object solution, Object root) throws Exception {
        Method method = solutionClass().getDeclaredMethod("displayTree", nodeClass());
        method.setAccessible(true);
        return captureOutput(() -> {
            try {
                method.invoke(solution, root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Class<?> solutionClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.RightSideViewBinaryTree$Solution");
    }

    private static Class<?> binaryTreeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.RightSideViewBinaryTree$BinaryTree");
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.RightSideViewBinaryTree$TreeNode");
    }

    private static Field rootField() throws Exception {
        Field field = binaryTreeClass().getDeclaredField("root");
        field.setAccessible(true);
        return field;
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
