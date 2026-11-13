package org.educative.modules.module2.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

/**
 * Red-Black Balanced Binary Search Tree
 * Problem:
 *  - A Red-Black Tree is a self-balancing Binary Search Tree (BST).
 *    It keeps the tree roughly balanced by assigning every node a color,
 *    Red or Black, and repairing the tree after insertions and deletions.
 *  - Develop a data structure to represent a Red-Black Balanced BST.
 *  - Duplicates are allowed (an equal value goes to the right); null values are rejected.
 * The 5 Red-Black Tree rules:
 * 1. Every node is either red or black.
 * 2. The root is black.
 * 3. Every leaf (NIL) is black.
 * 4. If a red node has children then, the children are always black.
 * 5. Every path from a given node to any of its descendant NIL (leaf) nodes contains the same number of black nodes.
 * Notes:
 *  - RED cannot have a RED child.
 *          20(B)
 *        /     \
 *     10(R)    30(R)
 * Time Complexity: [search = O(logn), Insertion = O(logn), Deletion = O(logn), Traversal = O(n),
 *                   height = O(n), depth = O(logn)]
 * Space Complexity: O(n)
 */
public class RedBlackBalancedBST<T extends Comparable<T>> {
    /**
     * Interview mental model:
     * Red-Black Tree
     *  → Binary Search Tree
     *  → Every node has Red/Black color
     *  → Root is Black
     *  → No Red-Red parent-child
     *  → Every path has equal black height
     *  → Insert like BST
     *  → New node starts Red
     *  → Violation? Check Parent → Uncle → Grandparent
     *  → Red uncle = recolor
     *  → Black uncle = rotate + recolor
     */
    private enum Color { RED, BLACK }

    private static class Node <T> {
        T data;
        Color color;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        Node(T data) {
            this.data = data;
            this.color = Color.RED; // New nodes are always red
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node<T> root;
    private int size;

    public RedBlackBalancedBST() {
        this.root = null; // empty tree; insert() creates the black root
    }

    public RedBlackBalancedBST(T data) {
        insert(data);
    }

    public RedBlackBalancedBST(T[] data) {
        Objects.requireNonNull(data, "data array must not be null");
        for (T value : data) {
            insert(value);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // =========================================================
    // 1. INSERT
    // =========================================================
    public void insert(T data) {
        Objects.requireNonNull(data, "null values are not allowed");
        size++;
        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
            root.color = Color.BLACK; // Root is always black
        } else {
            insertHelper(root, newNode);
            // Restore Red-Black Tree properties
            fixInsert(newNode);
        }
    }

    /**
     * Inserts a new node into the tree based on the BST property.
     * Black-->Red-->Black-->Red-->Black
     * 1. If the new node's value is less than the current node's value, go left.
     * 2. If the new node's value is greater than or equal to the current node's value,
     *      go right.
     * 3. If the left or right child is null, insert the new node there and set its
     *      parent to the current node.
     * @param node - the node to be used for comparison
     * @param newNode - the new node to be inserted
     */
    private void insertHelper(Node<T> node, Node<T> newNode) {
        if (newNode.data.compareTo(node.data) < 0) {
            // Go left
            if (node.left == null) { // base condition for left subtree
                // Insert new node and set parent
                node.left = newNode;
                newNode.parent = node;
            } else {
                // Recurse to the next level of the left subtree
                insertHelper(node.left, newNode);
            }
        } else {
            // Go right
            if (node.right == null) { // base condition for right subtree
                // Insert new node and set parent
                node.right = newNode;
                newNode.parent = node;
            } else {
                // Recurse to the next level of the right subtree
                insertHelper(node.right, newNode);
            }
        }
    }

    // =========================================================
    // 2. FIX RED-RED VIOLATION
    // fixInsertViolation (insert), which uses two primitives, rotateLeft and rotateRight:
    //    Case       |     Where                                           |    Handling
    //    Left-Left  |  parent is the left child, node is the left child   | rotateRight(grandParent) + recolor
    //    Left-Right |  parent is the left child, node is the right child  | rotateLeft(parent), then falls into Left-Left
    //    Right-Right|  parent is the right child, node is the right child | rotateLeft(grandParent) + recolor
    //    Right-Left |  parent is the right child, node is the left child  | rotateRight(parent), then falls into Right-Right
    // =========================================================
    private void fixInsert(Node<T> node) {
        while (node != root && node.parent.color == Color.RED) {
            Node<T> parent = node.parent;
            Node<T> grandParent = parent.parent; // never null: a red parent is never the root

            if (parent == grandParent.left) {
                // -------------------------------------------------
                // Parent is LEFT child of grandparent
                // -------------------------------------------------
                Node<T> uncle = grandParent.right;

                // Case 1: Red uncle → recolor and move up
                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    node = grandParent;
                } else {
                    // Case 2: Left-Right → rotate parent left to turn it into Left-Left
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Case 3: Left-Left → rotate grandparent right and swap colors
                    rotateRight(grandParent);
                    parent.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    break; // subtree root is now black → no violation left
                }
            } else {
                // -------------------------------------------------
                // Parent is RIGHT child of grandparent
                // -------------------------------------------------
                Node<T> uncle = grandParent.left;

                // Case 1: Red uncle → recolor and move up
                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    node = grandParent;
                } else {
                    // Case 2: Right-Left → rotate parent right to turn it into Right-Right
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Case 3: Right-Right → rotate grandparent left and swap colors
                    rotateLeft(grandParent);
                    parent.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    break; // subtree root is now black → no violation left
                }
            }
        }
        root.color = Color.BLACK; // Root is always black
    }

