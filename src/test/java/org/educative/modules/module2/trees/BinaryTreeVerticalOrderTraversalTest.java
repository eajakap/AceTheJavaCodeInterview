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

class BinaryTreeVerticalOrderTraversalTest {
    @Test
    void binaryTreeConstructorBuildsExpectedShapeAndTraversal() throws Exception {
        Object root = rootFromValues(3, 9, 20, null, null, 15, 7);

        assertEquals(List.of(List.of(9), List.of(3, 15), List.of(20), List.of(7)), verticalTraversal(root));
    }

    @Test
    void verticalTraversalBreaksTiesByRowThenValueAndHandlesNullRoot() throws Exception {
        Object root = rootFromValues(1, 2, 3, null, 5, 4, null);

        assertEquals(List.of(List.of(2), List.of(1, 4, 5), List.of(3)), verticalTraversal(root));
        assertEquals(List.of(), verticalTraversal(null));
    }

    @Test
    void constructorDisplayAndMainHandleEmptyCasesAndPrintExamples() throws Exception {
        assertNull(rootFromList(new ArrayList<>()));
        List<Object> firstNull = new ArrayList<>();
        firstNull.add(null);
        assertNull(rootFromList(firstNull));

        Constructor<?> constructor = solutionClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        Object solution = constructor.newInstance();
        String display = captureOutput(() -> invokeDisplayTree(solution, null));
        assertEquals("null", display);

        String output = captureOutput(() -> BinaryTreeVerticalOrderTraversal.main(new String[0]));
        assertTrue(output.contains("Description: Single node tree"));
        assertTrue(output.contains("Result: [[1]]"));
    }

    private static Object rootFromValues(Integer... values) throws Exception {
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        return rootFromList(nodes);
    }

    private static Object rootFromList(List<Object> nodes) throws Exception {
        Constructor<?> constructor = binaryTreeClass().getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        Object tree = constructor.newInstance(nodes);
        Field rootField = binaryTreeClass().getDeclaredField("root");
        rootField.setAccessible(true);
        return rootField.get(tree);
    }

    @SuppressWarnings("unchecked")
    private static List<List<Integer>> verticalTraversal(Object root) throws Exception {
        Method method = solutionClass().getDeclaredMethod("verticalTraversal", treeNodeClass());
        method.setAccessible(true);
        return (List<List<Integer>>) method.invoke(null, root);
    }

    private static void invokeDisplayTree(Object solution, Object root) {
        try {
            Method method = solutionClass().getDeclaredMethod("displayTree", treeNodeClass());
            method.setAccessible(true);
            method.invoke(solution, root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = treeNodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Class<?> treeNodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal$TreeNode");
    }

    private static Class<?> binaryTreeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal$BinaryTree");
    }

    private static Class<?> solutionClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal$Solution");
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
