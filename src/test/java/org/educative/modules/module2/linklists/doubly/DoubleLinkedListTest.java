package org.educative.modules.module2.linklists.doubly;

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

class DoubleLinkedListTest {
    private static final String DOUBLY_LINKED_LIST_CLASS_NAME =
            "org.educative.modules.module2.linklists.doubly.DoubleLinkedList$DoublyLinkedList";

    @Test
    void insertionsBuildExpectedOrderAndBidirectionalLinks() throws Throwable {
        Object list = newInstance();

        invoke(list, "insertAtBeginning", int.class, 20);
        invoke(list, "insertAtBeginning", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 30);
        invoke(list, "insertAfterValue", int.class, 20, int.class, 25);
        invoke(list, "insertAtPosition", int.class, 27, int.class, 4);

        assertEquals(List.of(10, 20, 25, 30, 27), toList(list));
        assertEquals(5, invoke(list, "count"));
        assertTrue((boolean) invoke(list, "exists", int.class, 25));
        assertFalse((boolean) invoke(list, "exists", int.class, 99));
        assertEquals(27, invoke(list, "last"));

        Object head = getField(list, "head");
        assertEquals(10, getNodeData(head));
        Object second = getField(head, "next");
        Object third = getField(second, "next");
        assertEquals(10, getNodeData(getField(second, "prev")));
        assertEquals(20, getNodeData(getField(third, "prev")));
        assertEquals(25, getNodeData(third));
    }

    @Test
    void insertAfterValueSkipsWhenTargetIsMissing() throws Throwable {
        Object list = newInstance();
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);

        invoke(list, "insertAfterValue", int.class, 99, int.class, 30);

        assertEquals(List.of(10, 20), toList(list));
    }

    @Test
    void deleteFromBeginningAndDeleteByValueHandleHeadTailAndMissingValues() throws Throwable {
        Object list = newInstance();
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);
        invoke(list, "insertAtEnd", int.class, 20);

        assertTrue((boolean) invoke(list, "deleteFromBeginning"));
        assertEquals(List.of(20, 30, 20), toList(list));

        invoke(list, "deleteByValue", int.class, 20);
        assertEquals(List.of(30, 20), toList(list));

        invoke(list, "deleteByValue", int.class, 20);
        assertEquals(List.of(30), toList(list));

        invoke(list, "deleteByValue", int.class, 99);
        assertEquals(List.of(30), toList(list));
    }

    @Test
    void deleteAtPositionShouldRemoveRequestedMiddleNode() throws Throwable {
        Object list = newInstance();
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);

        assertTrue((boolean) invoke(list, "deleteAtPosition", int.class, 1));

        assertEquals(List.of(10, 30), toList(list));
    }

    @Test
    void doublyLinkedListValidatesEmptyAndOutOfRangeOperations() throws Throwable {
        Object emptyList = newInstance();

        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "deleteFromBeginning"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "deleteAtPosition", int.class, 0));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "traverseBackward"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "last"));

        Object populatedList = newInstance();
        invoke(populatedList, "insertAtEnd", int.class, 10);
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteAtPosition", int.class, -1));
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteAtPosition", int.class, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> invoke(populatedList, "insertAtPosition", int.class, 20, int.class, 5));
    }

    @Test
    void traverseForwardPrintsValuesInOrder() throws Throwable {
        Object list = newInstance();
        invoke(list, "insertAtEnd", int.class, 1);
        invoke(list, "insertAtEnd", int.class, 2);
        invoke(list, "insertAtEnd", int.class, 3);

        String output = captureOutput(() -> invoke(list, "traverseForward"));

        assertEquals("1 -> 2 -> 3 -> Reached front --> end" + System.lineSeparator(), output);
    }

    @Test
    void traverseBackwardIncludesReverseTraversal() throws Throwable {
        Object list = newInstance();
        invoke(list, "insertAtEnd", int.class, 1);
        invoke(list, "insertAtEnd", int.class, 2);
        invoke(list, "insertAtEnd", int.class, 3);

        String output = captureOutput(() -> invoke(list, "traverseBackward"));

        assertTrue(output.contains("3 <-- 2 <-- 1 <-- Reached end <-- front"));
    }

    @Test
    void mainPrintsTheDemonstrationValues() {
        String output = captureOutputUnchecked(() -> DoubleLinkedList.main(new String[0]));

        assertTrue(output.contains("First node value: 10"));
        assertTrue(output.contains("Forward traversal: 10 -> 20 -> 30 -> Reached front --> end"));
        assertTrue(output.contains("Delete by value (25):"));
    }

    private static Object newInstance() throws Throwable {
        Class<?> clazz = Class.forName(DOUBLY_LINKED_LIST_CLASS_NAME);
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

    private static List<Integer> toList(Object list) throws Throwable {
        @SuppressWarnings("unchecked")
        List<Integer> values = (List<Integer>) invoke(list, "toList");
        return values;
    }

    private static Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    private static int getNodeData(Object node) throws Exception {
        Field dataField = node.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        return dataField.getInt(node);
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
}
