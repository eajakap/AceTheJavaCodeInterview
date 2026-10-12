package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ReconstructBinaryTreeSolutionTest {
    @Test
    void buildTreeReconstructsBalancedTree() throws Exception {
        Object solution = newSolution();
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        Object root = buildTree(solution, preorder, inorder);

        assertArrayEquals(preorder, preorderTraversal(root).stream().mapToInt(Integer::intValue).toArray());
        assertArrayEquals(inorder, inorderTraversal(root).stream().mapToInt(Integer::intValue).toArray());
        assertEquals(List.of(3, 9, 20, null, null, 15, 7), toLevelOrder(root));
    }

    @Test
    void buildTreeHandlesEmptyInputAndDisplayTreePrintsNull() throws Exception {
        Object solution = newSolution();

        Object root = buildTree(solution, new int[0], new int[0]);

        assertNull(root);
        assertTrue(captureDisplay(solution, null).contains("null"));
    }

    @Test
    void buildTreeSupportsRightSkewedTreesAndNegativeValues() throws Exception {
        Object solution = newSolution();
        int[] preorder = {-10, -3, 0, 5};
        int[] inorder = {-10, -3, 0, 5};

        Object root = buildTree(solution, preorder, inorder);

        assertEquals(List.of(-10, null, -3, null, 0, null, 5), toLevelOrder(root));
    }

    @Test
    void buildTreeRejectsMismatchedTraversals() throws Exception {
        Object solution = newSolution();

        InvocationTargetException thrown = assertThrows(
                InvocationTargetException.class,
                () -> buildTree(solution, new int[]{1, 2, 3}, new int[]{3, 1, 4})
        );

        assertInstanceOf(IllegalArgumentException.class, thrown.getCause());
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> ReconstructBinaryTreeSolution.main(new String[0]));

        assertTrue(printed.contains("1.\tInput array: preorder = [1, 2, 3]"));
        assertTrue(printed.contains("Binary tree:"));
        assertTrue(printed.contains("5.\tInput array: preorder = [-10, -20, -30, 0, 5, 10]"));
    }

    private static Object newSolution() throws Exception {
        Constructor<?> constructor = solutionClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object buildTree(Object solution, int[] preorder, int[] inorder) throws Exception {
        Method method = solutionClass().getDeclaredMethod("buildTree", int[].class, int[].class);
        method.setAccessible(true);
        return method.invoke(solution, preorder, inorder);
    }

    private static List<Integer> preorderTraversal(Object root) throws Exception {
        List<Integer> values = new ArrayList<>();
        preorder(root, values);
        return values;
    }

    private static void preorder(Object node, List<Integer> values) throws Exception {
        if (node == null) {
            return;
        }
        values.add((Integer) dataField().get(node));
        preorder(leftField().get(node), values);
        preorder(rightField().get(node), values);
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
        return Class.forName("org.educative.modules.module2.trees.ReconstructBinaryTreeSolution$Solution");
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.ReconstructBinaryTreeSolution$TreeNode");
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
