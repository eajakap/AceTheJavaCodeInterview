package org.educative.modules.module2.trees;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidateBstTest {
    @Test
    void defaultConstructorAndDisplayTreeHandleEmptyTree() throws Exception {
        ValidateBst<Integer> tree = new ValidateBst<>();

        assertTrue(captureDisplay(tree, null).contains("null"));
    }

    @Test
    void listConstructorHandlesNullRootNode() throws Exception {
        ValidateBst<Integer> tree = newTree((Integer) null);

        assertNull(getRoot(tree));
    }

    @Test
    void isValidBSTReturnsTrueForValidTreesIncludingNegativeValues() throws Exception {
        ValidateBst<Integer> singleNodeTree = newTree(1);
        ValidateBst<Integer> validTree = newTree(0, -10, 10, -20, -5, 5, 20);

        assertTrue(isValidBST(getRoot(singleNodeTree)));
        assertTrue(isValidBST(getRoot(validTree)));
    }

    @Test
    void isValidBSTReturnsFalseForDeepViolationsAndDuplicates() throws Exception {
        ValidateBst<Integer> deepViolationTree = newTree(5, 1, 7, null, 4, 6, 3);
        ValidateBst<Integer> duplicateTree = newTree(2, 1, 2);

        assertFalse(isValidBST(getRoot(deepViolationTree)));
        assertFalse(isValidBST(getRoot(duplicateTree)));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String printed = captureOutput(() -> ValidateBst.main(new String[0]));

        assertTrue(printed.contains("1.\tInput tree:"));
        assertTrue(printed.contains("3.\tValid BST? false"));
        assertTrue(printed.contains("8.\tValid BST? false"));
    }

    private static ValidateBst<Integer> newTree(Integer... values) throws Exception {
        Constructor<ValidateBst> constructor = ValidateBst.class.getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        List<Object> nodes = new ArrayList<>();
        for (Integer value : values) {
            nodes.add(value == null ? null : newNode(value));
        }
        return constructor.newInstance(nodes);
    }

    private static Object newNode(Integer value) throws Exception {
        Constructor<?> constructor = nodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(value);
    }

    private static Object getRoot(ValidateBst<?> tree) throws Exception {
        Field field = ValidateBst.class.getDeclaredField("root");
        field.setAccessible(true);
        return field.get(tree);
    }

    private static boolean isValidBST(Object root) throws Exception {
        Method method = ValidateBst.class.getDeclaredMethod("isValidBST", nodeClass());
        method.setAccessible(true);
        return (Boolean) method.invoke(null, root);
    }

    private static String captureDisplay(ValidateBst<?> tree, Object root) throws Exception {
        Method method = ValidateBst.class.getDeclaredMethod("displayTree", nodeClass());
        method.setAccessible(true);
        return captureOutput(() -> {
            try {
                method.invoke(tree, root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Class<?> nodeClass() throws Exception {
        return Class.forName("org.educative.modules.module2.trees.ValidateBst$TreeNode");
    }

    private static String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }
}
