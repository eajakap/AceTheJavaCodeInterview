package org.educative.modules.module2.linklists.single;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReverseLinkedListKGroupTest {
    private static final String LIST_NODE_CLASS_NAME =
            "org.educative.modules.module2.linklists.single.ReverseLinkedListKGroup$ListNode";

    @Test
    void buildListAndToListRoundTripValues() throws Throwable {
        Object head = buildList(1, 2, 3, 4);

        assertEquals(List.of(1, 2, 3, 4), toList(head));
    }

    @Test
    void buildListReturnsNullForNullOrEmptyInput() throws Throwable {
        assertNull(invokeStatic(ReverseLinkedListKGroup.class, "buildList", new Class<?>[]{int[].class}, new Object[]{null}));
        assertNull(buildList());
        assertEquals(List.of(), toList(null));
    }

    @Test
    void reverseKGroupReversesEachCompleteGroup() throws Throwable {
        ReverseLinkedListKGroup solver = new ReverseLinkedListKGroup();
        Object head = buildList(1, 2, 3, 4, 5, 6);

        Object reversed = invoke(
                solver,
                "reverseKGroup",
                new Class<?>[]{listNodeClass(), int.class},
                new Object[]{head, 3}
        );

        assertEquals(List.of(3, 2, 1, 6, 5, 4), toList(reversed));
    }

    @Test
    void reverseKGroupLeavesTrailingNodesWhenFewerThanKRemain() throws Throwable {
        ReverseLinkedListKGroup solver = new ReverseLinkedListKGroup();
        Object head = buildList(1, 2, 3, 4, 5);

        Object reversed = invoke(
                solver,
                "reverseKGroup",
                new Class<?>[]{listNodeClass(), int.class},
                new Object[]{head, 3}
        );

        assertEquals(List.of(3, 2, 1, 4, 5), toList(reversed));
    }

    @Test
    void reverseKGroupHandlesKOneSingleNodeAndKLargerThanLength() throws Throwable {
        ReverseLinkedListKGroup solver = new ReverseLinkedListKGroup();

        Object kOneResult = invoke(
                solver,
                "reverseKGroup",
                new Class<?>[]{listNodeClass(), int.class},
                new Object[]{buildList(10, 20, 30), 1}
        );
        assertEquals(List.of(10, 20, 30), toList(kOneResult));

        Object largerThanLengthResult = invoke(
                solver,
                "reverseKGroup",
                new Class<?>[]{listNodeClass(), int.class},
                new Object[]{buildList(7, 8), 5}
        );
        assertEquals(List.of(7, 8), toList(largerThanLengthResult));

        Object singleNodeResult = invoke(
                solver,
                "reverseKGroup",
                new Class<?>[]{listNodeClass(), int.class},
                new Object[]{buildList(42), 2}
        );
        assertEquals(List.of(42), toList(singleNodeResult));
    }

    @Test
    void mainPrintsBuiltInExamples() {
        String output = captureOutput(() -> ReverseLinkedListKGroup.main(new String[0]));

        assertTrue(output.contains("1.\thead: [1, 2, 3, 4, 5, 6]"));
        assertTrue(output.contains("Result: [3, 2, 1, 6, 5, 4]"));
        assertTrue(output.contains("Result: [400, 300, 200, 100]"));
    }

    private static Object buildList(int... values) throws Throwable {
        return invokeStatic(
                ReverseLinkedListKGroup.class,
                "buildList",
                new Class<?>[]{int[].class},
                new Object[]{values}
        );
    }

    private static List<Integer> toList(Object head) throws Throwable {
        @SuppressWarnings("unchecked")
        List<Integer> values = (List<Integer>) invokeStatic(
                ReverseLinkedListKGroup.class,
                "toList",
                new Class<?>[]{listNodeClass()},
                new Object[]{head}
        );
        return values;
    }

    private static Class<?> listNodeClass() throws ClassNotFoundException {
        return Class.forName(LIST_NODE_CLASS_NAME);
    }

    private static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
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

    private static String captureOutput(Runnable action) {
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
}
