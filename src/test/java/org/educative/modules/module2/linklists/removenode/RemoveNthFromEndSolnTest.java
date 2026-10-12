package org.educative.modules.module2.linklists.removenode;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoveNthFromEndSolnTest {
    @Test
    void removeNthFromEndRemovesTailMiddleAndHead() throws Exception {
        assertEquals(
                "10 -> 20 -> 30 -> 40 -> None",
                display(removeNthFromEnd(buildHead(10, 20, 30, 40, 50), 1))
        );
        assertEquals(
                "5 -> 10 -> None",
                display(removeNthFromEnd(buildHead(5, 10, 15), 1))
        );
        assertEquals(
                "14 -> 21 -> 28 -> None",
                display(removeNthFromEnd(buildHead(7, 14, 21, 28), 4))
        );
    }

    @Test
    void removeNthFromEndRejectsInvalidNValues() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> removeNthFromEnd(buildHead(1, 2, 3), 0));
        assertThrows(IllegalArgumentException.class, () -> removeNthFromEnd(buildHead(1, 2, 3), -1));
        assertThrows(IllegalArgumentException.class, () -> removeNthFromEnd(buildHead(1, 2, 3), 4));
        assertThrows(IllegalArgumentException.class, () -> removeNthFromEnd(buildHead(), 1));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            RemoveNthFromEndSoln.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("1.\tLinked List: [10, 20, 30, 40, 50]"));
        assertTrue(printed.contains("Result: 10 -> 20 -> 30 -> 40 -> None"));
        assertTrue(printed.contains("5.\tLinked List: [3, 6, 9, 12, 15, 18]"));
    }

    private static Object buildHead(int... values) throws Exception {
        Class<?> linkedListClass = Class.forName(
                "org.educative.modules.module2.linklists.removenode.RemoveNthFromEndSoln$LinkedList"
        );
        Method fromArray = linkedListClass.getDeclaredMethod("fromArray", int[].class);
        fromArray.setAccessible(true);
        Object linkedList = fromArray.invoke(null, values);
        Field headField = linkedListClass.getDeclaredField("head");
        headField.setAccessible(true);
        return headField.get(linkedList);
    }

    private static Object removeNthFromEnd(Object head, int n) throws Exception {
        Class<?> nodeClass = Class.forName(
                "org.educative.modules.module2.linklists.removenode.RemoveNthFromEndSoln$ListNode"
        );
        Class<?> solutionClass = Class.forName(
                "org.educative.modules.module2.linklists.removenode.RemoveNthFromEndSoln$Solution"
        );
        Constructor<?> constructor = solutionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object solution = constructor.newInstance();
        Method method = solutionClass.getDeclaredMethod("removeNthFromEnd", nodeClass, int.class);
        method.setAccessible(true);
        try {
            return method.invoke(solution, head, n);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof Exception checked) {
                throw checked;
            }
            throw exception;
        }
    }

    private static String display(Object head) throws Exception {
        Class<?> nodeClass = Class.forName(
                "org.educative.modules.module2.linklists.removenode.RemoveNthFromEndSoln$ListNode"
        );
        Method method = RemoveNthFromEndSoln.class.getDeclaredMethod("toDisplayString", nodeClass);
        method.setAccessible(true);
        return (String) method.invoke(null, head);
    }
}
