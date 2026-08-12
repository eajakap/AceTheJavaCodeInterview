package org.educative.modules.module2.trees;

import java.util.*;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    int height;

    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
/*
    Types of Rotation:
    1. Left Rotation (Single Rotation: LL Rotation: Left Rotation of the Right Subtree of its right child)
    2. Right Rotation (Single Rotation: RR Rotation: Right Rotation of the Left Subtree of its left child)
    3. Left-Right Rotation (Double Rotation: LR Rotation: Left Rotation followed by Right Rotation)
    4. Right-Left Rotation (Double Rotation: RL Rotation: Right Rotation followed by Left Rotation)
 */
class AVLTree {
    public int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public int getBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    public TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    public TreeNode insert(TreeNode node, int data) {
        if (node == null) {
            return new TreeNode(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else {
            node.right = insert(node.right, data);
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        // Left Left case
        if (balance > 1 && data < node.left.data) {
            return rightRotate(node);
        }

        // Right Right case
        if (balance < -1 && data > node.right.data) {
            return leftRotate(node);
        }

        // Left Right case
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left case
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public TreeNode getMinValueNode(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public TreeNode delete(TreeNode node, int data) {
        if (node == null) {
            return node;
        }

        if (data < node.data) {
            node.left = delete(node.left, data);
        } else if (data > node.data) {
            node.right = delete(node.right, data);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            TreeNode temp = getMinValueNode(node.right);
            node.data = temp.data;
            node.right = delete(node.right, temp.data);
        }

        if (node == null) {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        // Left Left case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void inorder(TreeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }

    public void preorder(TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    // --- Display tree methods ---
    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
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
}

public class AVLTreeMain {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        TreeNode root = null;

        int[] values = {10, 20, 30, 40, 50, 25};

        for (int value : values) {
            root = tree.insert(root, value);
        }

        System.out.println("Tree after insertion:");
        tree.displayTree(root);
        System.out.println();

        root = tree.delete(root, 40);

        System.out.println("Tree after deleting 40:");
        tree.displayTree(root);
        System.out.println();
    }
}