    // =========================================================
    // 3. ROTATIONS
    // =========================================================
    private void rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        replaceInParent(x, y);
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node<T> x) {
        Node<T> y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        replaceInParent(x, y);
        y.right = x;
        x.parent = y;
    }

    // Links newChild into oldChild's position under oldChild's parent (or as root)
    private void replaceInParent(Node<T> oldChild, Node<T> newChild) {
        newChild.parent = oldChild.parent;
        if (oldChild.parent == null) {
            root = newChild;
        } else if (oldChild == oldChild.parent.left) {
            oldChild.parent.left = newChild;
        } else {
            oldChild.parent.right = newChild;
        }
    }

    /**
     * Returns the minimum value in the tree.
     * @return the smallest value
     * @throws NoSuchElementException if the tree is empty
     */
    public T min() {
        if (root == null) throw new NoSuchElementException("tree is empty");
        return minimum(root).data;
    }

    /**
     * Returns the maximum value in the tree.
     * @return the largest value
     * @throws NoSuchElementException if the tree is empty
     */
    public T max() {
        if (root == null) throw new NoSuchElementException("tree is empty");
        return maximum(root).data;
    }

    // =========================================================
    // 4. SEARCH
    // =========================================================
    public boolean contains(T data) {
        Objects.requireNonNull(data, "null values are not allowed");
        return findNode(data) != null;
    }

