package org.educative.modules.module2.trees;

import java.util.*;

public class BinaryTreeMain {

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

    static class BinaryTree {
        TreeNode root;

        public BinaryTree() {
            this.root = null;
        }

        public BinaryTree(TreeNode root) {
            this.root = root;
        }

        public void insert(int value) {
            TreeNode newNode = new TreeNode(value);
            if (root == null) {
                root = newNode;
                return;
            }

            // Use a queue to perform level-order insertion
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            // Keep checking nodes until an empty spot is found
            while (!queue.isEmpty()) {
                // Remove the first node from the queue
                TreeNode current = queue.poll();

                // If the left child is empty, insert the new node there
                if (current.left == null) {
                    current.left = newNode;
                    return;
                } else {
                    // Otherwise, add the left child to the queue
                    queue.add(current.left);
                }

                // If the right child is empty, insert the new node there
                if (current.right == null) {
                    current.right = newNode;
                    return;
                } else {
                    // Otherwise, add the right child to the queue
                    queue.add(current.right);
                }
            }
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
            int h = height(root);
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

        public boolean search(TreeNode root, int target) {
            // Base case: if the current node is null, the value is not found
            if (root == null) {
                return false;
            }

            // Check if the current node's value matches the target
            if (root.data == target) {
                return true;
            }

            // Recursively search in the left subtree OR right subtree
            // If found in either, return true
            return search(root.left, target) || search(root.right, target);
        }

        public void delete(int target) {
            // If the tree is empty, there is nothing to delete
            if (root == null) {
                return;
            }

            if (root.left == null && root.right == null) {
                if (root.data == target) {
                    root = null;
                }
                return;
            }

            // Create a queue for level-order traversal
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            // Will store the node containing the target value
            TreeNode targetNode = null;

            // Will keep track of the last node visited in level-order traversal
            TreeNode lastNode = null;
            TreeNode lastParent = null;

            // Traverse the tree level by level
            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();

                // If the current node contains the target value,
                // save it as the node to be deleted
                if (current.data == target) {
                    targetNode = current;
                }

                lastNode = current;

                if (current.left != null) {
                    lastParent = current;
                    queue.add(current.left);
                }
                if (current.right != null) {
                    lastParent = current;
                    queue.add(current.right);
                }
            }

            // If the target node was found
            if (targetNode != null) {
                // Replace the target node's value with the deepest/rightmost node's value
                targetNode.data = lastNode.data;

                // Remove the deepest/rightmost node
                if (lastParent != null) {
                    if (lastParent.right == lastNode) {
                        lastParent.right = null;
                    } else {
                        lastParent.left = null;
                    }
                }
            }
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

            BinaryTree tree = new BinaryTree();
            tree.root = root;

            System.out.println("Root: " + tree.root.data);
            System.out.println("Left child of root: " + tree.root.left.data);
            System.out.println("Right child of root: " + tree.root.right.data);

            tree.displayTree(tree.root);

            System.out.println("_".repeat(100));

            BinaryTree bt = new BinaryTree();
            bt.insert(5);
            bt.insert(15);
            bt.insert(3);
            bt.insert(7);
            bt.insert(12);
            bt.insert(18);

            bt.displayTree(bt.root);

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

            System.out.println("Initial tree");
            bt.displayTree(bt.root);

            System.out.println("Search for 3: " + bt.search(bt.root, 3));
            System.out.println("Search for 7: " + bt.search(bt.root, 7));
            System.out.println("Search for 99: " + bt.search(bt.root, 99));
            bt.delete(7);
            System.out.println("After deleting 7:");
            bt.displayTree(bt.root);

            bt.delete(3);
            System.out.println("After deleting 3:");
            bt.displayTree(bt.root);

            TreeNode root2 = new TreeNode(11);
            root.left = new TreeNode(12);
            root.right = new TreeNode(13);
            root.left.left = new TreeNode(14);
            root.left.right = new TreeNode(15);
            root.right.left = new TreeNode(16);

            BinaryTree tree2 = new BinaryTree(root2);
            tree.displayTree(tree.root);

            System.out.println("Height of tree: " + tree2.height());
            System.out.println("Size of tree: " + tree2.size());

            System.out.println("Depth of A: " + tree2.depth(11));
            System.out.println("Depth of B: " + tree2.depth(12));
            System.out.println("Depth of E: " + tree2.depth(13));
            System.out.println("Depth of F: " + tree2.depth(14));
            System.out.println("Depth of X: " + tree2.depth(15));

        }
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}