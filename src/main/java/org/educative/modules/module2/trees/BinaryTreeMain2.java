package org.educative.modules.module2.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeMain2 {

    static class TreeNode {
        String data;
        TreeNode left;
        TreeNode right;

        public TreeNode(String data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static class BinaryTree {
        TreeNode root;

        public BinaryTree(String rootValue) {
            this.root = rootValue != null ? new TreeNode(rootValue) : null;
        }

        public BinaryTree() {
            this.root = null;
        }

        public boolean search(TreeNode root, String target) {
            if (root == null) {
                return false;
            }

            if (root.data.equals(target)) {
                return true;
            }

            return search(root.left, target) || search(root.right, target);
        }

        public void insert(String value) {
            TreeNode newNode = new TreeNode(value);

            if (root == null) {
                root = newNode;
                return;
            }

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();

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

        public void delete(String target) {
            if (root == null) {
                return;
            }

            if (root.left == null && root.right == null) {
                if (root.data.equals(target)) {
                    root = null;
                }
                return;
            }

            Queue<TreeNode[]> queue = new LinkedList<>();
            queue.add(new TreeNode[]{root, null});

            TreeNode targetNode = null;
            TreeNode lastNode = null;
            TreeNode lastParent = null;

            while (!queue.isEmpty()) {
                TreeNode[] pair = queue.poll();
                TreeNode current = pair[0];
                TreeNode parent = pair[1];

                if (current.data.equals(target)) {
                    targetNode = current;
                }

                lastNode = current;
                lastParent = parent;

                if (current.left != null) {
                    queue.add(new TreeNode[]{current.left, current});
                }
                if (current.right != null) {
                    queue.add(new TreeNode[]{current.right, current});
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

    public static class Main {
        public static void main(String[] args) {
            BinaryTree bt = new BinaryTree("A");
            bt.insert("B");
            bt.insert("C");
            bt.insert("D");
            bt.insert("E");

            System.out.println("Initial tree");
            bt.displayTree(bt.root);

            System.out.println("Search for C: " + bt.search(bt.root, "C"));
            System.out.println("Search for X: " + bt.search(bt.root, "X"));

            bt.delete("B");
            System.out.println("After deleting B:");
            bt.displayTree(bt.root);

            bt.delete("A");
            System.out.println("After deleting A:");
            bt.displayTree(bt.root);
        }
    }
    public static void main(String[] args) {
        Main.main(args);
    }
}