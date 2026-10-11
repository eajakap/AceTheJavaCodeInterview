package org.educative.modules.module2.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TwoSumTest {
    private static void assertIndicesSumTo(int[] nums, int[] indices, int target) {
        assertEquals(2, indices.length);
        assertNotEquals(indices[0], indices[1]);
        assertEquals(target, nums[indices[0]] + nums[indices[1]]);
    }

    @Test
    void bruteForceReturnsFirstPairInIndexOrder() {
        assertArrayEquals(new int[]{1, 4}, TwoSum.twosum(new int[]{3, 2, 5, 7, 9, 11}, 11));
    }

    @Test
    void bruteForceReturnsEmptyWhenNoPairExists() {
        assertArrayEquals(new int[]{}, TwoSum.twosum(new int[]{1, 2, 3}, 100));
        assertArrayEquals(new int[]{}, TwoSum.twosum(new int[]{}, 0));
    }

    @Test
    void sortingApproachReturnsOriginalIndices() {
        int[] nums = {10, -2, 3, 4, 5};
        assertIndicesSumTo(nums, TwoSum.twosumV2(nums, 8), 8);

        int[] negatives = {-4, -8, 0, -7, -3, -10};
        assertIndicesSumTo(negatives, TwoSum.twosumV2(negatives, -15), -15);
    }

    @Test
    void sortingApproachHandlesDuplicatesAndMissingPairs() {
        int[] nums = {0, 4, 3, 0};
        assertIndicesSumTo(nums, TwoSum.twosumV2(nums, 0), 0);
        assertArrayEquals(new int[]{}, TwoSum.twosumV2(new int[]{1, 2, 3}, 100));
        assertArrayEquals(new int[]{}, TwoSum.twosumV2(new int[]{}, 0));
    }

    @Test
    void sortedTwoPointersReturnsIndicesInSortedArray() {
        assertArrayEquals(new int[]{1, 6}, TwoSum.twoSumSortedTwoPointers(new int[]{1, 2, 3, 4, 5, 7, 9, 11}, 11));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumSortedTwoPointers(new int[]{1, 2, 3}, 100));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumSortedTwoPointers(new int[]{1}, 1));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumSortedTwoPointers(null, 1));
    }

    @Test
    void sortedTwoPointersAvoidsIntOverflow() {
        int[] nums = {Integer.MAX_VALUE - 1, Integer.MAX_VALUE};
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumSortedTwoPointers(nums, Integer.MIN_VALUE));
    }

    @Test
    void sortPairsApproachReturnsOriginalIndices() {
        int[] nums = {1, 5, 3, 7};
        assertIndicesSumTo(nums, TwoSum.twoSumUnsortedSortPairsTwoPointers(nums, 8), 8);
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedSortPairsTwoPointers(nums, 100));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedSortPairsTwoPointers(null, 8));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedSortPairsTwoPointers(new int[]{4}, 8));
    }

    @Test
    void hashMapApproachFindsPairWithoutReusingAnElement() {
        assertArrayEquals(new int[]{0, 1},
                TwoSum.twoSumUnsortedHashMap(new int[]{1000000000, -1000000000, 3, 4}, 0));
        // A single 4 must not be paired with itself to reach 8
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedHashMap(new int[]{4, 1}, 8));
        assertArrayEquals(new int[]{0, 2}, TwoSum.twoSumUnsortedHashMap(new int[]{4, 1, 4}, 8));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedHashMap(null, 8));
        assertArrayEquals(new int[]{-1, -1}, TwoSum.twoSumUnsortedHashMap(new int[]{4}, 8));
    }
}
