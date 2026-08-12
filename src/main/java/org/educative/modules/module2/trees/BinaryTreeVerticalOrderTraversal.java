package org.educative.modules.module2.trees;

import java.util.*;

public class BinaryTreeVerticalOrderTraversal {
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

        static class NodeInfo {
            TreeNode<Integer> node;
            int row, col;

            NodeInfo(TreeNode<Integer> node, int row, int col) {
                this.node = node;
                this.row = row;
                this.col = col;
            }
        }

        public static List<List<Integer>> verticalTraversal(TreeNode<Integer> root) {
            // HashMap to store nodes grouped by column: col -> list of {row, val}
            Map<Integer, List<int[]>> colMap = new HashMap<>();

            // BFS queue storing NodeInfo(node, row, col)
            Queue<NodeInfo> queue = new LinkedList<>();
            queue.add(new NodeInfo(root, 0, 0));

            while (!queue.isEmpty()) {
                NodeInfo curr = queue.poll();
                TreeNode<Integer> node = curr.node;
                int row = curr.row;
                int col = curr.col;

                // Append {row, val} array to the column's list
                colMap.computeIfAbsent(col, k -> new ArrayList<>()).add(new int[]{row, node.data});

                // Enqueue left child at (row+1, col-1)
                if (node.left != null) {
                    queue.add(new NodeInfo(node.left, row + 1, col - 1));
                }
                // Enqueue right child at (row+1, col+1)
                if (node.right != null) {
                    queue.add(new NodeInfo(node.right, row + 1, col + 1));
                }
            }

            List<List<Integer>> result = new ArrayList<>();
            // Iterate columns from leftmost to rightmost
            List<Integer> cols = new ArrayList<>(colMap.keySet());
            Collections.sort(cols);

            for (int col : cols) {
                List<int[]> colList = colMap.get(col);
                // Sort by row first, then by value for tie-breaking
                colList.sort(Comparator.comparingInt((int[] a) -> a[0])
                        .thenComparingInt(a -> a[1]));
                // Extract only the values
                List<Integer> colValues = new ArrayList<>();
                for (int[] entry : colList) {
                    colValues.add(entry[1]);
                }
                result.add(colValues);
            }

            return result;
        }

        // Driver code
        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][] testCases = {
                    {1},
                    {0, 1, 2, 3, -1, -1, 4},
                    {5, 3, 8, 1, 4, 7, 10},
                    {10, 5, 15, -1, 8, 12, -1},
                    {2, 1, 3, -1, -1, -1, 4, -1, 5}
            };

            String[] descriptions = {
                    "Single node tree",
                    "Skewed tree with some nulls",
                    "Complete binary tree with 7 nodes",
                    "Tree with some missing children",
                    "Right-leaning tree"
            };

            int y = 1;
            for (int t = 0; t < testCases.length; t++) {
                // Convert int[] to List<TreeNode<Integer>>, using null for -1
                List<TreeNode<Integer>> nodeList = new ArrayList<>();
                for (int val : testCases[t]) {
                    nodeList.add(val == -1 ? null : new TreeNode<>(val));
                }

                BinaryTree<Integer> tree = new BinaryTree<>(nodeList);
                System.out.println(y++ + ".\tInput tree:");
                sol.displayTree(tree.root);
                System.out.println("\tDescription: " + descriptions[t]);
                List<List<Integer>> result = sol.verticalTraversal(tree.root);
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
