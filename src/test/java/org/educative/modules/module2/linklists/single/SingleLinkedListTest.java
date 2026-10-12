package org.educative.modules.module2.linklists.single;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleLinkedListTest {
    private static final String LINKED_LIST_CLASS_NAME =
            "org.educative.modules.module2.linklists.single.SingleLinkedList$LinkedList";
    private static final String LINKED_LIST_WITH_TAIL_CLASS_NAME =
            "org.educative.modules.module2.linklists.single.SingleLinkedList$LinkedListWithTail";

    @Test
    void linkedListConstructorWithValuesBuildsExpectedList() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME, List.of(10, 20, 30));

        assertEquals(List.of(10, 20, 30), toList(list));
        assertEquals(3, invoke(list, "count"));
        assertTrue((boolean) invoke(list, "exists", int.class, 20));
        assertFalse((boolean) invoke(list, "exists", int.class, 99));
        assertEquals(30, invoke(list, "last"));
    }

    @Test
    void linkedListInsertionsHandleBeginningMiddleAndEnd() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME);

        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtBeginning", int.class, 10);
        invoke(list, "insertAtPosition", int.class, 15, int.class, 1);
        invoke(list, "insertAtEnd", int.class, 25);

        assertEquals(List.of(10, 15, 20, 25), toList(list));
    }

    @Test
    void linkedListDeletesHeadMiddleAndFirstDuplicate() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME, List.of(1, 2, 2, 3, 4));

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 1));
        assertEquals(List.of(2, 2, 3, 4), toList(list));

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 2));
        assertEquals(List.of(2, 3, 4), toList(list));

        assertTrue((boolean) invoke(list, "deleteByPosition", int.class, 1));
        assertEquals(List.of(2, 4), toList(list));

        assertFalse((boolean) invoke(list, "deleteByValue", int.class, 99));
    }

    @Test
    void linkedListValidatesEmptyAndOutOfRangeOperations() throws Throwable {
        Object emptyList = newInstance(LINKED_LIST_CLASS_NAME);

        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "deleteAtBegin"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "last"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "insertAtPosition", int.class, 5, int.class, 1));

        Object populatedList = newInstance(LINKED_LIST_CLASS_NAME, List.of(10, 20));
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteByPosition", int.class, -1));
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteByPosition", int.class, 5));
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "insertAtPosition", int.class, 30, int.class, 5));
    }

    @Test
    void linkedListTraversePrintsValuesInOrder() throws Throwable {
        Object list = newInstance(LINKED_LIST_CLASS_NAME, List.of(5, 6, 7));

        String output = captureOutput(() -> invoke(list, "traverse"));

        assertEquals("5 -> 6 -> 7 -> null" + System.lineSeparator(), output);
    }

    @Test
    void linkedListWithTailInsertionsAndQueriesWork() throws Throwable {
        Object list = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);

        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtBegin", int.class, 10);
        invoke(list, "insertAtPosition", int.class, 15, int.class, 1);
        invoke(list, "insertAtEnd", int.class, 25);

        assertEquals(List.of(10, 15, 20, 25), toList(list));
        assertEquals(4, invoke(list, "count"));
        assertTrue((boolean) invoke(list, "exists", int.class, 15));
        assertEquals(25, invoke(list, "last"));
    }

    @Test
    void linkedListWithTailDeletesMaintainUsableTail() throws Throwable {
        Object list = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 10);
        invoke(list, "insertAtEnd", int.class, 20);
        invoke(list, "insertAtEnd", int.class, 30);

        assertTrue((boolean) invoke(list, "deleteByValue", int.class, 30));
        assertEquals(List.of(10, 20), toList(list));
        assertEquals(20, invoke(list, "last"));

        assertTrue((boolean) invoke(list, "deleteByPosition", int.class, 1));
        assertEquals(List.of(10), toList(list));
        assertEquals(10, invoke(list, "last"));

        assertTrue((boolean) invoke(list, "deleteAtBegin"));
        assertEquals(List.of(), toList(list));

        invoke(list, "insertAtEnd", int.class, 40);
        assertEquals(List.of(40), toList(list));
        assertEquals(40, invoke(list, "last"));
    }

    @Test
    void linkedListWithTailValidatesEmptyAndOutOfRangeOperations() throws Throwable {
        Object emptyList = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);

        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "deleteAtBegin"));
        assertThrows(IllegalStateException.class, () -> invoke(emptyList, "last"));
        assertThrows(IndexOutOfBoundsException.class, () -> invoke(emptyList, "insertAtPosition", int.class, 5, int.class, 1));

        Object populatedList = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);
        invoke(populatedList, "insertAtEnd", int.class, 10);
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteByPosition", int.class, -1));
        assertThrows(IllegalArgumentException.class, () -> invoke(populatedList, "deleteByPosition", int.class, 3));
    }

    @Test
    void linkedListWithTailTraversePrintsValuesInOrder() throws Throwable {
        Object list = newInstance(LINKED_LIST_WITH_TAIL_CLASS_NAME);
        invoke(list, "insertAtEnd", int.class, 1);
        invoke(list, "insertAtEnd", int.class, 2);

        String output = captureOutput(() -> invoke(list, "traverse"));

        assertEquals("1 -> 2 -> null" + System.lineSeparator(), output);
    }

    @Test
    void mainPrintsTheDemonstrationValues() {
        String output = captureOutputUnchecked(() -> SingleLinkedList.main(new String[0]));

        assertTrue(output.contains("Head node value: 10"));
        assertTrue(output.contains("Linked list as a list: [10, 20, 30]"));
        assertTrue(output.contains("Last node value: 30"));
        assertTrue(output.contains("20 -> 10 -> 30 -> null"));
    }

    private static Object newInstance(String className, Object... args) throws Throwable {
        Class<?> clazz = Class.forName(className);
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterCount() == args.length) {
                constructor.setAccessible(true);
                try {
                    return constructor.newInstance(args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        }
        throw new IllegalStateException("No matching constructor found for " + className);
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