    private Node<T> findNode(T data) {
        Node<T> current = root;
        while (current != null) {
            int cmp = data.compareTo(current.data);
            if (cmp == 0) {
                return current;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        return null;
    }

    private Node<T> minimum(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node<T> maximum(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // =========================================================
    // 5. DELETE
    // =========================================================
    /**
     * Deletes one occurrence of the value.
     * 1. Remove the node like a normal BST (a node with two children is replaced
     *    by its in-order successor).
     * 2. If the physically removed/moved node was BLACK, the black height of its path
     *    dropped by one, so fixDelete() repairs the "double black" at its replacement.
     * @return true if the value was found and removed
     */
    public boolean delete(T data) {
        Objects.requireNonNull(data, "null values are not allowed");
        Node<T> target = findNode(data);
        if (target == null) {
            return false;
        }
        size--;

        Node<T> removedOrMoved = target;
        Color removedColor = removedOrMoved.color;
        Node<T> replacement;       // node that takes the removed position (may be null)
        Node<T> replacementParent; // tracked separately because replacement may be null

        if (target.left == null) {
            replacement = target.right;
            replacementParent = target.parent;
            transplant(target, target.right);
        } else if (target.right == null) {
            replacement = target.left;
            replacementParent = target.parent;
            transplant(target, target.left);
        } else {
            removedOrMoved = minimum(target.right);
            removedColor = removedOrMoved.color;
            replacement = removedOrMoved.right;
            if (removedOrMoved.parent == target) {
                replacementParent = removedOrMoved;
            } else {
                replacementParent = removedOrMoved.parent;
                transplant(removedOrMoved, removedOrMoved.right);
                removedOrMoved.right = target.right;
                removedOrMoved.right.parent = removedOrMoved;
            }
            transplant(target, removedOrMoved);
            removedOrMoved.left = target.left;
            removedOrMoved.left.parent = removedOrMoved;
            removedOrMoved.color = target.color;
        }

        if (removedColor == Color.BLACK) {
            fixDelete(replacement, replacementParent);
        }
        return true;
    }

    // Replaces the subtree rooted at oldNode with the subtree rooted at newNode (may be null)
    private void transplant(Node<T> oldNode, Node<T> newNode) {
        if (oldNode.parent == null) {
            root = newNode;
        } else if (oldNode == oldNode.parent.left) {
            oldNode.parent.left = newNode;
        } else {
            oldNode.parent.right = newNode;
        }
        if (newNode != null) {
            newNode.parent = oldNode.parent;
        }
    }

    // Resolves the extra black on 'node' (null leaves are black, so the parent is passed explicitly)
    private void fixDelete(Node<T> node, Node<T> parent) {
        while (node != root && !isRed(node)) {
            if (node == parent.left) {
                Node<T> sibling = parent.right;

                // Case 1: Red sibling → rotate so the sibling becomes black
                if (isRed(sibling)) {
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    rotateLeft(parent);
                    sibling = parent.right;
                }
                // Case 2: Black sibling with two black children → recolor and move up
                if (!isRed(sibling.left) && !isRed(sibling.right)) {
                    sibling.color = Color.RED;
                    node = parent;
                    parent = node.parent;
                } else {
                    // Case 3: Sibling's far child is black → rotate to make it red
                    if (!isRed(sibling.right)) {
                        sibling.left.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotateRight(sibling);
                        sibling = parent.right;
                    }
                    // Case 4: Sibling's far child is red → rotate parent, recolor, done
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    sibling.right.color = Color.BLACK;
                    rotateLeft(parent);
                    node = root;
                }
            } else {
                Node<T> sibling = parent.left;

                // Case 1: Red sibling
                if (isRed(sibling)) {
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    rotateRight(parent);
                    sibling = parent.left;
                }
                // Case 2: Black sibling with two black children
                if (!isRed(sibling.left) && !isRed(sibling.right)) {
                    sibling.color = Color.RED;
                    node = parent;
                    parent = node.parent;
                } else {
                    // Case 3: Sibling's far child is black
                    if (!isRed(sibling.left)) {
                        sibling.right.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotateLeft(sibling);
                        sibling = parent.left;
                    }
                    // Case 4: Sibling's far child is red
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    sibling.left.color = Color.BLACK;
                    rotateRight(parent);
                    node = root;
                }
            }
        }
        if (node != null) {
            node.color = Color.BLACK;
        }
    }

    // =========================================================
    // 6. TRAVERSALS
    // =========================================================
    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        collectInOrder(root, result);
        return result;
    }

    public List<T> preOrder() {
        List<T> result = new ArrayList<>();
        collectPreOrder(root, result);
        return result;
    }

    public List<T> postOrder() {
        List<T> result = new ArrayList<>();
        collectPostOrder(root, result);
        return result;
    }

    /**
     * Level-order traversal.:
     *  Performs a breadth-first traversal of the tree, visiting each node exactly once.
     *  1. Create an empty queue.
     *  2. Enqueue the root node.
     *  3. While the queue is not empty:
     *      a. Dequeue a node from the queue.
     *      b. Visit the node.
     *      c. Enqueue the node's children.
     *  4. Return the list of visited nodes.
     * @return the values in breadth-first order
     */
    public List<T> levelOrder() {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll(); // dequeue
            result.add(node.data); // visit
            if (node.left != null) {
                queue.add(node.left); // enqueue left children
            }
            if (node.right != null) {
                queue.add(node.right); // enqueue right children
            }
        }
        return result;
    }

    private void collectPreOrder(Node<T> node, List<T> values) {
        if (node != null) {
            values.add(node.data);
            collectPreOrder(node.left, values);
            collectPreOrder(node.right, values);
        }
    }

    private void collectInOrder(Node<T> node, List<T> values) {
        if (node != null) {
            collectInOrder(node.left, values);
            values.add(node.data);
            collectInOrder(node.right, values);
        }
    }

    private void collectPostOrder(Node<T> node, List<T> values) {
        if (node != null) {
            collectPostOrder(node.left, values);
            collectPostOrder(node.right, values);
            values.add(node.data);
        }
    }

    // =========================================================
    // 7. HEIGHT AND DEPTH
    //    Both are measured in edges:
    //    height = longest path from a node down to a leaf (empty tree = -1, single node = 0)
    //    depth  = path length from the root down to a node (root = 0)
    //    Red-Black guarantee: height <= 2 * log2(n + 1)
    // =========================================================
    /**
     * Returns the height of the tree.
     * @return the number of edges on the longest root-to-leaf path, or -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) {
            return -1; // so that a leaf gets height 0
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Returns the depth of a value: the number of edges from the root to its node.
     * With duplicates this is the shallowest occurrence, because every other equal
     * value lies in the subtree of the first one found on the search path.
     * @return the depth of the value, or -1 if it is not in the tree
     */
    public int depth(T data) {
        Objects.requireNonNull(data, "null values are not allowed");
        int depth = 0;
        Node<T> current = root;
        while (current != null) {
            int cmp = data.compareTo(current.data);
            if (cmp == 0) {
                return depth;
            }
            current = cmp < 0 ? current.left : current.right;
            depth++;
        }
        return -1;
    }

    private void validate() {
        if (root == null) {
            return; // an empty tree is valid
        }
        if (root.color != Color.BLACK || root.parent != null) {
            throw new IllegalStateException("Root must be black and have no parent");
        }

        validateNode(root);

        List<T> values = new ArrayList<>();
        collectInOrder(root, values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i - 1).compareTo(values.get(i)) > 0) {
                throw new IllegalStateException("Tree is not in binary-search order");
            }
        }
    }

