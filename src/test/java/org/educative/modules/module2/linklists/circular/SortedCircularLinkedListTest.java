package org.educative.modules.module2.linklists.circular;

import org.educative.modules.module2.linklists.SortedCircularLinkedList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortedCircularLinkedListTest {
    private static final String NODE_CLASS_NAME =
            "org.educative.modules.module2.linklists.SortedCircularLinkedList$ListNode";
    private static final String HELPER_CLASS_NAME =
            "org.educative.modules.module2.linklists.SortedCircularLinkedList$CircularLinkedList";

    @Test
    void insertHandlesEmptyAndSingleNodeLists() throws Exception {
        SortedCircularLinkedList solution = new SortedCircularLinkedList();

        Object emptyResult = insert(solution, null, 7);
        assertEquals(List.of(7), toList(emptyResult));

        Object singleNode = createCircularList(5);
        Object singleResult = insert(solution, singleNode, 3);
        assertEquals(List.of(5, 3), toList(singleResult));
    }

    @Test
    void insertPlacesValuesInMiddleWrapAroundAndDuplicateCases() throws Exception {
        SortedCircularLinkedList solution = new SortedCircularLinkedList();

        Object middleInsert = insert(solution, createCircularList(1, 3, 5), 4);
        assertEquals(List.of(1, 3, 4, 5), toList(middleInsert));

        Object wrapAroundInsert = insert(solution, createCircularList(5, 6, 1, 2, 3), -1);
        assertEquals(List.of(5, 6, -1, 1, 2, 3), toList(wrapAroundInsert));

        Object duplicatesInsert = insert(solution, createCircularList(2, 2, 2), 2);
        assertEquals(List.of(2, 2, 2, 2), toList(duplicatesInsert));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            SortedCircularLinkedList.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("1.\tList: [1, 3, 5]"));
        assertTrue(printed.contains("Result: [1, 3, 4, 5]"));
        assertTrue(printed.contains("5.\tList: [5, 6, 1, 2, 3]"));
    }

    private static Object createCircularList(int... values) throws Exception {
        Class<?> helperClass = Class.forName(HELPER_CLASS_NAME);
        Constructor<?> constructor = helperClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object helper = constructor.newInstance();
        Method method = helperClass.getDeclaredMethod("createCircularLinkedList", int[].class);
        method.setAccessible(true);
        return method.invoke(helper, values);
    }

    private static Object insert(SortedCircularLinkedList solution, Object head, int value) throws Exception {
        Class<?> nodeClass = Class.forName(NODE_CLASS_NAME);
        Method method = SortedCircularLinkedList.class.getDeclaredMethod("insert", nodeClass, int.class);
        method.setAccessible(true);
        return method.invoke(solution, head, value);
    }

    @SuppressWarnings("unchecked")
    private static List<Integer> toList(Object head) throws Exception {
        Class<?> helperClass = Class.forName(HELPER_CLASS_NAME);
        Class<?> nodeClass = Class.forName(NODE_CLASS_NAME);
        Method method = helperClass.getDeclaredMethod("toList", nodeClass);
        method.setAccessible(true);
        return (List<Integer>) method.invoke(null, head);
    }
}
