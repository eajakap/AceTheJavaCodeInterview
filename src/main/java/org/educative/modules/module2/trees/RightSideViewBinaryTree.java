package org.educative.modules.module2.trees;

import java.util.*;

public class RightSideViewBinaryTree {
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

    static class BinaryTree<T> {
        TreeNode<T> root;

        public BinaryTree() {
            this.root = null;
        }

        public BinaryTree(TreeNode<T> root) {
            this.root = root;
        }

        public BinaryTree(List<TreeNode<T>> nodes) {
            if (nodes == null || nodes.isEmpty() || nodes.get(0) == null) {
                this.root = null;
                return;
            }

            this.root = nodes.get(0);
            for (int i = 0; i < nodes.size(); i++) {
                TreeNode<T> current = nodes.get(i);
                if (current == null) {
                    continue;
                }

                // Left Node
                int leftIndex = (2 * i) + 1;
                if (leftIndex < nodes.size()) {
                    current.left = nodes.get(leftIndex);
                }
                // Right Node
                int rightIndex = (2 * i) + 2;
                if (rightIndex < nodes.size()) {
                    current.right = nodes.get(rightIndex);
                }
            }
        }
    }

    private static class Solution {

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
            if (level > 0) p = Math.max(p, output.get(level - 1).length());
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

        // --- Display tree methods ---
        private int treeHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
        }

        public void displayTree(TreeNode root) {
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

        public List<Integer> rightSideView(TreeNode<Integer> root) {
            if (root == null) return new ArrayList<>();

            List<Integer> result = new ArrayList<>();
            Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode<Integer> node = queue.poll();

                    // The last node in each level is the rightmost visible node
                    if (i == levelSize - 1) {
                        result.add(node.data);
                    }

                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
            }
            return result;
        }

        // Driver code
        public static void main(String[] args) {
            Solution sol = new Solution();

            List<List<TreeNode<Integer>>> input = Arrays.asList(
                    Arrays.asList(new TreeNode<Integer>(1), new TreeNode<Integer>(2), new TreeNode<Integer>(3), null, null, new TreeNode<Integer>(4), new TreeNode<Integer>(5)),
                    Arrays.asList(new TreeNode<Integer>(1), new TreeNode<Integer>(2), null, new TreeNode<Integer>(3), null, new TreeNode<Integer>(4)),
                    Arrays.asList(new TreeNode<Integer>(1), new TreeNode<Integer>(2), new TreeNode<Integer>(3), new TreeNode<Integer>(4), new TreeNode<Integer>(5), new TreeNode<Integer>(6), new TreeNode<Integer>(7), new TreeNode<Integer>(8)),
                    Arrays.asList(new TreeNode<Integer>(1), new TreeNode<Integer>(2), new TreeNode<Integer>(3), new TreeNode<Integer>(4), new TreeNode<Integer>(5), new TreeNode<Integer>(6), null, new TreeNode<Integer>(8)),
                    Arrays.asList(new TreeNode<Integer>(1), new TreeNode<Integer>(2), null, new TreeNode<Integer>(3), new TreeNode<>(4))
            );

            List<BinaryTree<Integer>> inputTrees = new ArrayList<>();
            for (List<TreeNode<Integer>> listOfNodes : input) {
                BinaryTree<Integer> tree = new BinaryTree<Integer>(listOfNodes);
                inputTrees.add(tree);
            }

            int y = 1;
            for (BinaryTree<Integer> tree : inputTrees) {
                System.out.println(y++ + ". Binary tree:");
                sol.displayTree(tree.root);
                System.out.print("\n   Right side view: [");

                List<Integer> result = sol.rightSideView(tree.root);

                for (int i = 0; i < result.size(); i++) {
                    System.out.print(result.get(i));
                    if (i != result.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
