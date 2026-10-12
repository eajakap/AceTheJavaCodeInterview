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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KthSmallestBSTSolutionTest {
    @Test
    void kthSmallestFindsExpectedOrderStatistics() throws Exception {
        KthSmallestBSTSolution<Integer> solution = new KthSmallestBSTSolution<>();
        Object root = rootFromValues(5, 3, 7, 2, 4, 6, 8);

        assertEquals(4, kthSmallest(solution, root, 3));
        assertEquals(8, kthSmallest(solution, root, 7));
    }

    @Test
    void kthSmallestHandlesNullInputAndDisplayTree() throws Exception {
        KthSmallestBSTSolution<Integer> solution = new KthSmallestBSTSolution<>();

        assertEquals(-1, kthSmallest(solution, null, 1));
        assertEquals("null", captureOutput(() -> displayTree(solution, null)));
    }

    @Test
    void binaryTreeConstructorRespectsGapsAndMainPrintsExamples() throws Exception {
        KthSmallestBSTSolution<Integer> solution = new KthSmallestBSTSolution<>();
        assertNull(rootFromList(new ArrayList<>()));

        Object root = rootFromValues(4, 2, 6, 1, 3, 5, 7);
        assertEquals(5, kthSmallest(solution, root, 5));

        Object sparseRoot = rootFromValues(10, 5, 15, 3, 8, null, 20, 1);
        assertEquals(8, kthSmallest(solution, sparseRoot, 4));

        String output = captureOutput(() -> KthSmallestBSTSolution.main(new String[0]));
        assertTrue(output.contains("Target: 2"));
        assertTrue(output.contains("Result: 7"));
    }

    private static int kthSmallest(KthSmallestBSTSolution<Integer> solution, Object root, int k) throws Exception {
        Method method = KthSmallestBSTSolution.class.getDeclaredMethod("kthSmallest", treeNodeClass(), int.class);
        method.setAccessible(true);
        return (int) method.invoke(solution, root, k);
    }

    private static void displayTree(KthSmallestBSTSolution<Integer> solution, Object root) {
        try {
            Method method = KthSmallestBSTSolution.class.getDeclaredMethod("displayTree", treeNodeClass());
            method.setAccessible(true);
            method.invoke(solution, root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object rootFromValues(Integer... values) throws Exception {
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        return rootFromList(nodes);
    }

    private static Object rootFromList(List<Object> nodes) throws Exception {
        Constructor<?> constructor = Class.forName("org.educative.modules.module2.trees.KthSmallestBSTSolution$BinaryTree")
                .getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        Object tree = constructor.newInstance(nodes);
        Field rootField = tree.getClass().getDeclaredField("root");
        rootField.setAccessible(true);
        return rootField.get(tree);
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = treeNodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Class<?> treeNodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.KthSmallestBSTSolution$TreeNode");
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
}