    private int validateNode(Node<T> node) {
        if (node == null) {
            return 1;
        }
        if (node.left != null && node.left.parent != node
                || node.right != null && node.right.parent != node) {
            throw new IllegalStateException("Incorrect parent link");
        }
        if (node.color == Color.RED
                && (isRed(node.left) || isRed(node.right))) {
            throw new IllegalStateException("A red node cannot have a red child");
        }

        int leftBlackHeight = validateNode(node.left);
        int rightBlackHeight = validateNode(node.right);
        if (leftBlackHeight != rightBlackHeight) {
            throw new IllegalStateException("Black heights differ");
        }
        return leftBlackHeight + (node.color == Color.BLACK ? 1 : 0);
    }

    private boolean isRed(Node<T> node) {
        return node != null && node.color == Color.RED;
    }

    private static class Block {
        final List<String> lines;
        final int width;
        final int center;

        Block(List<String> lines, int width, int center) {
            this.lines = lines;
            this.width = width;
            this.center = center;
        }
    }

    // Draws the tree top-down with / and \ connectors
    private void printTree() {
        if (root == null) {
            System.out.println("  (empty)");
            return;
        }
        for (String line : buildBlock(root).lines) {
            System.out.println("  " + line.stripTrailing());
        }
    }

    private Block buildBlock(Node<T> node) {
        String label = node.data + "(" + node.color.name().charAt(0) + ")";
        if (node.left == null && node.right == null) {
            return new Block(List.of(label), label.length(), label.length() / 2);
        }

        Block left = node.left == null ? null : buildBlock(node.left);
        Block right = node.right == null ? null : buildBlock(node.right);

        // Child centers and the child area, relative to the child area's origin
        int leftCenter = -1;
        int rightCenter = -1;
        int childWidth;
        int parentCenter;
        List<String> childLines = new ArrayList<>();
        if (left != null && right != null) {
            leftCenter = left.center;
            rightCenter = left.width + 1 + right.center;
            parentCenter = (leftCenter + rightCenter) / 2;
            childWidth = left.width + 1 + right.width;
            int rows = Math.max(left.lines.size(), right.lines.size());
            for (int i = 0; i < rows; i++) {
                String l = i < left.lines.size() ? left.lines.get(i) : "";
                String r = i < right.lines.size() ? right.lines.get(i) : "";
                childLines.add(String.format("%-" + left.width + "s %s", l, r));
            }
        } else if (left != null) {
            leftCenter = left.center;
            parentCenter = leftCenter + 2;
            childWidth = left.width;
            childLines.addAll(left.lines);
        } else {
            rightCenter = right.center;
            parentCenter = rightCenter - 2;
            childWidth = right.width;
            childLines.addAll(right.lines);
        }

        // Shift everything right if the label (or the parent) would start left of column 0
        int shift = Math.max(0, label.length() / 2 - parentCenter);
        parentCenter += shift;
        int width = Math.max(childWidth + shift, parentCenter - label.length() / 2 + label.length());

        char[] connectors = new char[width];
        Arrays.fill(connectors, ' ');
        if (leftCenter >= 0) {
            connectors[(leftCenter + shift + parentCenter) / 2] = '/';
        }
        if (rightCenter >= 0) {
            connectors[(rightCenter + shift + parentCenter + 1) / 2] = '\\';
        }

        List<String> lines = new ArrayList<>();
        lines.add(" ".repeat(parentCenter - label.length() / 2) + label);
        lines.add(new String(connectors));
        for (String childLine : childLines) {
            lines.add(" ".repeat(shift) + childLine);
        }
        return new Block(lines, width, parentCenter);
    }

