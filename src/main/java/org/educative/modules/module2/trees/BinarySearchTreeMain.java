package org.educative.modules.module2.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTreeMain {

    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static class BinarySearchTree {
        TreeNode root;

        public BinarySearchTree() {
            this.root = null;
        }

        public BinarySearchTree(TreeNode root) {
            this.root = root;
        }

        // Iterative way of insertion
        public void insertIterative(int value) {
            if (root == null) {
                root = new TreeNode(value);
                return;
            }

            TreeNode current = root;
            while (true) {
                if (value < current.data) {
                    if (current.left == null) {
                        current.left = new TreeNode(value);
                        return;
                    }
                    current = current.left;
                } else {  // duplicates go to the right
                    if (current.right == null) {
                        current.right = new TreeNode(value);
                        return;
                    }
                    current = current.right;
                }
            }
        }

        // Recursive way of insertion
        public void insert(int value) {
            root = insertHelper(root, value);
        }

        private TreeNode insertHelper(TreeNode root, int value) {
            // Base case:
            // If the current position is empty, create a new node
            // and insert the value here
            if (root == null) {
                return new TreeNode(value);
            }

            // If the value to insert is smaller than the current node's value,
            // recursively insert it into the left subtree
            if (value < root.data) {
                root.left = insertHelper(root.left, value);
            }
            // If the value is greater than or equal to the current node's value,
            // recursively insert it into the right subtree (duplicates go right)
            else {
                root.right = insertHelper(root.right, value);
            }

            // Return the (unchanged) root node after insertion
            return root;
        }

        public void preorder(TreeNode root) {
            if (root != null) {
                System.out.print(root.data + " ");
                preorder(root.left);
                preorder(root.right);
            }
        }

        public void inorder(TreeNode root) {
            if (root != null) {
                inorder(root.left);
                System.out.print(root.data + " ");
                inorder(root.right);
            }
        }

        public void postorder(TreeNode root) {
            if (root != null) {
                postorder(root.left);
                postorder(root.right);
                System.out.print(root.data + " ");
            }
        }

        public void levelOrder() {
            // If the tree is empty, there is nothing to traverse
            if (root == null) {
                return;
            }

            // Initialize a queue with the root node
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            // Continue until the queue is empty
            while (!queue.isEmpty()) {
                // Remove the first node from the queue (FIFO)
                TreeNode current = queue.poll();

                // Print the data of the current node
                System.out.print(current.data + " ");

                // If the current node has a left child, add it to the queue
                if (current.left != null) {
                    queue.add(current.left);
                }

                // If the current node has a right child, add it to the queue
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }

        // --- Display tree methods ---
        private int treeHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
        }

        public int height(TreeNode node) {
            // Base case:
            // If the node is null (empty subtree),
            // we return -1 so that leaf nodes have height 0
            if (node == null) {
                return -1;
            }

            // Recursively calculate the height of the left subtree
            int leftHeight = height(node.left);

            // Recursively calculate the height of the right subtree
            int rightHeight = height(node.right);

            // The height of the current node is:
            // 1 (for the current node) + the maximum height of its subtrees
            return 1 + Math.max(leftHeight, rightHeight);
        }

        public int height() {
            return heightHelper(root);
        }

        private int heightHelper(TreeNode node) {
            if (node == null) {
                return -1;
            }
            int leftHeight = heightHelper(node.left);
            int rightHeight = heightHelper(node.right);
            return 1 + Math.max(leftHeight, rightHeight);
        }

        public int size(TreeNode node) {
            // Base case:
            // If the node is null, it contributes 0 to the size
            // (i.e., no nodes in an empty subtree)
            if (node == null) {
                return 0;
            }

            // Count:
            // 1 for the current node
            // + size of left subtree
            // + size of right subtree
            return 1 + size(node.left) + size(node.right);
        }

        public int size() {
            return sizeHelper(root);
        }

        private int sizeHelper(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return 1 + sizeHelper(node.left) + sizeHelper(node.right);
        }

        public int depth(int target) {
            return depthHelper(root, target, 0);
        }

        private int depthHelper(TreeNode node, int target, int currentDepth) {
            if (node == null) {
                return -1;
            }
            if (node.data == target) {
                return currentDepth;
            }
            int leftResult = depthHelper(node.left, target, currentDepth + 1);
            if (leftResult != -1) {
                return leftResult;
            }
            return depthHelper(node.right, target, currentDepth + 1);
        }

        public int depth(TreeNode root, int target, int currentDepth) {
            // Base case:
            // If the current node is null, the target does not exist
            // in this path, so return -1
            if (root == null) {
                return -1;
            }

            // If the current node contains the target value,
            // return the current depth
            if (root.data == target) {
                return currentDepth;
            }

            // Recursively search for the target in the left subtree,
            // increasing the depth by 1
            int leftResult = depth(root.left, target, currentDepth + 1);

            // If the target was found in the left subtree,
            // return that depth immediately
            if (leftResult != -1) {
                return leftResult;
            }

            // Otherwise, search for the target in the right subtree,
            // also increasing the depth by 1
            return depth(root.right, target, currentDepth + 1);
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

        public boolean searchRecursive(TreeNode node, int target) {
            // Base case: if the current node is null, the value is not found
            if (node == null) {
                return false;
            }

            // Check if the current node's value matches the target
            if (node.data == target) {
                return true;
            }

            // Check if the current node's value is greater than the target
            // i.e. the target is smaller, search in the left subtree
            if (target < node.data) {
                return searchRecursive(node.left, target);
            }

            // Check if the current node's value is less than the target
            // i.e. the target is larger, search in the right subtree
            return searchRecursive(node.right, target);
        }

        public boolean searchIterative(int target) {
            // Start from the root node
            TreeNode current = root;

            // Traverse the tree until we reach a null node
            while (current != null) {
                // If the target value is found, return true
                if (target == current.data) {
                    return true;
                }

                // If the target is smaller, move to the left subtree
                else if (target < current.data) {
                    current = current.left;
                }

                // If the target is greater than or equal to the current node,
                // move to the right subtree (duplicates go right)
                else {
                    current = current.right;
                }
            }

            // If we reach here, the value was not found
            return false;
        }

        public TreeNode minValueNode(TreeNode node) {
            TreeNode current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        public void delete(int value) {
            root = deleteHelper(root, value);
        }

        public TreeNode deleteHelper(TreeNode root, int value) {
            if (root == null) {
                return null;
            }

            if (value < root.data) {
                root.left = deleteHelper(root.left, value);
            } else if (value > root.data) {
                root.right = deleteHelper(root.right, value);
            } else {
                // Case 1 and Case 2: node has 0 or 1 child
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                // Case 3: node has 2 children
                TreeNode successor = minValueNode(root.right);
                root.data = successor.data;
                root.right = deleteHelper(root.right, successor.data);
            }

            return root;
        }
    }

    static class Main {
        public static void main(String[] args) {
            TreeNode root = new TreeNode(10);
            TreeNode node1 = new TreeNode(5);
            TreeNode node2 = new TreeNode(15);
            TreeNode node3 = new TreeNode(3);
            TreeNode node4 = new TreeNode(7);

            root.left = node1;
            root.right = node2;
            node1.left = node3;
            node1.right = node4;

            BinarySearchTree tree = new BinarySearchTree();
            tree.root = root;

            System.out.println("Root: " + tree.root.data);
            System.out.println("Left child of root: " + tree.root.left.data);
            System.out.println("Right child of root: " + tree.root.right.data);

            tree.displayTree(tree.root);

            System.out.println("_".repeat(100));

            BinarySearchTree bt = new BinarySearchTree();
            bt.insert(5);
            bt.insert(15);
            bt.insert(3);
            bt.insert(7);
            bt.insert(12);
            bt.insert(18);

            bt.displayTree(bt.root);

            System.out.println("_".repeat(100));

            System.out.println("Inorder Traversal:");
            bt.inorder(bt.root);
            System.out.println();

            System.out.println("Preorder Traversal:");
            bt.preorder(bt.root);
            System.out.println();

            System.out.println("Postorder Traversal:");
            bt.postorder(bt.root);
            System.out.println();

            System.out.println("Level Order Traversal:");
            bt.levelOrder();
            System.out.println();

            System.out.println("_".repeat(100));
            System.out.println("Initial tree");
            bt.displayTree(bt.root);

            System.out.println("Search for 3: " + bt.searchRecursive(bt.root, 3));
            System.out.println("Search for 7: " + bt.searchRecursive(bt.root, 7));
            System.out.println("Search for 99: " + bt.searchRecursive(bt.root, 99));
            bt.delete(7);
            System.out.println("After deleting 7:");
            bt.displayTree(bt.root);

            bt.delete(3);
            System.out.println("After deleting 3:");
            bt.displayTree(bt.root);

            TreeNode root2 = new TreeNode(11);
            root2.left = new TreeNode(12);
            root2.right = new TreeNode(13);
            root2.left.left = new TreeNode(14);
            root2.left.right = new TreeNode(15);
            root2.right.left = new TreeNode(16);

            BinarySearchTree tree2 = new BinarySearchTree(root2);
            tree2.displayTree(tree2.root);

            System.out.println("Height of tree: " + tree2.height());
            System.out.println("Size of tree: " + tree2.size());

            System.out.println("Depth of A: " + tree2.depth(11));
            System.out.println("Depth of B: " + tree2.depth(12));
            System.out.println("Depth of E: " + tree2.depth(13));
            System.out.println("Depth of F: " + tree2.depth(14));
            System.out.println("Depth of X: " + tree2.depth(15));
            System.out.println("_".repeat(100));
            BinarySearchTree bst = new BinarySearchTree();
            int[] values = {50, 30, 70, 20, 40, 60, 80};
            for (int value : values) {
                bst.insert(value);
            }

            System.out.println("Recursive search for 60: " + bst.searchRecursive(bst.root, 60));
            System.out.println("Recursive search for 25: " + bst.searchRecursive(bst.root, 25));

            System.out.println("Iterative search for 80: " + bst.searchIterative(80));
            System.out.println("Iterative search for 10: " + bst.searchIterative(10));

            System.out.println("Tree after insertion:");
            bst.displayTree(bst.root);

            bst.delete(20);
            System.out.println("\nAfter deleting 20:");
            bst.displayTree(bst.root);

            bst.delete(30);
            System.out.println("\nAfter deleting 30:");
            bst.displayTree(bst.root);

            bst.delete(50);
            System.out.println("\nAfter deleting 50:");
            bst.displayTree(bst.root);

        }
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}