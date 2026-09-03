package org.educative.modules.module2.trees;

import java.util.*;

/**
 * Time Complexity: O(n +e), where n is the number of nodes in the BST. and e is the number of edges in the BST.
 * In the worst case, we may need to traverse all nodes to find the kth smallest element.
 * Space Complexity: O(h), where h is the height of the BST (due to the stack used for in-order traversal).
 * <p>
 * Given a binary search tree, write a function to find the kth smallest element in it.
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 */

public class KthSmallestBSTSolution<T> {
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

    public int kthSmallest(TreeNode<Integer> root, int k) {
        // Use iterative in-order traversal (left -> node -> right) to find kth smallest
        Deque<TreeNode<Integer>> stack = new ArrayDeque<>();
        TreeNode<Integer> current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            // Traverse to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Pop the top node from stack (smallest unvisited node)
            current = stack.pop();
            count++;

            // If count equals k, we found the kth smallest
            if (count == k) {
                return current.data;
            }

            // Move to the right subtree
            current = current.right;
        }

        return -1;
    }

    public static void main(String[] args) {
        KthSmallestBSTSolution<Integer> sol = new KthSmallestBSTSolution<>();

        int[][][] testCases = {
                {{2, 1, 3},                      {2}},
                {{5, 3, 7, 2, 4, 6, 8},          {5}},
                {{1},                             {1}},
                {{4, 2, 6, 1, 3, 5, 7},          {7}},
                {{10, 5, 15, 3, 8, -1, 20, 1},   {4}},
        };

        String[] descriptions = {
                "Simple 3-node BST, k=2",
                "Complete BST with 7 nodes, k=5",
                "Single node tree, k=1",
                "Full BST, find the largest (k=7)",
                "Unbalanced BST, k=4",
        };

        int idx = 1;
        for (int t = 0; t < testCases.length; t++) {
            int[] treeArr = testCases[t][0];
            int k = testCases[t][1][0];

            List<TreeNode<Integer>> nodeList = new ArrayList<>();
            for (int val : treeArr) {
                nodeList.add(val == -1 ? null : new TreeNode<>(val));
            }

            BinaryTree<Integer> tree = new BinaryTree<>(nodeList);

            System.out.println(idx + ".\tInput tree:");
            sol.displayTree(tree.root);
            System.out.println("\tTarget: " + k);

            int result = sol.kthSmallest(tree.root, k);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
            idx++;
        }
    }
}

