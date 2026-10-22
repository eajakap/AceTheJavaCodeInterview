package org.codesignal.debugging.hashmap;

import java.util.*;

/**
 * Finds four distinct numbers in a list whose sum equals a target.
 *
 * Given a list of distinct integers and a target sum, return exactly four numbers from the list that add up
 * to the target. If several quads qualify, any one of them may be returned. If none exists, return an empty list.
 *
 * Constraints: the list holds between 4 and 1,000 distinct integers. Each integer and the target sum are in the
 * range [-10^6, 10^6]. The solution must finish within 3 seconds.
 *
 * Example: for the list [5, 15, 2, 7, 8, 4] and a target of 24, one valid result is [5, 7, 4, 8].
 */
public class QuadSumFinder {
    private static enum SolutionApproach {
        HASH_MAP_COMPLEMENT,
        TWO_POINTERS
    }
    /**
     * Finds four numbers from the list that sum to the target.
     * Steps:
     * 1. Populate a HashMap with the pairwise sums of all distinct integers in the list.
     * 2. Iterate over the sums in the HashMap.
     * 3. For each sum, find the complement (target - sum) in the HashMap.
     * 4. If the complement exists, find all pairs of indices that sum to the complement.
     * 5. Return the four numbers at those indices.
     * 6. Time Complexity: O(n^2)
     * 7. Space Complexity: O(n^2)
     * @param targetSum the required sum of the four numbers
     * @param numbers   the distinct input integers
     * @return a list of four numbers that sum to {@code targetSum}, or an empty list if none exist
     */
    public static List<Integer> findQuadSumUsingHashMapComplement(int targetSum, List<Integer> numbers) {
        int length = numbers.size();
        Map<Integer, List<int[]>> sumMap = new HashMap<>();

        // Step 2: Populate the HashMap with the sums of all pairs
        long start = System.nanoTime();
        for (int i = 0; i < length - 1; ++i) {
            for (int j = i + 1; j < length; ++j) {
                int pairwiseSum = numbers.get(i) + numbers.get(j);
                sumMap.computeIfAbsent(pairwiseSum, k -> new ArrayList<>()).add(new int[]{i, j});
            }
        }
        System.out.println("Built pair-sum map (" + sumMap.size() + " distinct sums) in "
                + (System.nanoTime() - start) / 1000 + " us");

        // Step 3: Iterate over the sums
        start = System.nanoTime();
        for (int sum : sumMap.keySet()) {
            int complement = targetSum - sum;
            if (sumMap.containsKey(complement)) {
                List<int[]> pairs1 = sumMap.get(sum);
                List<int[]> pairs2 = sumMap.get(complement);

                for (int[] pair1 : pairs1) {
                    for (int[] pair2 : pairs2) {
                        int a = pair1[0], b = pair1[1];
                        int c = pair2[0], d = pair2[1];

                        // Ensure all indices are distinct
                        if (a != c && a != d && b != c && b != d) {
                            System.out.println("Found a quad in " + (System.nanoTime() - start) / 1000 + " us");
                            return Arrays.asList(numbers.get(a), numbers.get(b), numbers.get(c), numbers.get(d));
                        }
                    }
                }
            }
        }
        System.out.println("No quad found after searching in " + (System.nanoTime() - start) / 1000 + " us");
        return Collections.emptyList();
    }

    /**
     * Finds four numbers from the list that sum to the target using sorting and two pointers.
     * Steps:
     * 1. Sort a copy of the numbers in ascending order.
     * 2. Fix the first two numbers with indices i and j, where i is less than j.
     * 3. Place a left pointer just after j and a right pointer at the end of the array.
     * 4. If the four numbers sum to the target, return them. If the sum is too small, move the left pointer
     *    right. If it is too large, move the right pointer left.
     * 5. If no quad is found, return an empty list.
     * 6. Time Complexity: O(n^3)
     * 7. Space Complexity: O(n) for the sorted copy
     *
     * @param targetSum the required sum of the four numbers
     * @param numbers   the distinct input integers
     * @return a list of four numbers that sum to {@code targetSum}, or an empty list if none exist
     */
    public static List<Integer> findQuadSumUsingTwoPointers(int targetSum, List<Integer> numbers) {
        long start = System.nanoTime();
        int[] sorted = numbers.stream().mapToInt(Integer::intValue).sorted().toArray();
        int length = sorted.length;

        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                int left = j + 1;
                int right = length - 1;
                while (left < right) {
                    // Values are at most 10^6 in magnitude, so a four-number sum fits in an int
                    int sum = sorted[i] + sorted[j] + sorted[left] + sorted[right];
                    if (sum == targetSum) {
                        System.out.println("Found a quad in " + (System.nanoTime() - start) / 1000 + " us");
                        return Arrays.asList(sorted[i], sorted[j], sorted[left], sorted[right]);
                    } else if (sum < targetSum) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        System.out.println("No quad found after searching in " + (System.nanoTime() - start) / 1000 + " us");
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        int targetSum = 24;
        List<Integer> numbers = Arrays.asList(5, 15, 2, 7, 8, 4);
        long start = System.nanoTime();
        System.out.println(findQuadSumUsingHashMapComplement(targetSum, numbers));
        System.out.println("Total time: " + (System.nanoTime() - start) / 1000 + " us");
        System.out.println("_".repeat(100));

        start = System.nanoTime();
        System.out.println(findQuadSumUsingTwoPointers(targetSum, numbers));
        System.out.println("Total time: " + (System.nanoTime() - start) / 1000 + " us");
    }
}

