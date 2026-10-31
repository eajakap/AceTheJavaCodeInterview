package org.educative.modules.module2.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeGeneric {

    static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static class BinaryTree<T> {
        TreeNode<T> root;

        // Pairs a node with its parent during level-order traversal in delete()
        private record NodeWithParent<T>(TreeNode<T> node, TreeNode<T> parent) {
        }

        public BinaryTree(T rootValue) {
            this.root = rootValue != null ? new TreeNode<>(rootValue) : null;
        }

        public BinaryTree() {
            this.root = null;
        }

        public BinaryTree(TreeNode<T> root) {
            this.root = root;
        }

        public boolean search(TreeNode<T> root, T target) {
            if (root == null) {
                return false;
            }

            if (root.data.equals(target)) {
                return true;
            }

            return search(root.left, target) || search(root.right, target);
        }

        public void insert(T value) {
            TreeNode<T> newNode = new TreeNode<>(value);

            if (root == null) {
                root = newNode;
                return;
            }

            Queue<TreeNode<T>> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                TreeNode<T> current = queue.poll();

                if (current.left == null) {
                    current.left = newNode;
                    return;
                }
                queue.add(current.left);

                if (current.right == null) {
                    current.right = newNode;
                    return;
                }
                queue.add(current.right);
            }
        }

        public void preorder(TreeNode<T> root) {
            if (root != null) {
                System.out.print(root.data + " ");
                preorder(root.left);
                preorder(root.right);
            }
        }

        public void inorder(TreeNode<T> root) {
            if (root != null) {
                inorder(root.left);
                System.out.print(root.data + " ");
                inorder(root.right);
            }
        }

        public void postorder(TreeNode<T> root) {
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
            Queue<TreeNode<T>> queue = new LinkedList<>();
            queue.add(root);

            // Continue until the queue is empty
            while (!queue.isEmpty()) {
                // Remove the first node from the queue (FIFO)
                TreeNode<T> current = queue.poll();

                // Print the data of the current node
                System.out.print(current.data + " ");

                // If the current node has a left child, add it to back of the queue
                if (current.left != null) {
                    queue.add(current.left);
                }

                // If the current node has a right child, add it to the back of the queue
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }

        public void delete(T target) {
            if (root == null) {
                return;
            }

            if (root.left == null && root.right == null) {
                if (root.data.equals(target)) {
                    root = null;
                }
                return;
            }

            Queue<NodeWithParent<T>> queue = new LinkedList<>();
            queue.add(new NodeWithParent<>(root, null));

            TreeNode<T> targetNode = null;
            TreeNode<T> lastNode = null;
            TreeNode<T> lastParent = null;

            while (!queue.isEmpty()) {
                NodeWithParent<T> pair = queue.poll();
                TreeNode<T> current = pair.node();
                TreeNode<T> parent = pair.parent();

                if (current.data.equals(target)) {
                    targetNode = current;
                }

                lastNode = current;
                lastParent = parent;

                if (current.left != null) {
                    queue.add(new NodeWithParent<>(current.left, current));
                }
                if (current.right != null) {
                    queue.add(new NodeWithParent<>(current.right, current));
                }
            }

            if (targetNode != null) {
                targetNode.data = lastNode.data;

                if (lastParent != null) {
                    if (lastParent.left == lastNode) {
                        lastParent.left = null;
                    } else {
                        lastParent.right = null;
                    }
                }
            }
        }

        public int height(TreeNode<T> node) {
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

        private int heightHelper(TreeNode<T> node) {
            if (node == null) {
                return -1;
            }
            int leftHeight = heightHelper(node.left);
            int rightHeight = heightHelper(node.right);
            return 1 + Math.max(leftHeight, rightHeight);
        }

        public int size(TreeNode<T> node) {
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

        private int sizeHelper(TreeNode<T> node) {
            if (node == null) {
                return 0;
            }
            return 1 + sizeHelper(node.left) + sizeHelper(node.right);
        }

        public int depth(TreeNode<T> root, T target, int currentDepth) {
            // Base case:
            // If the current node is null, the target does not exist
            // in this path, so return -1
            if (root == null) {
                return -1;
            }

            // If the current node contains the target value,
            // return the current depth
            if (root.data.equals(target)) {
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

        public int depth(T target) {
            return depthHelper(root, target, 0);
        }

        private int depthHelper(TreeNode<T> node, T target, int currentDepth) {
            if (node == null) {
                return -1;
            }
            if (node.data.equals(target)) {
                return currentDepth;
            }
            int leftResult = depthHelper(node.left, target, currentDepth + 1);
            if (leftResult != -1) {
                return leftResult;
            }
            return depthHelper(node.right, target, currentDepth + 1);
        }

    }

    static class PrintBinaryTree {
        // --- Display tree methods ---
        // Display only reads node.data via toString(), so any element type works: TreeNode<?>
        private static int treeHeight(TreeNode<?> node) {
            if (node == null) return 0;
            return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
        }


        private static void drawNode(List<StringBuilder> output, List<StringBuilder> linkAbove,
                              TreeNode<?> node, int level, int p, char linkChar) {
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

        public static void displayTree(TreeNode<?> root) {
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
    }

    public static void testBinaryTreeWithStrings() {
        BinaryTree<String> bt = new BinaryTree<>("A");
        bt.insert("B");
        bt.insert("C");
        bt.insert("D");
        bt.insert("E");
        bt.insert("F");

        System.out.println("Initial tree");
        PrintBinaryTree.displayTree(bt.root);

        System.out.println("Height of tree: " + bt.height());
        System.out.println("Size of tree: " + bt.size());

        System.out.println("Depth of A: " + bt.depth("A"));
        System.out.println("Depth of B: " + bt.depth("B"));
        System.out.println("Depth of E: " + bt.depth("E"));
        System.out.println("Depth of F: " + bt.depth("F"));
        System.out.println("Depth of X: " + bt.depth("X"));

        System.out.println("Search for C: " + bt.search(bt.root, "C"));
        System.out.println("Search for X: " + bt.search(bt.root, "X"));

        bt.delete("B");
        System.out.println("After deleting B:");
        PrintBinaryTree.displayTree(bt.root);

        bt.delete("A");
        System.out.println("After deleting A:");
        PrintBinaryTree.displayTree(bt.root);
    }

    public static void testBinaryTreeCreatedWithStringNodes() {
        TreeNode<String> root = new TreeNode<>("A");
        root.left = new TreeNode<>("B");
        root.right = new TreeNode<>("C");
        root.left.left = new TreeNode<>("D");
        root.left.right = new TreeNode<>("E");
        root.right.left = new TreeNode<>("F");

        BinaryTree<String> tree = new BinaryTree<>(root);
        PrintBinaryTree.displayTree(tree.root);

        System.out.println("Height of tree: " + tree.height());
        System.out.println("Size of tree: " + tree.size());

        System.out.println("Depth of A: " + tree.depth("A"));
        System.out.println("Depth of B: " + tree.depth("B"));
        System.out.println("Depth of E: " + tree.depth("E"));
        System.out.println("Depth of F: " + tree.depth("F"));
        System.out.println("Depth of X: " + tree.depth("X"));

    }

    public static void testBinaryTreeWithIntegers() {
        BinaryTree<Integer> bt = new BinaryTree<>(10);
        bt.insert(20);
        bt.insert(30);
        bt.insert(40);
        bt.insert(50);

        System.out.println("Initial tree");
        PrintBinaryTree.displayTree(bt.root);

        System.out.println("Search for 20: " + bt.search(bt.root, 20));
        System.out.println("Search for 60: " + bt.search(bt.root, 60));

        bt.delete(20);
        System.out.println("After deleting 20:");
        PrintBinaryTree.displayTree(bt.root);

        bt.delete(10);
        System.out.println("After deleting 10:");
        PrintBinaryTree.displayTree(bt.root);
    }

    public static void testBinaryTreeDFSTraversals() {
        BinaryTree<String> bt = new BinaryTree<>("A");
        bt.insert("B");
        bt.insert("C");
        bt.insert("D");
        bt.insert("E");
        bt.insert("F");

        System.out.println("Preorder traversal:");
        bt.preorder(bt.root);
        System.out.println();

        System.out.println("Inorder traversal:");
        bt.inorder(bt.root);
        System.out.println();

        System.out.println("Postorder traversal:");
        bt.postorder(bt.root);
        System.out.println();
    }

    public static void testBinaryTreeBFSTraversals() {
        BinaryTree<String> bt = new BinaryTree<>("A");
        bt.insert("B");
        bt.insert("C");
        bt.insert("D");
        bt.insert("E");
        bt.insert("F");

        System.out.println("Level-order traversal:");
        bt.levelOrder();
        System.out.println();
    }

    public static void main(String[] args) {
        testBinaryTreeWithStrings();
        testBinaryTreeCreatedWithStringNodes();
        testBinaryTreeWithIntegers();
        testBinaryTreeDFSTraversals();
        testBinaryTreeBFSTraversals();
    }

}
