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

class SortedArrayToBstTest {
    @Test
    void defaultConstructorAndDisplayTreeHandleEmptyTree() throws Exception {
        SortedArrayToBst<Integer> solution = new SortedArrayToBst<>();

        assertTrue(captureDisplay(solution, null).contains("null"));
    }

    @Test
    void listConstructorHandlesNullRootNode() throws Exception {
        SortedArrayToBst<Integer> solution = newTree((Integer) null);

        assertNull(getRoot(solution));
    }

    @Test
    void sortedArrayToBSTBuildsBalancedTreeFromOddLengthArray() throws Exception {
        SortedArrayToBst<Integer> solution = new SortedArrayToBst<>();
        Object root = sortedArrayToBST(solution, new int[]{1, 2, 3, 4, 5, 6, 7});

        assertEquals(4, dataField().get(root));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), inorderTraversal(root));
        assertTrue(isHeightBalanced(root));
    }

    @Test
    void sortedArrayToBSTHandlesEmptySingleAndNegativeEvenLengthArrays() throws Exception {
        SortedArrayToBst<Integer> solution = new SortedArrayToBst<>();

        assertNull(sortedArrayToBST(solution, new int[0]));
        assertEquals(List.of(0), inorderTraversal(sortedArrayToBST(solution, new int[]{0})));

        Object root = sortedArrayToBST(solution, new int[]{-5, -2, 0, 3, 6, 8});
        assertEquals(3, dataField().get(root));
        assertEquals(List.of(-5, -2, 0, 3, 6, 8), inorderTraversal(root));
        assertTrue(isHeightBalanced(root));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> SortedArrayToBst.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: [1, 2, 3, 4, 5, 6, 7]"));
        assertTrue(printed.contains("2.\tInput array: [0]"));
        assertTrue(printed.contains("5.\tInput array: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]"));
    }

    private static SortedArrayToBst<Integer> newTree(Integer... values) throws Exception {
        Constructor<SortedArrayToBst> constructor = SortedArrayToBst.class.getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        return constructor.newInstance(nodes);
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = nodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Object sortedArrayToBST(SortedArrayToBst<Integer> solution, int[] values) throws Exception {
        Method method = SortedArrayToBst.class.getDeclaredMethod("sortedArrayToBST", int[].class);
        method.setAccessible(true);
        return method.invoke(solution, new Object[]{values});
    }

    private static Object getRoot(SortedArrayToBst<?> solution) throws Exception {
        Field field = SortedArrayToBst.class.getDeclaredField("root");
        field.setAccessible(true);
        return field.get(solution);
    }

    private static List<Integer> inorderTraversal(Object root) throws Exception {
        List<Integer> values = new ArrayList<>();
        inorder(root, values);
        return values;
    }

    private static void inorder(Object node, List<Integer> values) throws Exception {
        if (node == null) {
            return;
        }
        inorder(leftField().get(node), values);
        values.add((Integer) dataField().get(node));
        inorder(rightField().get(node), values);
    }

    private static boolean isHeightBalanced(Object root) throws Exception {
        return height(root) >= 0;
    }

    private static int height(Object node) throws Exception {
        if (node == null) {
            return 0;
        }
        int leftHeight = height(leftField().get(node));
        int rightHeight = height(rightField().get(node));
        if (leftHeight < 0 || rightHeight < 0 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static String captureDisplay(SortedArrayToBst<?> solution, Object root) throws Exception {
        Method method = SortedArrayToBst.class.getDeclaredMethod("displayTree", nodeClass());
        method.setAccessible(true);
        return captureOutput(() -> {
            try {
                method.invoke(solution, root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.SortedArrayToBst$TreeNode");
    }

    private static Field dataField() throws Exception {
        Field field = nodeClass().getDeclaredField("data");
        field.setAccessible(true);
        return field;
    }

    private static Field leftField() throws Exception {
        Field field = nodeClass().getDeclaredField("left");
        field.setAccessible(true);
        return field;
    }

    private static Field rightField() throws Exception {
        Field field = nodeClass().getDeclaredField("right");
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
