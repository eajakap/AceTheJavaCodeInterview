package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.educative.modules.module2.trees.BinarySearchTreeMain.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinarySearchTreeMainTest {
    @Test
    void insertOperationsSupportDuplicatesAndSearches() {
        BinarySearchTreeMain.BinarySearchTree tree = new BinarySearchTreeMain.BinarySearchTree();

        tree.insertIterative(10);
        tree.insertIterative(5);
        tree.insertIterative(15);
        tree.insertIterative(10);
        tree.insert(3);
        tree.insert(7);

        assertEquals(10, tree.root.data);
        assertEquals(5, tree.root.left.data);
        assertEquals(15, tree.root.right.data);
        assertEquals(10, tree.root.right.left.data);
        assertEquals(3, tree.root.left.left.data);
        assertEquals(7, tree.root.left.right.data);
        assertTrue(tree.searchRecursive(tree.root, 7));
        assertTrue(tree.searchIterative(10));
        assertFalse(tree.searchRecursive(tree.root, 999));
        assertFalse(tree.searchIterative(-1));
        assertEquals(3, tree.minValueNode(tree.root).data);
        assertNull(tree.minValueNode(null));
    }

    @Test
    void traversalsMetricsAndDisplayWorkForEmptyAndPopulatedTrees() {
        BinarySearchTreeMain.BinarySearchTree empty = new BinarySearchTreeMain.BinarySearchTree();

        assertEquals(-1, empty.height());
        assertEquals(0, empty.size());
        assertEquals(-1, empty.depth(5));
        assertEquals("", captureOutput(empty::levelOrder));
        assertEquals("null", captureOutput(() -> BinarySearchTreeMain.PrintBinarySearchTree.displayTree(null)));

        Node root = new BinarySearchTreeMain.Node(10);
        root.left = new BinarySearchTreeMain.Node(5);
        root.right = new BinarySearchTreeMain.Node(15);
        root.left.left = new BinarySearchTreeMain.Node(3);
        root.left.right = new BinarySearchTreeMain.Node(7);

        BinarySearchTreeMain.BinarySearchTree tree = new BinarySearchTreeMain.BinarySearchTree(root);
        assertEquals("10 5 3 7 15", captureOutput(() -> tree.preorder(tree.root)));
        assertEquals("3 5 7 10 15", captureOutput(() -> tree.inorder(tree.root)));
        assertEquals("3 7 5 15 10", captureOutput(() -> tree.postorder(tree.root)));
        assertEquals("10 5 15 3 7", captureOutput(tree::levelOrder));
        assertEquals(2, tree.height(tree.root));
        assertEquals(2, tree.height());
        assertEquals(5, tree.size(tree.root));
        assertEquals(5, tree.size());
        assertEquals(2, tree.depth(7));
        assertEquals(1, tree.depth(tree.root, 15, 0));
        assertEquals(-1, tree.depth(tree.root, 999, 0));
    }

    @Test
    void deleteHandlesLeafOneChildTwoChildrenAndMissingValues() {
        BinarySearchTreeMain.BinarySearchTree tree = new BinarySearchTreeMain.BinarySearchTree();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80, 65}) {
            tree.insert(value);
        }

        tree.delete(20);
        tree.delete(60);
        tree.delete(50);
        tree.delete(999);

        assertEquals("30 40 65 70 80", captureOutput(() -> tree.inorder(tree.root)));
        assertFalse(tree.searchIterative(20));
        assertFalse(tree.searchIterative(60));
        assertFalse(tree.searchIterative(50));
        assertTrue(tree.searchIterative(65));
        assertEquals(65, tree.root.data);
    }

    @Test
    void mainPrintsItsDemonstrationSections() {
        String output = captureOutput(() -> BinarySearchTreeMain.main(new String[0]));

        assertTrue(output.contains("Inorder Traversal:"));
        assertTrue(output.contains("Recursive search for 60: true"));
        assertTrue(output.contains("After deleting 50:"));
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
