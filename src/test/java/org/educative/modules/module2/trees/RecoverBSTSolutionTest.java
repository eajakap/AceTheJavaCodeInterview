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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RecoverBSTSolutionTest {
    @Test
    void recoverTreeFixesAdjacentSwap() throws Exception {
        Object root = buildTree(3, 1, 4, null, null, 2);

        Object recovered = recover(root);

        assertSame(root, recovered);
        assertEquals(List.of(1, 2, 3, 4), inorderTraversal(root));
        assertEquals(Arrays.asList(2, 1, 4, null, null, 3), toLevelOrder(root));
    }

    @Test
    void recoverTreeFixesNonAdjacentSwap() throws Exception {
        Object root = buildTree(4, 6, 2, 1, 3, 5, 7);

        recover(root);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), inorderTraversal(root));
        assertEquals(List.of(4, 2, 6, 1, 3, 5, 7), toLevelOrder(root));
    }

    @Test
    void recoverTreeHandlesNullAndSingleNodeTrees() throws Exception {
        assertNull(recover(null));

        Object root = buildTree(42);
        recover(root);

        assertEquals(List.of(42), inorderTraversal(root));
    }

    @Test
    void displayTreePrintsNullForEmptyTree() throws Exception {
        RecoverBSTSolution<Integer> solution = new RecoverBSTSolution<>();

        assertTrue(captureDisplay(solution, null).contains("null"));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> RecoverBSTSolution.main(new String[0]));

        assertTrue(printed.contains("1.\tInput tree:"));
        assertTrue(printed.contains("\tRecovered tree:"));
        assertTrue(printed.contains("2.\tInput tree:"));
    }

    private static Object recover(Object root) throws Exception {
        Method method = RecoverBSTSolution.class.getDeclaredMethod("recoverTree", nodeClass());
        method.setAccessible(true);
        return method.invoke(null, root);
    }

    private static Object buildTree(Integer... values) throws Exception {
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        for (int i = 0; i < nodes.size(); i++) {
            Object node = nodes.get(i);
            if (node == null) {
                continue;
            }
            int leftIndex = (2 * i) + 1;
            if (leftIndex < nodes.size()) {
                leftField().set(node, nodes.get(leftIndex));
            }
            int rightIndex = (2 * i) + 2;
            if (rightIndex < nodes.size()) {
                rightField().set(node, nodes.get(rightIndex));
            }
        }
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = nodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
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

    private static List<Integer> toLevelOrder(Object root) throws Exception {
        List<Integer> values = new ArrayList<>();
        Queue<Object> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Object node = queue.poll();
            if (node == null) {
                values.add(null);
                continue;
            }
            values.add((Integer) dataField().get(node));
            queue.add(leftField().get(node));
            queue.add(rightField().get(node));
        }
        int last = values.size() - 1;
        while (last >= 0 && values.get(last) == null) {
            values.remove(last--);
        }
        return values;
    }

    private static String captureDisplay(RecoverBSTSolution<?> solution, Object root) throws Exception {
        Method method = RecoverBSTSolution.class.getDeclaredMethod("displayTree", nodeClass());
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
        return Class.forName("org.educative.modules.module2.trees.RecoverBSTSolution$TreeNode");
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
