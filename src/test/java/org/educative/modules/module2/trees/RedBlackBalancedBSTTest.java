package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedBlackBalancedBSTTest {
    @Test
    void insertBalancesAllRotationCases() throws Exception {
        int[][] orders = {{10, 20, 30}, {30, 20, 10}, {30, 10, 20}, {10, 30, 20}};
        for (int[] order : orders) {
            RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>();
            for (int value : order) {
                tree.insert(value);
                validate(tree);
            }
            assertEquals(List.of(20, 10, 30), tree.levelOrder());
        }
    }

    @Test
    void searchMinMaxSizeAndTraversalsWork() throws Exception {
        RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>(new Integer[]{20, 10, 30, 5, 15, 25, 35});
        validate(tree);

        assertEquals(7, tree.size());
        assertFalse(tree.isEmpty());
        assertEquals(5, tree.min());
        assertEquals(35, tree.max());
        assertTrue(tree.contains(15));
        assertFalse(tree.contains(99));
        assertEquals(List.of(5, 10, 15, 20, 25, 30, 35), tree.inOrder());
        assertEquals(List.of(20, 10, 5, 15, 30, 25, 35), tree.preOrder());
        assertEquals(List.of(5, 15, 10, 25, 35, 30, 20), tree.postOrder());
        assertEquals(List.of(20, 10, 30, 5, 15, 25, 35), tree.levelOrder());
    }

    @Test
    void deleteHandlesMissingValuesAndEmptiesTheTree() throws Exception {
        RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>(42);
        assertEquals(1, tree.size());
        assertFalse(tree.delete(7));
        assertEquals(1, tree.size());
        assertTrue(tree.delete(42));
        validate(tree);
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        assertEquals(List.of(), tree.inOrder());
        assertEquals(List.of(), tree.levelOrder());
    }

    @Test
    void emptyTreeAndNullValuesAreRejected() {
        RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>();
        assertTrue(tree.isEmpty());
        assertThrows(NoSuchElementException.class, tree::min);
        assertThrows(NoSuchElementException.class, tree::max);
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> tree.contains(null));
        assertThrows(NullPointerException.class, () -> tree.delete(null));
        assertThrows(NullPointerException.class, () -> new RedBlackBalancedBST<Integer>((Integer) null));
        assertThrows(NullPointerException.class, () -> new RedBlackBalancedBST<Integer>((Integer[]) null));
        assertTrue(tree.isEmpty());
    }

    @Test
    void randomInsertsAndDeletesKeepAllInvariants() throws Exception {
        Random random = new Random(42);
        for (int round = 0; round < 50; round++) {
            // Values in [0, 50) with 200 inserts → plenty of duplicates
            RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>();
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                int value = random.nextInt(50);
                values.add(value);
                tree.insert(value);
                validate(tree);
            }

            Collections.shuffle(values, random);
            List<Integer> expected = new ArrayList<>(values);
            Collections.sort(expected);
            for (int value : values) {
                assertTrue(tree.delete(value), "Failed to delete " + value);
                expected.remove((Integer) value);
                validate(tree);
                assertEquals(expected, tree.inOrder());
                assertEquals(expected.size(), tree.size());
            }
            assertTrue(tree.isEmpty());
        }
    }

    @Test
    void mainPrintsItsDemonstrationSections() {
        String output = captureOutput(() -> RedBlackBalancedBST.main(new String[0]));

        assertTrue(output.contains("Test 1:"));
        assertTrue(output.contains("Traversals:"));
        assertTrue(output.contains("delete(99) -> false"));
    }

    private static void validate(RedBlackBalancedBST<?> tree) throws Exception {
        Method method = RedBlackBalancedBST.class.getDeclaredMethod("validate");
        method.setAccessible(true);
        try {
            method.invoke(tree);
        } catch (InvocationTargetException e) {
            throw new AssertionError(e.getCause().getMessage(), e.getCause());
        }
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
