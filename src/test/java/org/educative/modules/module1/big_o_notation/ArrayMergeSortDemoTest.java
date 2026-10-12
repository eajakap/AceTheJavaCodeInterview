package org.educative.modules.module1.big_o_notation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// mergeSort is private, so it is invoked through reflection.
class ArrayMergeSortDemoTest {

    private static void mergeSort(int[] input) throws Exception {
        Method method = ArrayMergeSortDemo.class.getDeclaredMethod("mergeSort", int.class, int.class, int[].class);
        method.setAccessible(true);
        method.invoke(null, 0, input.length - 1, input);
    }

    private static void assertSorts(int[] input) throws Exception {
        int[] expected = input.clone();
        Arrays.sort(expected);
        mergeSort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void sortsUnsortedArray() throws Exception {
        assertSorts(new int[]{5, 3, 8, 1, 9, 2, 7});
    }

    @Test
    void keepsSortedArrayUnchanged() throws Exception {
        assertSorts(new int[]{1, 2, 3, 4, 5, 6});
    }

    @Test
    void sortsReverseSortedArray() throws Exception {
        assertSorts(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
    }

    @Test
    void sortsSmallArrays() throws Exception {
        assertSorts(new int[]{1});
        assertSorts(new int[]{2, 1});
        assertSorts(new int[]{3, 1, 2});
        assertSorts(new int[]{4, 3, 2, 1});
    }

    @Test
    void sortsArrayWithDuplicatesAndNegatives() throws Exception {
        assertSorts(new int[]{3, -1, 3, 0, -1, 2, 2});
    }

    @Test
    void sortsRandomArraysOfManySizes() throws Exception {
        Random random = new Random(7);
        for (int size = 1; size <= 150; size++) {
            int[] input = random.ints(size, -1000, 1000).toArray();
            assertSorts(input);
        }
    }

    @Test
    void mainRunsWithoutError() {
        ArrayMergeSortDemo.main(new String[0]);
    }
}
