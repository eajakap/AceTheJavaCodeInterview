package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryTreeMainTest {
    @Test
    void insertTraversalsMetricsAndSearchWork() {
        BinaryTreeMain.BinaryTree empty = new BinaryTreeMain.BinaryTree();

        assertEquals(-1, empty.height());
        assertEquals(0, empty.size());
        assertEquals(-1, empty.depth(1));
        assertEquals("", captureOutput(empty::levelOrder));
        assertEquals("null", captureOutput(() -> BinaryTreeMain.PrintBinaryTree.displayTree(null)));

        BinaryTreeMain.BinaryTree tree = new BinaryTreeMain.BinaryTree();
        for (int value : new int[]{1, 2, 3, 4, 5}) {
            tree.insert(value);
        }

        assertEquals(1, tree.root.data);
        assertEquals(2, tree.root.left.data);
        assertEquals(3, tree.root.right.data);
        assertEquals(4, tree.root.left.left.data);
        assertEquals(5, tree.root.left.right.data);
        assertEquals("1 2 4 5 3", captureOutput(() -> tree.preorder(tree.root)));
        assertEquals("4 2 5 1 3", captureOutput(() -> tree.inorder(tree.root)));
        assertEquals("4 5 2 3 1", captureOutput(() -> tree.postorder(tree.root)));
        assertEquals("1 2 3 4 5", captureOutput(tree::levelOrder));
        assertEquals(2, tree.height(tree.root));
        assertEquals(2, tree.height());
        assertEquals(5, tree.size(tree.root));
        assertEquals(5, tree.size());
        assertEquals(2, tree.depth(5));
        assertEquals(1, tree.depth(tree.root, 3, 0));
        assertEquals(-1, tree.depth(tree.root, 99, 0));
        assertTrue(tree.search(tree.root, 4));
        assertFalse(tree.search(tree.root, 99));
    }

    @Test
    void deleteHandlesLeafInternalRootMissingAndSingleNodeCases() {
        BinaryTreeMain.BinaryTree tree = new BinaryTreeMain.BinaryTree();
        for (int value : new int[]{1, 2, 3, 4, 5}) {
            tree.insert(value);
        }

        tree.delete(5);
        assertEquals("1 2 3 4", captureOutput(tree::levelOrder));

        tree.delete(2);
        assertEquals("1 4 3", captureOutput(tree::levelOrder));

        tree.delete(1);
        assertEquals("3 4", captureOutput(tree::levelOrder));

        tree.delete(999);
        assertEquals("3 4", captureOutput(tree::levelOrder));

        BinaryTreeMain.BinaryTree single = new BinaryTreeMain.BinaryTree(new BinaryTreeMain.TreeNode(42));
        single.delete(42);
        assertNull(single.root);

        BinaryTreeMain.BinaryTree notDeleted = new BinaryTreeMain.BinaryTree(new BinaryTreeMain.TreeNode(42));
        notDeleted.delete(99);
        assertEquals(42, notDeleted.root.data);
    }

    @Test
    void mainPrintsItsDemonstrationSections() {
        String output = captureOutput(() -> BinaryTreeMain.main(new String[0]));

        assertTrue(output.contains("Level Order Traversal:"));
        assertTrue(output.contains("Search for 99: false"));
        assertTrue(output.contains("Height of tree: 2"));
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
