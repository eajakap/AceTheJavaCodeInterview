package org.educative.modules.module2.linklists.reverse;

import org.educative.modules.module2.linklists.ReverseLinkedList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReverseLinkedListTest {
    private static final String LINKED_LIST_CLASS_NAME =
            "org.educative.modules.module2.linklists.ReverseLinkedList$LinkedList";
    private static final String LINKED_LIST_WITH_TAIL_CLASS_NAME =
            "org.educative.modules.module2.linklists.ReverseLinkedList$LinkedListWithTail";
    private static final String LIST_NODE_CLASS_NAME =
            "org.educative.modules.module2.linklists.ReverseLinkedList$ListNode";

    @Test
    void linkedListInsertionsAndQueriesWork() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME);

        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtBeginning", int.class, 10);
        invoke(list, "insertAtPosition", int.class, 15, int.class, 1);
        invoke(list, "insertAtEnd", int.class, 25);

        assertEquals(List.of(10, 15, 20, 25), toList(list));
        assertEquals(4, invoke(list, "count"));
        assertTrue((boolean) invoke(list, "exists", int.class, 15));
        assertFalse((boolean) invoke(list, "exists", int.class, 99));
        assertEquals(25, invoke(list, "last"));
    }

    @Test
    void linkedListDeleteOperationsAndBoundsWork() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 10));
        assertEquals(List.of(20, 20, 30), toList(list));

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 20));
        assertEquals(List.of(20, 30), toList(list));

        assertTrue((boolean) invoke(list, "deleteByPosition", int.class, 1));
        assertEquals(List.of(20), toList(list));

        assertFalse((boolean) invoke(list, "deleteByValue", int.class, 99));
        assertThrows(IllegalArgumentException.class, () -> invoke(list, "deleteByPosition", int.class, -1));
        assertThrows(IllegalArgumentException.class, () -> invoke(list, "deleteByPosition", int.class, 2));
    }

    @Test
    void linkedListReverseReturnsReversedOrder() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);

        Object reversedList = captureResult(() -> invoke(list, "reverse"));

        assertEquals(List.of(30, 20, 10), toList(reversedList));
    }

    @Test
    void linkedListReverseHandlesEmptyAndSingleNodeLists() throws Throwable {
        Object emptyList = newInstance(LINKED_LIST_CLASS_NAME);
        Object reversedEmpty = captureResult(() -> invoke(emptyList, "reverse"));
        assertEquals(List.of(), toList(reversedEmpty));

        Object singleNodeList = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(singleNodeList, "insertAtEnd", int.class, 42);
        Object reversedSingle = captureResult(() -> invoke(singleNodeList, "reverse"));
        assertEquals(List.of(42), toList(reversedSingle));
    }

    @Test
    void linkedListTraversePrintsValuesInOrder() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 1);
        invoke(list, "insertAtEnd", int.class, 2);

        String output = captureOutput(() -> invoke(list, "traverse"));

        assertEquals("1 -> 2 -> null" + System.lineSeparator(), output);
    }

    @Test
    void middleNodeReturnsMiddleForOddAndSecondMiddleForEvenLength() throws Throwable {
        Object oddList = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(oddList, "insertAtEnd", int.class, 10);
        invoke(oddList, "insertAtEnd", int.class, 20);
        invoke(oddList, "insertAtEnd", int.class, 30);
        invoke(oddList, "insertAtEnd", int.class, 40);
        invoke(oddList, "insertAtEnd", int.class, 50);

        Object oddMiddle = invokeStatic(
                linkedListClass(),
                "middleNode",
                new Class<?>[]{listNodeClass()},
                new Object[]{getHead(oddList)}
        );
        assertEquals(30, getNodeData(oddMiddle));

        Object evenList = newInstance(LINKED_LIST_CLASS_NAME);
        invoke(evenList, "insertAtEnd", int.class, 1);
        invoke(evenList, "insertAtEnd", int.class, 2);
        invoke(evenList, "insertAtEnd", int.class, 3);
        invoke(evenList, "insertAtEnd", int.class, 4);

        Object evenMiddle = invokeStatic(
                linkedListClass(),
                "middleNode",
                new Class<?>[]{listNodeClass()},
                new Object[]{getHead(evenList)}
        );
        assertEquals(3, getNodeData(evenMiddle));
    }

    @Test
    void linkedListValidatesEmptyOperations() throws Throwable {
        Object emptyList = newInstance(LINKED_LIST_CLASS_NAME);

        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "deleteAtBegin"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "last"));
        assertThrows(IndexOutOfBoundsException.class, () -> invoke(emptyList, "insertAtPosition", int.class, 5, int.class, 1));
    }

    @Test
    void linkedListWithTailInsertionsAndDeletesMaintainTail() throws Throwable {
        Object list = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);

        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtBegin", int.class, 10);
        invoke(list, "insertAtPosition", int.class, 15, int.class, 1);
        invoke(list, "insertAtEnd", int.class, 25);

        assertEquals(List.of(10, 15, 20, 25), toList(list));
        assertEquals(25, invoke(list, "last"));

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 25));
        assertTrue((boolean) invoke(list, "deleteByPosition", int.class, 1));
        assertTrue((boolean) invoke(list, "deleteAtBegin"));

        assertEquals(List.of(20), toList(list));
        assertEquals(20, invoke(list, "last"));
    }

    @Test
    void linkedListWithTailReverseShouldReverseList() throws Throwable {
        Object list = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);

        captureOutput(() -> invoke(list, "reverse"));

        assertEquals(List.of(30, 20, 10), toList(list));
    }

    @Test
    void mainPrintsTheDemonstrationValues() {
        String output = captureOutputUnchecked(() -> ReverseLinkedList.main(new String[0]));

        assertTrue(output.contains("<<<<<<<<<<<<<<<< LinkedList operations >>>>>>>>>>>>>>>>"));
        assertTrue(output.contains("Traversing the Reversed linked list: 30 -> 20 -> 10 -> null"));
        assertTrue(output.contains("<<<<<<<<<<<<<<<< LinkedListWithTail operations >>>>>>>>>>>>>>>>"));
    }

    private static Object newInstance(String className) throws Throwable {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static Object invoke(Object target, String methodName, Object... typedArgs) throws Throwable {
        int argumentCount = typedArgs.length / 2;
        Class<?>[] parameterTypes = new Class<?>[argumentCount];
        Object[] arguments = new Object[argumentCount];
        for (int i = 0; i < argumentCount; i++) {
            parameterTypes[i] = (Class<?>) typedArgs[i * 2];
            arguments[i] = typedArgs[i * 2 + 1];
        }

        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return method.invoke(target, arguments);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static Object invokeStatic(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return method.invoke(null, arguments);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static List<Integer> toList(Object list) throws Throwable {
        @SuppressWarnings("unchecked")
        List<Integer> values = (List<Integer>) invoke(list, "toList");
        return values;
    }

    private static Object getHead(Object list) throws Exception {
        Field field = list.getClass().getDeclaredField("head");
        field.setAccessible(true);
        return field.get(list);
    }

    private static int getNodeData(Object node) throws Exception {
        Field dataField = node.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        return dataField.getInt(node);
    }

    private static Class<?> linkedListClass() throws ClassNotFoundException {
        return Class.forName(LINKED_LIST_CLASS_NAME);
    }

    private static Class<?> listNodeClass() throws ClassNotFoundException {
        return Class.forName(LIST_NODE_CLASS_NAME);
    }

    private static String captureOutput(ThrowingRunnable action) throws Throwable {
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

    private static Object captureResult(ThrowingSupplier supplier) throws Throwable {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            return supplier.get();
        } finally {
            System.setOut(originalOut);
        }
    }

    private static String captureOutputUnchecked(Runnable action) {
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
        void run() throws Throwable;
    }

    @FunctionalInterface
    private interface ThrowingSupplier {
        Object get() throws Throwable;
    }
}
