package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryTreeMaxDepthTest {
    @Test
    void maxDepthHandlesNullSingleNodeAndUnbalancedTrees() {
        BinaryTreeMaxDepth.Solution solution = new BinaryTreeMaxDepth.Solution();

        assertEquals(0, solution.maxDepth(null));

        BinaryTreeMaxDepth.TreeNode<Integer> single = new BinaryTreeMaxDepth.TreeNode<>(42);
        assertEquals(1, solution.maxDepth(single));

        BinaryTreeMaxDepth.TreeNode<Integer> root = solution.buildTree(new Integer[]{1, 2, 3, 4, null, null, 5});
        assertEquals(3, solution.maxDepth(root));
    }

    @Test
    void buildTreeHandlesNullEmptyNullRootAndSparseInput() {
        BinaryTreeMaxDepth.Solution solution = new BinaryTreeMaxDepth.Solution();

        assertNull(solution.buildTree(null));
        assertNull(solution.buildTree(new Integer[]{}));
        assertNull(solution.buildTree(new Integer[]{null}));

        BinaryTreeMaxDepth.TreeNode<Integer> root = solution.buildTree(new Integer[]{1, 2, 3, null, 4});
        assertNotNull(root);
        assertEquals(1, root.data);
        assertEquals(2, root.left.data);
        assertEquals(3, root.right.data);
        assertNull(root.left.left);
        assertEquals(4, root.left.right.data);
    }

    @Test
    void mainPrintsKnownDepths() {
        String output = captureOutput(() -> BinaryTreeMaxDepth.main(new String[0]));

        assertTrue(output.contains("Test Case 1 - Empty tree"));
        assertTrue(output.contains("Maximum Depth: 0"));
        assertTrue(output.contains("Maximum Depth: 3"));
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
