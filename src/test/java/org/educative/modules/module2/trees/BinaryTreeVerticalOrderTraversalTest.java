package org.educative.modules.module2.trees;

import org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal.BinaryTree;
import org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal.PrintBinaryTree;
import org.educative.modules.module2.trees.BinaryTreeVerticalOrderTraversal.TreeNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryTreeVerticalOrderTraversalTest {
    @Test
    void binaryTreeConstructorBuildsExpectedShapeAndTraversal() {
        TreeNode<Integer> root = rootFromValues(3, 9, 20, null, null, 15, 7);

        assertEquals(List.of(List.of(9), List.of(3, 15), List.of(20), List.of(7)),
                BinaryTreeVerticalOrderTraversal.verticalTraversal(root));
    }

    @Test
    void verticalTraversalBreaksTiesByRowThenValueAndHandlesNullRoot() {
        TreeNode<Integer> root = rootFromValues(1, 2, 3, null, 5, 4, null);

        assertEquals(List.of(List.of(2), List.of(1, 4, 5), List.of(3)),
                BinaryTreeVerticalOrderTraversal.verticalTraversal(root));
        assertEquals(List.of(), BinaryTreeVerticalOrderTraversal.verticalTraversal(null));
    }

    @Test
    void constructorDisplayAndMainHandleEmptyCasesAndPrintExamples() {
        assertNull(new BinaryTree<Integer>(new ArrayList<>()).root);
        List<TreeNode<Integer>> firstNull = new ArrayList<>();
        firstNull.add(null);
        assertNull(new BinaryTree<>(firstNull).root);

        String display = captureOutput(() -> new PrintBinaryTree().displayTree(null));
        assertEquals("null", display);

        String output = captureOutput(() -> BinaryTreeVerticalOrderTraversal.main(new String[0]));
        assertTrue(output.contains("Description: Single node tree"));
        assertTrue(output.contains("Result: [[1]]"));
    }

    private static TreeNode<Integer> rootFromValues(Integer... values) {
        List<TreeNode<Integer>> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : new TreeNode<>(value));
        }
        return new BinaryTree<>(nodes).root;
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
