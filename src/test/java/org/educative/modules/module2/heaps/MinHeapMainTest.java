package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinHeapMainTest {
    @Test
    void insertPeekAndExtractMaintainAscendingHeapOrder() {
        Object heap = newHeap(8);

        assertTrue(isEmpty(heap));
        assertTrue(insert(heap, 7));
        assertTrue(insert(heap, -3));
        assertTrue(insert(heap, 7));
        assertTrue(insert(heap, 0));
        assertTrue(insert(heap, 2));
        assertEquals(-3, peek(heap));
        assertEquals(5, getSize(heap));

        assertEquals(-3, extractMin(heap));
        assertEquals(0, extractMin(heap));
        assertEquals(2, extractMin(heap));
        assertEquals(7, extractMin(heap));
        assertEquals(7, extractMin(heap));
        assertTrue(isEmpty(heap));
    }

    @Test
    void fullHeapRejectsAdditionalInsertions() {
        Object heap = newHeap(2);
        insert(heap, 4);
        insert(heap, 9);

        String output = ConsoleOutputCapture.capture(() -> assertFalse(insert(heap, 11)));

        assertTrue(isFull(heap));
        assertTrue(output.contains("Heap Overflow! Cannot insert element."));
        assertEquals(4, peek(heap));
    }

    @Test
    void emptyHeapPeekAndExtractReturnSentinelAndPrintMessages() {
        Object heap = newHeap(3);

        String output = ConsoleOutputCapture.capture(() -> {
            assertEquals(-1, peek(heap));
            assertEquals(-1, extractMin(heap));
        });

        assertTrue(output.contains("Heap is empty! No element to peek."));
        assertTrue(output.contains("Heap Underflow! Cannot extract from empty heap."));
    }

    @Test
    void defaultConstructorProvidesCapacityForTenElements() {
        Object heap = newDefaultHeap();

        for (int value = 10; value >= 1; value--) {
            assertTrue(insert(heap, value));
        }

        assertEquals(10, getSize(heap));
        assertTrue(isFull(heap));
    }

    private Object newHeap(int capacity) {
        try {
            Class<?> heapClass = Class.forName("org.educative.modules.module2.heaps.MinHeapMain$MinHeap");
            Constructor<?> constructor = heapClass.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(capacity);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object newDefaultHeap() {
        try {
            Class<?> heapClass = Class.forName("org.educative.modules.module2.heaps.MinHeapMain$MinHeap");
            Constructor<?> constructor = heapClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean insert(Object heap, int value) {
        return invokeBoolean(heap, "insert", value);
    }

    private int extractMin(Object heap) {
        return invokeInt(heap, "extractMin");
    }

    private int peek(Object heap) {
        return invokeInt(heap, "peek");
    }

    private int getSize(Object heap) {
        return invokeInt(heap, "getSize");
    }

    private boolean isEmpty(Object heap) {
        return invokeBoolean(heap, "isEmpty");
    }

    private boolean isFull(Object heap) {
        return invokeBoolean(heap, "isFull");
    }

    private boolean invokeBoolean(Object target, String methodName, int value) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName, int.class);
            method.setAccessible(true);
            return (boolean) method.invoke(target, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean invokeBoolean(Object target, String methodName) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (boolean) method.invoke(target);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private int invokeInt(Object target, String methodName) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (int) method.invoke(target);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
