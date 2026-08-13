package org.educative.modules.module2.trees;

import java.util.*;


public class RecoverBSTSolution<T> {
    // Definiton of a binary tree node class
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

    private static class BinaryTree<T> {
        TreeNode<T> root;

        public BinaryTree() {
            root = createBinaryTree(new ArrayList<>());
        }

        public BinaryTree(List<TreeNode<T>> ListOfNodes) {
            root = createBinaryTree(ListOfNodes);
        }

        private TreeNode<T> createBinaryTree(List<TreeNode<T>> ListOfNodes) {
            if (ListOfNodes.isEmpty()) {
                return null;
            }

            // Create the root node of the binary tree
            TreeNode<T> root = new TreeNode<>(ListOfNodes.get(0).data);

            // Create a queue and add the root node to it
            Queue<TreeNode<T>> q = new LinkedList<>();
            q.add(root);

            // Start iterating over the list of ListOfNodes starting from the second node
            int i = 1;
            while (i < ListOfNodes.size()) {
                // Get the next node from the queue
                TreeNode<T> curr = q.remove();

                // If the node is not null, create a new TreeNode object for its left child,
                // set it as the left child of the current node, and add it to the queue
                if (ListOfNodes.get(i) != null) {
                    curr.left = new TreeNode<>(ListOfNodes.get(i).data);
                    q.add(curr.left);
                }

                i++;

                // If there are more ListOfNodes in the list and the next node is not null,
                // create a new TreeNode object for its right child, set it as the right child
                // of the current node, and add it to the queue
                if (i < ListOfNodes.size() && ListOfNodes.get(i) != null) {
                    curr.right = new TreeNode<>(ListOfNodes.get(i).data);
                    q.add(curr.right);
                }

                i++;
            }

            // Return the root of the binary tree
            return root;
        }
    }

    // --- Display tree methods ---
    private int treeHeight(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
    }

    private void drawNode(List<StringBuilder> output, List<StringBuilder> linkAbove,
                          TreeNode<T> node, int level, int p, char linkChar) {
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

    public void displayTree(TreeNode<T> root) {
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

    public static TreeNode<Integer> recoverTree(TreeNode<Integer> root) {
        /*
         * Recover BST where exactly two nodes are swapped using Morris Inorder Traversal (O(1) space).
         * We find two nodes that violate the inorder sorted property and swap their values.
         */

        // firstNode and secondNode are the two swapped nodes; prevNode tracks the previous node in inorder
        TreeNode<Integer> firstNode = null;
        TreeNode<Integer> secondNode = null;
        TreeNode<Integer> prevNode = null;

        // Morris Inorder Traversal for O(1) space
        TreeNode<Integer> current = root;
        while (current != null) {
            if (current.left == null) {
                // Visit current node: check for violation
                if (prevNode != null && prevNode.data > current.data) {
                    if (firstNode == null) {
                        // First violation: prevNode is the first swapped node
                        firstNode = prevNode;
                    }
                    // Second violation (or adjacent swap): current is the second swapped node
                    secondNode = current;
                }
                prevNode = current;
                // Move to right subtree
                current = current.right;
            } else {
                // Find the inorder predecessor of current
                TreeNode<Integer> predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Create a temporary thread back to current
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove the thread
                    predecessor.right = null;
                    // Visit current node: check for violation
                    if (prevNode != null && prevNode.data > current.data) {
                        if (firstNode == null) {
                            firstNode = prevNode;
                        }
                        secondNode = current;
                    }
                    prevNode = current;
                    current = current.right;
                }
            }
        }

        // Swap the values of the two misplaced nodes
        if (firstNode != null && secondNode != null) {
            int temp = firstNode.data;
            firstNode.data = secondNode.data;
            secondNode.data = temp;
        }
        return root;
    }

    public static void main(String[] args) {
        RecoverBSTSolution sol = new RecoverBSTSolution();

        int[][] testCases = {
                {3, 1, 4, -1, -1, 2},
                {1, 3, -1, -1, 2}
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            List<TreeNode<Integer>> nodeList = new ArrayList<>();
            for (int val : testCases[t]) {
                nodeList.add(val == -1 ? null : new TreeNode<>(val));
            }

            BinaryTree<Integer> tree = new BinaryTree<>(nodeList);
            System.out.println(y++ + ".\tInput tree:");
            sol.displayTree(tree.root);
            sol.recoverTree(tree.root);
            System.out.println("\tRecovered tree:");
            sol.displayTree(tree.root);
            System.out.println("-".repeat(100));
        }
    }
}

