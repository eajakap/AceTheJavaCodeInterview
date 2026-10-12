package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryTreeMain2Test {
    @Test
    void constructorsInsertSearchMetricsAndDisplayWork() {
        BinaryTreeMain2.BinaryTree emptyFromNullRoot = new BinaryTreeMain2.BinaryTree((String) null);
        assertNull(emptyFromNullRoot.root);
        assertEquals(-1, emptyFromNullRoot.height());
        assertEquals(0, emptyFromNullRoot.size());
        assertEquals(-1, emptyFromNullRoot.depth("X"));
        assertEquals("null", captureOutput(() -> emptyFromNullRoot.displayTree(null)));

        BinaryTreeMain2.BinaryTree tree = new BinaryTreeMain2.BinaryTree("A");
        tree.insert("B");
        tree.insert("C");
        tree.insert("D");
        tree.insert("E");

        assertEquals("A", tree.root.data);
        assertEquals("B", tree.root.left.data);
        assertEquals("C", tree.root.right.data);
        assertEquals("D", tree.root.left.left.data);
        assertEquals("E", tree.root.left.right.data);
        assertTrue(tree.search(tree.root, "E"));
        assertFalse(tree.search(tree.root, "Z"));
        assertEquals(2, tree.height(tree.root));
        assertEquals(2, tree.height());
        assertEquals(5, tree.size(tree.root));
        assertEquals(5, tree.size());
        assertEquals(2, tree.depth("E"));
        assertEquals(1, tree.depth(tree.root, "C", 0));
        assertEquals(-1, tree.depth(tree.root, "Z", 0));
    }

    @Test
    void deleteHandlesLeafRootMissingAndSingleNodeCases() {
        BinaryTreeMain2.BinaryTree tree = new BinaryTreeMain2.BinaryTree("A");
        tree.insert("B");
        tree.insert("C");
        tree.insert("D");
        tree.insert("E");

        tree.delete("E");
        assertEquals("A B C D", captureOutput(() -> levelOrder(tree.root)));

        tree.delete("B");
        assertEquals("A D C", captureOutput(() -> levelOrder(tree.root)));

        tree.delete("A");
        assertEquals("C D", captureOutput(() -> levelOrder(tree.root)));

        tree.delete("missing");
        assertEquals("C D", captureOutput(() -> levelOrder(tree.root)));

        BinaryTreeMain2.BinaryTree single = new BinaryTreeMain2.BinaryTree("only");
        single.delete("only");
        assertNull(single.root);
    }

    @Test
    void mainPrintsItsDemonstrationSections() {
        String output = captureOutput(() -> BinaryTreeMain2.main(new String[0]));

        assertTrue(output.contains("Search for C: true"));
        assertTrue(output.contains("Depth of X: -1"));
        assertTrue(output.contains("After deleting A:"));
    }

    private static void levelOrder(BinaryTreeMain2.TreeNode root) {
        if (root == null) {
            return;
        }
        java.util.Queue<BinaryTreeMain2.TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BinaryTreeMain2.TreeNode current = queue.poll();
            System.out.print(current.data + " ");
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
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
