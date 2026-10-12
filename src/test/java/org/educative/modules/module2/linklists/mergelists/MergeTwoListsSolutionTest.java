package org.educative.modules.module2.linklists.mergelists;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeTwoListsSolutionTest {
    @Test
    void mergeTwoListsHandlesEmptyInputs() throws Exception {
        MergeTwoListsSolution solution = new MergeTwoListsSolution();

        Object mergedWithBothEmpty = merge(solution, null, null);
        assertEquals(List.of(), toList(mergedWithBothEmpty));

        Object nonEmpty = buildLinkedList(1, 2, 3);
        Object mergedWithOneEmpty = merge(solution, nonEmpty, null);
        assertEquals(List.of(1, 2, 3), toList(mergedWithOneEmpty));
    }

    @Test
    void mergeTwoListsMergesSortedValuesIncludingDuplicatesAndNegatives() throws Exception {
        MergeTwoListsSolution solution = new MergeTwoListsSolution();

        Object mergedDuplicates = merge(solution, buildLinkedList(1, 1, 3), buildLinkedList(1, 2, 2));
        assertEquals(List.of(1, 1, 1, 2, 2, 3), toList(mergedDuplicates));

        Object mergedNegatives = merge(solution, buildLinkedList(-10, -5, 0, 5), buildLinkedList(-7, -3, 2, 10));
        assertEquals(List.of(-10, -7, -5, -3, 0, 2, 5, 10), toList(mergedNegatives));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            MergeTwoListsSolution.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("1.\tlist1: [2, 5, 7]"));
        assertTrue(printed.contains("Result: [1, 2, 3, 5, 6, 7, 8]"));
        assertTrue(printed.contains("5.\tlist1: [0, 10, 20, 30, 40, 50]"));
    }

    private static Object merge(MergeTwoListsSolution solution, Object list1, Object list2) throws Exception {
        Class<?> nodeClass = Class.forName(
                "org.educative.modules.module2.linklists.mergelists.MergeTwoListsSolution$ListNode"
        );
        Method method = MergeTwoListsSolution.class.getDeclaredMethod("mergeTwoLists", nodeClass, nodeClass);
        method.setAccessible(true);
        try {
            return method.invoke(solution, list1, list2);
        } catch (InvocationTargetException exception) {
            throw unwrap(exception);
        }
    }

    private static Object buildLinkedList(int... values) throws Exception {
        Method method = MergeTwoListsSolution.class.getDeclaredMethod("buildLinkedList", int[].class);
        method.setAccessible(true);
        return method.invoke(null, values);
    }

    @SuppressWarnings("unchecked")
    private static List<Integer> toList(Object head) throws Exception {
        Class<?> nodeClass = Class.forName(
                "org.educative.modules.module2.linklists.mergelists.MergeTwoListsSolution$ListNode"
        );
        Method method = MergeTwoListsSolution.class.getDeclaredMethod("toList", nodeClass);
        method.setAccessible(true);
        return (List<Integer>) method.invoke(null, head);
    }

    private static Exception unwrap(InvocationTargetException exception) throws Exception {
        Throwable cause = exception.getCause();
        if (cause instanceof Exception checked) {
            return checked;
        }
        throw exception;
    }
}
