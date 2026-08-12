package org.educative.modules.module2.trees;

import java.util.*;

public class RangeSumBst<T> {

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

    TreeNode<T> root;

    public RangeSumBst(List<TreeNode<T>> ListOfNodes) {
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

    /*
     * Now, let’s look at the solution steps below:
     * 1. If the current node root is null, return 0 as the base case of the recursion.
     * 2. If root.data is less than low, the current node and its entire left subtree are out of range.
     *     2.1 Recurse only into the right subtree by returning the result of rangeSumBST(root.right, low, high).
     * 3. If root.data is greater than high, the current node and its entire right subtree are out of range.
     *     3.1 Recurse only into the left subtree by returning the result of rangeSumBST(root.left, low, high).
     * 4. If root.data is within the inclusive range [low, high], include root.data in the sum.
     *     4.1 Recurse into both the left and right subtrees.
     *     4.2 Return root.data plus the results from both recursive calls.
     * 5. The final returned value at the root of the tree is the total sum of all node values within the range.
     */
    public static int rangeSumBST(TreeNode<Integer> root, int low, int high) {

        if (root == null) return 0;

        // If value is smaller than L, skip left subtree
        if (root.data < low) {
            return rangeSumBST(root.right, low, high);
        }

        // If value is larger than R, skip right subtree
        if (root.data > high) {
            return rangeSumBST(root.left, low, high);
        }

        // Value is within range → include it and explore both sides
        return root.data
                + rangeSumBST(root.left, low, high)
                + rangeSumBST(root.right, low, high);
    }

    public int rangeSumIterativeBST(TreeNode<Integer> root, int L, int R) {
        int sum = 0;
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            if (node == null) continue;

            if (node.data >= L && node.data <= R) {
                sum += node.data;
            }
            if (node.data > L) {
                stack.push(node.left);
            }
            if (node.data < R) {
                stack.push(node.right);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Object[][] testCases = {
                {new Integer[]{20, 10, 30, 5, 15, 25, 35}, 12, 28},
                {new Integer[]{8, 4, 12, 2, 6, 10, 14}, 4, 10},
                {new Integer[]{50}, 1, 100},
                {new Integer[]{50}, 51, 100},
                {new Integer[]{40, 20, 60, 10, 30, 50, 70, 5, 15, 25, 35, 45, 55, 65, 75}, 25, 55}
        };

        for (int i = 0; i < testCases.length; i++) {
            Integer[] values = (Integer[]) testCases[i][0];
            int low = (int) testCases[i][1];
            int high = (int) testCases[i][2];

            List<TreeNode<Integer>> nodes = new ArrayList<>();
            for (Integer value : values) {
                nodes.add(value == null ? null : new TreeNode<>(value));
            }

            RangeSumBst<Integer> tree = new RangeSumBst<>(nodes);
            System.out.println((i + 1) + ".\tInput tree:");
            tree.displayTree(tree.root);
            System.out.println("Low: " + low + ", High: " + high);
            System.out.println("Result: " + rangeSumBST(tree.root, low, high));
            System.out.println("-".repeat(100));
        }
    }

}