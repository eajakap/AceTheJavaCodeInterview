package org.educative.modules.module2.trees;

import java.util.LinkedList;
import java.util.Queue;

public class SameTreeSolution {

    // Definition of a binary tree node class
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

    private static class Solution {

        public static boolean isSameTree(TreeNode<Integer> p, TreeNode<Integer> q) {
            // Base case: both nodes are null
            if (p == null && q == null) {
                return true;
            }
            // If one of the nodes is null, trees are not the same
            if (p == null || q == null) {
                return false;
            }
            // If the values of the nodes are different, trees are not the same
            if (!p.data.equals(q.data)) {
                return false;
            }
            // Recursively check left and right subtrees
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }

        public TreeNode<Integer> buildTree(Integer[] values) {
            if (values == null || values.length == 0) return null;

            TreeNode<Integer> root = new TreeNode<>(values[0]);
            Queue<TreeNode<Integer>> queue = new LinkedList<>();
            queue.add(root);
            int i = 1;

            while (!queue.isEmpty() && i < values.length) {
                TreeNode<Integer> node = queue.poll();

                if (i < values.length && values[i] != null) {
                    node.left = new TreeNode<>(values[i]);
                    queue.add(node.left);
                }
                i++;

                if (i < values.length && values[i] != null) {
                    node.right = new TreeNode<>(values[i]);
                    queue.add(node.right);
                }
                i++;
            }
            return root;
        }

        public static void main(String[] args) {
            Solution solution = new Solution();  // instance created to call non-static methods

            Object[][] testCases = {
                    new Object[]{new Integer[]{1},                    new Integer[]{1},                  "[1]",               "[1]"},
                    new Object[]{new Integer[]{},                     new Integer[]{},                   "[]",                "[]"},
                    new Object[]{new Integer[]{5, 3, 7, 1, 4},       new Integer[]{5, 3, 7, 1, 4},      "[5,3,7,1,4]",       "[5,3,7,1,4]"},
                    new Object[]{new Integer[]{1, 2, 3},              new Integer[]{1, 2, 4},             "[1,2,3]",           "[1,2,4]"},
                    new Object[]{new Integer[]{10, -5, 20, null, 8}, new Integer[]{10, -5, 20, null, 9}, "[10,-5,20,null,8]", "[10,-5,20,null,9]"},
            };

            int idx = 1;
            for (Object[] tc : testCases) {
                Integer[] pList = (Integer[]) tc[0];
                Integer[] qList = (Integer[]) tc[1];
                String pStr     = (String)    tc[2];
                String qStr     = (String)    tc[3];

                TreeNode<Integer> pTree = solution.buildTree(pList);
                TreeNode<Integer> qTree = solution.buildTree(qList);

                boolean result = solution.isSameTree(pTree, qTree);

                System.out.println(idx + ".\tInput tree p: " + pStr);
                System.out.println("\tInput tree q: " + qStr);
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
                idx++;
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
