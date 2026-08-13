package org.educative.modules.module2.trees;

import java.util.*;

public class ValidateBst<T> {

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

    private TreeNode<T> root;

    public ValidateBst() {
        root = createBinaryTree(new ArrayList<>());
    }

    public ValidateBst(List<TreeNode<T>> ListOfNodes) {
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
        * Function to convert a sorted array to a balanced binary search tree (BST)
        * 1. If nums is empty, return null as the base case, since there are no elements to form a subtree.
        * 2. Compute the middle index mid as nums.length / 2. This selects the middle element of the current subarray (choosing the lower middle when the length is even).
        * 3. Create a new TreeNode with the value nums[mid] as the root of the current subtree.
        * 3.1 Since nums is sorted, placing the middle element at the root ensures that all elements before mid are smaller (valid for the left subtree) and all elements after mid are larger (valid for the right subtree).
        * 4. Recursively build the left subtree by calling the function on the left half of the array, Arrays.copyOfRange(nums, 0, mid), and assign the result to root.left.
        * 4.1 This subarray contains all elements strictly less than Arrays.copyOfRange(nums, mid + 1, nums.length), preserving the BST ordering property.
        * 5. Recursively build the right subtree by calling the function on the right half of the array, Arrays.copyOfRange(nums, mid + 1, nums.length), and assign the result to root.right.
        * 5.1 This subarray contains all elements strictly greater than nums[mid], preserving the BST ordering property.
        *
        * Return root, which now points to a height-balanced BST for the current subarray.
        */
    public static boolean isValidBST(TreeNode<Integer> root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode<Integer> node, long lower, long upper) {
        // Base case: empty node is valid
        if (node == null) {
            return true;
        }
        // Current node's value must be strictly within bounds
        if (node.data <= lower || node.data >= upper) {
            return false;
        }
        // Left subtree must have values < node.data, right subtree must have values > node.data
        return validate(node.left, lower, node.data) && validate(node.right, node.data, upper);
    }

    public static void main(String[] args) {
        Integer[][] testCases = {
                {1},
                {10, 5, 15, 3, 7, 12, 20},
                {5, 4, 6, -1, -1, 3, 7},
                {5, 3, 7, 1, 4, 6, 8},
                {5, 1, 7, null, 4, 6, 8},
                {5, 1, 7, null, null, 6, 8},
                {5, 1, 7, null, 4, 6, 3},
                {10, 5, 15, null, null, 6, 20}
        };

        for (int i = 0; i < testCases.length; i++) {
            List<TreeNode<Integer>> nodes = Arrays.stream(testCases[i]).map(val -> val == null ? null : new TreeNode<>(val)).toList();
//            Arrays.stream(testCases[i]).forEach(val -> {nodes.add(val == null ? null : new TreeNode<>(val));});
//            for (Integer value : testCases[i]) {
//                nodes.add(value == null ? null : new TreeNode<>(value));
//            }
            ValidateBst<Integer> tree = new ValidateBst<>(nodes);
            System.out.println(i + 1 + ".\tInput tree:");
            tree.displayTree(tree.root);
            System.out.println((i + 1) + ".\tValid BST? " + isValidBST(tree.root));
            System.out.println("\tTree: " + Arrays.deepToString(testCases[i]));
            System.out.println("-".repeat(100));
        }
    }

}