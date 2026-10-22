package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Our task here involves an array composed of at most 1,000 elements and potentially millions of queries.
 * Each query is a pair of integers denoted as l and r, which correspond to some indices in the array.
 * Your goal is to write a Java method that, for each query, returns the minimum value in the array
 * between indices l and r (inclusive).
 *
 * The catch is this: rather than directly finding the minimum value for each query one by one,
 * we're required to optimize the process. The idea here is to precalculate the minimum value for
 * each possible l and r, store these values, and then proceed with the queries.
 * This way, we can simplify the problem and enhance the speed of our solution by eliminating redundant computations.
 *
 * The method will accept three parameters: arr, Ls, and Rs.
 * The primary array is arr, while Ls and Rs are ArrayLists that hold the l and r values respectively for each query.
 * For instance, let's say you have an array like {2, 1, 3, 7, 5} and the following queries: {0, 2, 4} and {1, 3, 4}.
 * The aim of our method would be to return {1, 3, 5} as the minimum values within the ranges of the three queries.
 */
public class QuadrupleSumFinder {

    private static enum SolutionApproach {
        HASH_MAP_COMPLEMENT,
        TWO_POINTERS
    }
    
    public static List<Integer> queryMin(int[] arr, List<Integer> Ls, List<Integer> Rs) {
        int n = arr.length;
        long start = System.nanoTime();
        int[][] precalc = new int[n][n];
        for (int l = 0; l < n; ++l) {
            int minVal = arr[l];
            for (int r = l; r < n; ++r) {
                minVal = Math.min(minVal, arr[r]);
                precalc[l][r] = minVal;
            }
        }
        System.out.println("Precalculated range minimums in " + (System.nanoTime() - start) / 1000 + " us");
        List<Integer> res = new ArrayList<>();

        start = System.nanoTime();
        for (int i = 0; i < Ls.size(); ++i) {
            int l = Ls.get(i);
            int r = Rs.get(i);
            res.add(precalc[l][r]);
        }
        System.out.println("Answered " + Ls.size() + " queries in " + (System.nanoTime() - start) / 1000 + " us");

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 3, 7, 5};
        List<Integer> Ls =  Arrays.asList(0, 2, 4);
        List<Integer> Rs = List.of(1, 3, 4);

        long start = System.nanoTime();
        List<Integer> result = queryMin(arr, Ls, Rs);
        System.out.println("Total time: " + (System.nanoTime() - start) / 1000 + " us");

        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println();
        System.out.println("Total Time Consumed: " + (System.nanoTime() - start) / 1000 + " us");

    }
}