package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AVLTreeMainTest {
    @Test
    void insertBalancesAllRotationCasesIncludingDuplicatesOnTheRight() throws Exception {
        Object llTree = newAvlTree();
        Object llRoot = null;
        llRoot = insert(llTree, llRoot, 30);
        llRoot = insert(llTree, llRoot, 20);
        llRoot = insert(llTree, llRoot, 10);
        assertBalancedThreeNodeTree(llRoot);

        Object rrTree = newAvlTree();
        Object rrRoot = null;
        rrRoot = insert(rrTree, rrRoot, 10);
        rrRoot = insert(rrTree, rrRoot, 20);
        rrRoot = insert(rrTree, rrRoot, 30);
        assertBalancedThreeNodeTree(rrRoot);

        Object lrTree = newAvlTree();
        Object lrRoot = null;
        lrRoot = insert(lrTree, lrRoot, 30);
        lrRoot = insert(lrTree, lrRoot, 10);
        lrRoot = insert(lrTree, lrRoot, 20);
        assertBalancedThreeNodeTree(lrRoot);

        Object rlTree = newAvlTree();
        Object rlRoot = null;
        rlRoot = insert(rlTree, rlRoot, 10);
        rlRoot = insert(rlTree, rlRoot, 30);
        rlRoot = insert(rlTree, rlRoot, 20);
        assertBalancedThreeNodeTree(rlRoot);

        Object duplicateTree = newAvlTree();
        Object duplicateRoot = null;
        duplicateRoot = insert(duplicateTree, duplicateRoot, 10);
        duplicateRoot = insert(duplicateTree, duplicateRoot, 10);
        duplicateRoot = insert(duplicateTree, duplicateRoot, 10);
        assertEquals(10, dataOf(duplicateRoot));
        assertEquals(10, dataOf(leftOf(duplicateRoot)));
        assertEquals(10, dataOf(rightOf(duplicateRoot)));
        assertEquals(2, getHeight(duplicateTree, duplicateRoot));
        assertTrue(Math.abs(getBalance(duplicateTree, duplicateRoot)) <= 1);
    }

    @Test
    void rotationsDeletionTraversalsAndDisplayTreeWork() throws Exception {
        Object tree = newAvlTree();

        Object rotateRightRoot = newNode(30);
        setLeft(rotateRightRoot, newNode(20));
        setRight(leftOf(rotateRightRoot), newNode(25));
        updateHeights(tree, rotateRightRoot, leftOf(rotateRightRoot));
        Object rightRotated = invokeNodeMethod(tree, "rightRotate", rotateRightRoot);
        assertEquals(20, dataOf(rightRotated));
        assertEquals(30, dataOf(rightOf(rightRotated)));
        assertEquals(25, dataOf(leftOf(rightOf(rightRotated))));

        Object rotateLeftRoot = newNode(10);
        setRight(rotateLeftRoot, newNode(20));
        setLeft(rightOf(rotateLeftRoot), newNode(15));
        updateHeights(tree, rotateLeftRoot, rightOf(rotateLeftRoot));
        Object leftRotated = invokeNodeMethod(tree, "leftRotate", rotateLeftRoot);
        assertEquals(20, dataOf(leftRotated));
        assertEquals(10, dataOf(leftOf(leftRotated)));
        assertEquals(15, dataOf(rightOf(leftOf(leftRotated))));

        Object root = null;
        for (int value : new int[]{20, 10, 30, 5, 15, 25, 35}) {
            root = insert(tree, root, value);
        }

        assertEquals(0, getHeight(tree, null));
        assertEquals(0, getBalance(tree, null));
        assertNull(invokeNodeMethod(tree, "getMinValueNode", null));
        assertEquals(25, dataOf(invokeNodeMethod(tree, "getMinValueNode", rightOf(root))));
        Object initialRoot = root;
        assertEquals("5 10 15 20 25 30 35", captureOutput(() -> invokeVoidNodeMethod(tree, "inorder", initialRoot)));
        assertEquals("20 10 5 15 30 25 35", captureOutput(() -> invokeVoidNodeMethod(tree, "preorder", initialRoot)));
        assertEquals("null", captureOutput(() -> invokeDisplayTree(null)));

        root = delete(tree, root, 5);
        root = delete(tree, root, 30);
        Object finalRoot = root;
        assertEquals("10 15 20 25 35", captureOutput(() -> invokeVoidNodeMethod(tree, "inorder", finalRoot)));
        assertTrue(Math.abs(getBalance(tree, root)) <= 1);
    }

    @Test
    void mainPrintsItsDemonstrationSections() {
        String output = captureOutput(() -> AVLTreeMain.main(new String[0]));

        assertTrue(output.contains("Tree after insertion:"));
        assertTrue(output.contains("Tree after deleting 40:"));
    }

    private static Object newAvlTree() throws Exception {
        Constructor<?> constructor = Class.forName("org.educative.modules.module2.trees.AVLTreeMain$AVLTree")
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object newNode(int value) throws Exception {
        Constructor<?> constructor = Class.forName("org.educative.modules.module2.trees.AVLTreeMain$Node")
                .getDeclaredConstructor(int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Object insert(Object tree, Object node, int value) throws Exception {
        return invokeNodeMethod(tree, "insert", node, value);
    }

    private static Object delete(Object tree, Object node, int value) throws Exception {
        return invokeNodeMethod(tree, "delete", node, value);
    }

    private static int getHeight(Object tree, Object node) throws Exception {
        Method method = tree.getClass().getDeclaredMethod("getHeight", nodeClass());
        method.setAccessible(true);
        return (int) method.invoke(tree, node);
    }

    private static int getBalance(Object tree, Object node) throws Exception {
        Method method = tree.getClass().getDeclaredMethod("getBalance", nodeClass());
        method.setAccessible(true);
        return (int) method.invoke(tree, node);
    }

    private static Object invokeNodeMethod(Object tree, String name, Object node) throws Exception {
        Method method = tree.getClass().getDeclaredMethod(name, nodeClass());
        method.setAccessible(true);
        return method.invoke(tree, node);
    }

    private static Object invokeNodeMethod(Object tree, String name, Object node, int value) throws Exception {
        Method method = tree.getClass().getDeclaredMethod(name, nodeClass(), int.class);
        method.setAccessible(true);
        return method.invoke(tree, node, value);
    }

    private static void invokeVoidNodeMethod(Object tree, String name, Object node) {
        try {
            Method method = tree.getClass().getDeclaredMethod(name, nodeClass());
            method.setAccessible(true);
            method.invoke(tree, node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeDisplayTree(Object node) {
        try {
            Method method = Class.forName("org.educative.modules.module2.trees.AVLTreeMain$PrintAVLTree")
                    .getDeclaredMethod("displayTree", nodeClass());
            method.setAccessible(true);
            method.invoke(null, node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.AVLTreeMain$Node");
    }

    private static int dataOf(Object node) throws Exception {
        Field field = nodeClass().getDeclaredField("data");
        field.setAccessible(true);
        return (int) field.get(node);
    }

    private static Object leftOf(Object node) throws Exception {
        Field field = nodeClass().getDeclaredField("left");
        field.setAccessible(true);
        return field.get(node);
    }

    private static Object rightOf(Object node) throws Exception {
        Field field = nodeClass().getDeclaredField("right");
        field.setAccessible(true);
        return field.get(node);
    }

    private static void setLeft(Object node, Object child) throws Exception {
        Field field = nodeClass().getDeclaredField("left");
        field.setAccessible(true);
        field.set(node, child);
    }

    private static void setRight(Object node, Object child) throws Exception {
        Field field = nodeClass().getDeclaredField("right");
        field.setAccessible(true);
        field.set(node, child);
    }

    private static void updateHeights(Object tree, Object... nodes) throws Exception {
        Field height = nodeClass().getDeclaredField("height");
        height.setAccessible(true);
        for (Object node : nodes) {
            height.setInt(node, 1 + Math.max(getHeight(tree, leftOf(node)), getHeight(tree, rightOf(node))));
        }
    }

    private static void assertBalancedThreeNodeTree(Object root) throws Exception {
        assertEquals(20, dataOf(root));
        assertEquals(10, dataOf(leftOf(root)));
        assertEquals(30, dataOf(rightOf(root)));
        assertEquals(2, getHeight(newAvlTree(), root));
    }

    private static String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").trim();
    }
}
