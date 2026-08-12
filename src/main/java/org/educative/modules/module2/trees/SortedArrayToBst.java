package org.educative.modules.module2.trees;

import java.util.*;

public class SortedArrayToBst<T> {

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

    public SortedArrayToBst() {
        root = createBinaryTree(new ArrayList<>());
    }

    public SortedArrayToBst(List<TreeNode<T>> ListOfNodes) {
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
    public TreeNode<Integer> sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        // get the root of the binary tree from the sorted array
        int rootIndex = nums.length / 2;
        TreeNode<Integer> root = new TreeNode<>(nums[rootIndex]);
        // add the left and right subtrees recursively
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, rootIndex));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, rootIndex + 1, nums.length));
        return root;
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {1, 2, 3, 4, 5, 6, 7},
                {0},
                {-5, -2, 0, 3, 6, 8},
                {-100, -50, 0, 50, 100},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
        };

        int i = 1;
        SortedArrayToBst<Integer> solution = new SortedArrayToBst<>(); // instance created to call non-static methods
        for (int[] nums : testCases) {
            TreeNode<Integer> root = solution.sortedArrayToBST(nums);

            System.out.println(i + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tResult:");
            solution.displayTree(root);
            System.out.println("-".repeat(100));
            i++;
        }
    }

}