package org.educative.modules.module2.trees;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeMaxDepth {

    // Definition of a binary tree node class
    static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static class Solution {

        public int maxDepth(TreeNode<Integer> root) {
            // Base case: empty node has depth 0
            if (root == null) {
                return 0;
            }
            // Recursively find depth of left subtree
            int leftDepth = maxDepth(root.left);
            // Recursively find depth of right subtree
            int rightDepth = maxDepth(root.right);
            // Return 1 (current node) + max of left and right depths
            return 1 + Math.max(leftDepth, rightDepth);
        }

        public TreeNode<Integer> buildTree(Integer[] values) {
            if (values == null || values.length == 0) return null;

            TreeNode<Integer> root = new TreeNode<>(values[0]);
            Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.add(root);
            int i = 1;

            while (!queue.isEmpty() && i < values.length) {
                TreeNode<Integer> node = queue.poll();

                if (i < values.length && values[i] != null) {
                    node.left = new TreeNode<>(values[i]);
                    queue.add(node.left);
                }
                i++;

                if (i < values.length && values[i] != null) {
                    node.right = new TreeNode<>(values[i]);
                    queue.add(node.right);
                }
                i++;
            }
            return root;
        }

        public static void main(String[] args) {
            Solution solution = new Solution();  // instance created to call non-static methods

            Object[][] testCases = {
                    new Object[]{new Integer[]{},                                                      "Empty tree"},
                    new Object[]{new Integer[]{42},                                                    "Single node tree"},
                    new Object[]{new Integer[]{1, 2, 3, 4, 5, 6, 7},                                  "Complete binary tree of depth 3"},
                    new Object[]{new Integer[]{1, 2, null, 3, null, 4, null, 5},                       "Left-skewed tree of depth 5"},
                    new Object[]{new Integer[]{10, -5, 20, null, null, 15, 25, null, null, null, 30},  "Unbalanced tree"},
            };

            int idx = 1;
            for (Object[] tc : testCases) {
                Integer[] values = (Integer[]) tc[0];
                String description = (String) tc[1];
                TreeNode<Integer> root = solution.buildTree(values);
                int result = solution.maxDepth(root);
                System.out.println("Test Case " + idx + " - " + description);
                System.out.println("Maximum Depth: " + result);
                System.out.println();
                idx++;
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