    private void printLevelOrder() {
        if (root == null) {
            return;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < size; i++) {
                Node<T> node = queue.poll();
                line.append(node.data).append('(').append(node.color.name().charAt(0)).append(") ");
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            System.out.printf("  Level %d: %s%n", level++, line.toString().trim());
        }
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {10, 20, 30},                 // Right-right rotation
                {30, 20, 10},                 // Left-left rotation
                {30, 10, 20},                 // Left-right rotation
                {10, 30, 20},                 // Right-left rotation
                {10, 5, 15, 1, 7, 12, 18, 0} // Recoloring, including propagation
        };

        for (int i = 0; i < testCases.length; i++) {
            RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>();
            for (int value : testCases[i]) {
                tree.insert(value);
                tree.validate();
            }

            System.out.printf("Test %d:%n", i + 1);
            tree.printLevelOrder();
            tree.printTree();
        }

        // Traversals, search and delete on a larger tree
        RedBlackBalancedBST<Integer> tree = new RedBlackBalancedBST<>();
        for (int value : new int[]{20, 10, 30, 5, 15, 25, 35, 1, 7, 12, 18}) {
            tree.insert(value);
        }
        System.out.println("Traversals:");
        System.out.println("  In-order   : " + tree.inOrder());
        System.out.println("  Pre-order  : " + tree.preOrder());
        System.out.println("  Post-order : " + tree.postOrder());
        System.out.println("  Level-order: " + tree.levelOrder());
        System.out.println("  contains(15)=" + tree.contains(15) + ", contains(99)=" + tree.contains(99));
        System.out.println("  height()=" + tree.height() + ", depth(20)=" + tree.depth(20)
                + ", depth(12)=" + tree.depth(12) + ", depth(99)=" + tree.depth(99));
        tree.printTree();

        // red leaf, black node with children, node with two children, root, missing value
        for (int value : new int[]{1, 10, 20, 30, 99}) {
            boolean deleted = tree.delete(value);
            tree.validate();
            System.out.printf("delete(%d) -> %b, in-order: %s%n", value, deleted, tree.inOrder());
            tree.printTree();
        }
    }

}
