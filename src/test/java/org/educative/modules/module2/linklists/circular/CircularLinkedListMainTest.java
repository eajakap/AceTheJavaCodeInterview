package org.educative.modules.module2.linklists.circular;

import org.educative.modules.module2.linklists.CircularLinkedListMain;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularLinkedListMainTest {
    private static final String LIST_CLASS_NAME =
            "org.educative.modules.module2.linklists.CircularLinkedListMain$CircularLinkedList";
    private static final String NODE_CLASS_NAME =
            "org.educative.modules.module2.linklists.CircularLinkedListMain$ListNode";

    @Test
    void insertAndDeleteOperationsWorkForTypicalPositions() throws Exception {
        Object list = newList();

        invoke(list, "insertAtEnd", 10);
        invoke(list, "insertAtEnd", 30);
        invoke(list, "insertAtPosition", 20, 1);
        invoke(list, "insertAfter", 20, 25);
        invoke(list, "insertAtBeginning", 5);

        assertEquals(List.of(5, 10, 20, 25, 30), snapshot(list, 10));
        assertEquals(5, invoke(list, "count"));
        assertEquals(30, invoke(list, "last"));
        assertTrue((Boolean) invoke(list, "deleteByValue", 25));
        assertTrue((Boolean) invoke(list, "deleteByPosition", 1));
        assertTrue((Boolean) invoke(list, "deleteFromBeginning"));
        assertTrue((Boolean) invoke(list, "deleteFromEnd"));
        assertFalse((Boolean) invoke(list, "deleteByValue", 999));
        assertEquals(List.of(20), snapshot(list, 10));
    }

    @Test
    void operationsReportExpectedErrorsForInvalidInputs() throws Exception {
        Object list = newList();

        assertThrows(IllegalStateException.class, () -> invoke(list, "deleteFromBeginning"));
        assertThrows(IllegalStateException.class, () -> invoke(list, "deleteFromEnd"));
        assertThrows(IllegalStateException.class, () -> invoke(list, "deleteByValue", 1));
        assertThrows(IllegalStateException.class, () -> invoke(list, "last"));
        assertThrows(IndexOutOfBoundsException.class, () -> invoke(list, "insertAtPosition", 10, -1));

        invoke(list, "insertAtEnd", 1);
        invoke(list, "insertAtEnd", 2);
        assertThrows(IndexOutOfBoundsException.class, () -> invoke(list, "insertAtPosition", 3, 5));
    }

    @Test
    void traverseAndMainPrintExpectedText() throws Exception {
        Object list = newList();
        invoke(list, "insertAtEnd", 10);
        invoke(list, "insertAtEnd", 20);
        invoke(list, "insertAtEnd", 30);

        String traversed = captureOutput(() -> invoke(list, "traverse"));
        assertTrue(traversed.contains("10 -> 20 -> 30 -> 10 -> back to head"));

        String printed = captureOutput(() -> CircularLinkedListMain.main(new String[0]));
        assertTrue(printed.contains("Head node value: 10"));
        assertTrue(printed.contains("Does third.next point back to first? true"));
        assertTrue(printed.contains("Deleting the node with value 20:"));
    }

    @Test
    void insertAtBeginningOnEmptyListShouldCreateSingleNodeCycle() throws Exception {
        Object list = newList();

        invoke(list, "insertAtBeginning", 7);

        Object head = headNode(list);
        Object next = nextNode(head);
        assertSame(head, next);
        assertEquals(List.of(7), snapshot(list, 10));
    }

    @Test
    void existsOnEmptyListShouldReturnFalse() throws Exception {
        Object emptyList = newList();
        Boolean emptyResult = assertDoesNotThrow(() -> (Boolean) invoke(emptyList, "exists", 1));
        assertFalse(emptyResult);
    }

    @Test
    void existsShouldReturnTrueForSingleNodeList() throws Exception {
        Object singleNodeList = newList();
        invoke(singleNodeList, "insertAtEnd", 42);
        assertTrue((Boolean) invoke(singleNodeList, "exists", 42));
    }

    @Test
    void existsShouldFindValueStoredAtTail() throws Exception {
        Object multiNodeList = newList();
        invoke(multiNodeList, "insertAtEnd", 10);
        invoke(multiNodeList, "insertAtEnd", 20);
        invoke(multiNodeList, "insertAtEnd", 30);
        assertTrue((Boolean) invoke(multiNodeList, "exists", 30));
    }

    @Test
    void deletingHeadByValueShouldPreserveCircularStructure() throws Exception {
        Object list = newList();
        invoke(list, "insertAtEnd", 10);
        invoke(list, "insertAtEnd", 20);
        invoke(list, "insertAtEnd", 30);

        assertTrue((Boolean) invoke(list, "deleteByValue", 10));

        assertEquals(List.of(20, 30), snapshot(list, 10));
        assertSame(headNode(list), nextNode(nextNode(headNode(list))));
    }

    @Test
    void deleteByPositionShouldRejectOutOfRangeIndexes() throws Exception {
        Object list = newList();
        invoke(list, "insertAtEnd", 1);
        invoke(list, "insertAtEnd", 2);
        invoke(list, "insertAtEnd", 3);

        assertThrows(IllegalArgumentException.class, () -> invoke(list, "deleteByPosition", 5));
    }

    @Test
    void toListShouldReturnFiniteTraversalOrder() throws Exception {
        Object list = newList();
        invoke(list, "insertAtEnd", 1);
        invoke(list, "insertAtEnd", 2);
        invoke(list, "insertAtEnd", 3);

        @SuppressWarnings("unchecked")
        List<Integer> values = assertTimeoutPreemptively(
                Duration.ofMillis(250),
                () -> (List<Integer>) invoke(list, "toList")
        );
        assertEquals(List.of(1, 2, 3), values);
    }

    private static Object newList() throws Exception {
        Class<?> listClass = Class.forName(LIST_CLASS_NAME);
        var constructor = listClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object invoke(Object target, String methodName, Object... args) throws Exception {
        Method method = findMethod(target.getClass(), methodName, args.length);
        method.setAccessible(true);
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof Exception runtimeLike) {
                throw runtimeLike;
            }
            throw exception;
        }
    }

    private static Method findMethod(Class<?> type, String methodName, int argumentCount) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == argumentCount) {
                return method;
            }
        }
        throw new IllegalArgumentException("Method not found: " + methodName);
    }

    private static Object headNode(Object list) throws Exception {
        Field headField = list.getClass().getDeclaredField("head");
        headField.setAccessible(true);
        return headField.get(list);
    }

    private static Object nextNode(Object node) throws Exception {
        Field nextField = Class.forName(NODE_CLASS_NAME).getDeclaredField("next");
        nextField.setAccessible(true);
        return nextField.get(node);
    }

    private static int data(Object node) throws Exception {
        Field dataField = Class.forName(NODE_CLASS_NAME).getDeclaredField("data");
        dataField.setAccessible(true);
        return dataField.getInt(node);
    }

    private static List<Integer> snapshot(Object list, int maxNodes) throws Exception {
        List<Integer> values = new ArrayList<>();
        Object current = headNode(list);
        Map<Object, Boolean> seen = new IdentityHashMap<>();
        while (current != null && !seen.containsKey(current) && values.size() < maxNodes) {
            seen.put(current, Boolean.TRUE);
            values.add(data(current));
            current = nextNode(current);
        }
        return values;
    }

    private static String captureOutput(ThrowingRunnable action) throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
