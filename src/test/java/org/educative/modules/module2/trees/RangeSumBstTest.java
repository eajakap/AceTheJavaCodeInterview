package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RangeSumBstTest {
    @Test
    void constructorWithNullRootAndDisplayTreeHandleEmptyTree() throws Exception {
        RangeSumBst<Integer> tree = newTree((Integer) null);

        assertNull(getRoot(tree));
        assertTrue(captureDisplay(tree, null).contains("null"));
    }

    @Test
    void rangeSumBSTReturnsExpectedSumForBalancedTree() throws Exception {
        RangeSumBst<Integer> tree = newTree(10, 5, 15, 3, 7, null, 18);
        Object root = getRoot(tree);

        assertEquals(32, invokeRecursive(tree, root, 7, 15));
        assertEquals(32, invokeIterative(tree, root, 7, 15));
    }

    @Test
    void rangeSumMethodsHandleNegativeValuesAndSkewedSearchSpace() throws Exception {
        RangeSumBst<Integer> tree = newTree(0, -3, 9, -10, null, 5);
        Object root = getRoot(tree);

        assertEquals(-8, invokeRecursive(tree, root, -10, 5));
        assertEquals(14, invokeIterative(tree, root, -3, 9));
    }

    @Test
    void rangeSumMethodsReturnZeroForNullAndOutOfRangeTrees() throws Exception {
        RangeSumBst<Integer> emptyTree = newTree((Integer) null);
        RangeSumBst<Integer> singleNodeTree = newTree(50);

        assertEquals(0, invokeRecursive(emptyTree, null, 1, 10));
        assertEquals(0, invokeIterative(emptyTree, null, 1, 10));
        assertEquals(0, invokeRecursive(singleNodeTree, getRoot(singleNodeTree), 51, 100));
        assertEquals(0, invokeIterative(singleNodeTree, getRoot(singleNodeTree), 51, 100));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureMainOutput(() -> RangeSumBst.main(new String[0]));

        assertTrue(printed.contains("1.\tInput tree:"));
        assertTrue(printed.contains("Low: 12, High: 28"));
        assertTrue(printed.contains("Result: 70"));
        assertTrue(printed.contains("5.\tInput tree:"));
        assertTrue(printed.contains("Result: 240"));
    }

    private static RangeSumBst<Integer> newTree(Integer... values) throws Exception {
        Constructor<RangeSumBst> constructor = RangeSumBst.class.getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        return constructor.newInstance(toNodeList(values));
    }

    private static List<Object> toNodeList(Integer... values) throws Exception {
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        return nodes;
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = nodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Object getRoot(RangeSumBst<?> tree) throws Exception {
        Field rootField = RangeSumBst.class.getDeclaredField("root");
        rootField.setAccessible(true);
        return rootField.get(tree);
    }

    private static int invokeRecursive(RangeSumBst<?> tree, Object root, int low, int high) throws Exception {
        Method method = RangeSumBst.class.getDeclaredMethod("rangeSumBST", nodeClass(), int.class, int.class);
        method.setAccessible(true);
        return (Integer) method.invoke(tree, root, low, high);
    }

    private static int invokeIterative(RangeSumBst<?> tree, Object root, int low, int high) throws Exception {
        Method method = RangeSumBst.class.getDeclaredMethod("rangeSumIterativeBST", nodeClass(), int.class, int.class);
        method.setAccessible(true);
        return (Integer) method.invoke(tree, root, low, high);
    }

    private static String captureDisplay(RangeSumBst<?> tree, Object root) throws Exception {
        Method method = RangeSumBst.class.getDeclaredMethod("displayTree", nodeClass());
        method.setAccessible(true);
        return captureMainOutput(() -> {
            try {
                method.invoke(tree, root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.RangeSumBst$TreeNode");
    }

    private static String captureMainOutput(Runnable action) {
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
