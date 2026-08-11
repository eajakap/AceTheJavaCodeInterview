package org.educative.modules.module2.trees;

import java.util.*;

public class ReconstructBinaryTreeSolution {

    // Definition of a binary tree node class
    private static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private static class Solution {

        private int[] preorderIdx = {0};

        public TreeNode<Integer> buildTree(int[] preorder, int[] inorder) {
            // Build a hashmap for O(1) lookup of root index in inorder array
            Map<Integer, Integer> inorderIndexMap = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderIndexMap.put(inorder[i], i);
            }

            preorderIdx[0] = 0;

            return helper(preorder, inorderIndexMap, 0, inorder.length - 1);
        }

        private TreeNode<Integer> helper(int[] preorder, Map<Integer, Integer> inorderIndexMap, int inLeft, int inRight) {
            // Base case: no elements to construct the subtree
            if (inLeft > inRight) {
                return null;
            }

            // Pick the current root from preorder traversal
            int rootVal = preorder[preorderIdx[0]];
            TreeNode<Integer> root = new TreeNode<>(rootVal);

            // Advance the preorder index
            preorderIdx[0] += 1;

            // Find the index of root in inorder to split left and right subtrees
            int inorderRootIdx = inorderIndexMap.get(rootVal);

            // Recursively build left subtree (elements left of root in inorder)
            root.left = helper(preorder, inorderIndexMap, inLeft, inorderRootIdx - 1);

            // Recursively build right subtree (elements right of root in inorder)
            root.right = helper(preorder, inorderIndexMap, inorderRootIdx + 1, inRight);

            return root;
        }

        // --- Display tree methods ---
        private int treeHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
        }

        private void drawNode(List<StringBuilder> output, List<StringBuilder> linkAbove,
                              TreeNode node, int level, int p, char linkChar) {
            if (node == null) return;
            String SP = " ";
            int h = output.size();

            if (p < 0) {
                for (StringBuilder s : output)
                    if (s.length() > 0)
                        s.insert(0, " ".repeat(-p));
                for (StringBuilder s : linkAbove)
                    if (s.length() > 0)
                        s.insert(0, " ".repeat(-p));
            }

            if (level < h - 1) p = Math.max(p, output.get(level + 1).length());
            if (level > 0)     p = Math.max(p, output.get(level - 1).length());
            p = Math.max(p, output.get(level).length());

            if (node.left != null) {
                String leftData = SP + node.left.data + SP;
                drawNode(output, linkAbove, node.left, level + 1, p - leftData.length(), 'L');
                p = Math.max(p, output.get(level + 1).length());
            }

            int space = p - output.get(level).length();
            if (space > 0) output.get(level).append(" ".repeat(space));
            output.get(level).append(SP + node.data + SP);

            space = p + SP.length() - linkAbove.get(level).length();
            if (space > 0) linkAbove.get(level).append(" ".repeat(space));
            linkAbove.get(level).append(linkChar);

            if (node.right != null)
                drawNode(output, linkAbove, node.right, level + 1, output.get(level).length(), 'R');
        }

        public void displayTree(TreeNode<Integer> root) {
            if (root == null) {
                System.out.println("\tnull");
                return;
            }
            int h = treeHeight(root);
            List<StringBuilder> output = new ArrayList<>();
            List<StringBuilder> linkAbove = new ArrayList<>();
            for (int i = 0; i < h; i++) {
                output.add(new StringBuilder());
                linkAbove.add(new StringBuilder());
            }
            drawNode(output, linkAbove, root, 0, 5, ' ');

            for (int i = 1; i < h; i++) {
                for (int j = 0; j < linkAbove.get(i).length(); j++) {
                    if (linkAbove.get(i).charAt(j) != ' ') {
                        int size = output.get(i - 1).length();
                        if (size < j + 1)
                            output.get(i - 1).append(" ".repeat(j + 1 - size));
                        int jj = j;
                        if (linkAbove.get(i).charAt(j) == 'L') {
                            while (jj < output.get(i - 1).length() && output.get(i - 1).charAt(jj) == ' ')
                                jj++;
                            for (int k = j + 1; k < jj - 1 && k < output.get(i - 1).length(); k++)
                                output.get(i - 1).setCharAt(k, '_');
                        } else if (linkAbove.get(i).charAt(j) == 'R') {
                            while (jj >= 0 && output.get(i - 1).charAt(jj) == ' ')
                                jj--;
                            for (int k = j - 1; k > jj && k >= 0; k--)
                                output.get(i - 1).setCharAt(k, '_');
                        }
                        linkAbove.get(i).setCharAt(j, '|');
                    }
                }
            }

            for (int i = 0; i < h; i++) {
                if (i > 0) System.out.println("\t" + linkAbove.get(i));
                System.out.println("\t" + output.get(i));
            }
        }

        public static void main(String[] args) {
            Solution solution = new Solution();

            int[][][] testCases = {
                    {{1, 2, 3},                 {2, 1, 3}},
                    {{1, 2, 4, 5, 3, 6, 7},    {4, 2, 5, 1, 6, 3, 7}},
                    {{5, 3, 2, 4, 8, 7, 9},    {2, 3, 4, 5, 7, 8, 9}},
                    {{10, 5, 1, 7, 40, 50},    {1, 5, 7, 10, 40, 50}},
                    {{-10, -20, -30, 0, 5, 10},{-30, -20, -10, 0, 5, 10}},
            };

            int i = 1;
            for (int[][] tc : testCases) {
                int[] preorder = tc[0];
                int[] inorder  = tc[1];

                TreeNode<Integer> tree = solution.buildTree(preorder, inorder);

                System.out.println(i + ".\tInput array: preorder = " + Arrays.toString(preorder));
                System.out.println("\tInorder: " + Arrays.toString(inorder));
                System.out.println("\n\tBinary tree:");
                solution.displayTree(tree);
                System.out.println("-".repeat(100));
                i++;
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
