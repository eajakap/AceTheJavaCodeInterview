package org.codesignal.debugging.arrays;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem:
 * You are given two arrays, array A and array B, each containing n integers where n can range from $2$ to $100000$, inclusive.
 * All elements of both arrays are integers that can range from $-10^{10}$ to $10^{10}$, inclusive.
 * Your task is to create a function optimizedReplace that returns a new array, C. For each index i, C[i] should contain a
 * specific value from array A - A[j], determined by the condition that B[i] is the closest number to B[j] from array B.
 * This means that array C will have the same length as A and B. C[i] will contain the value corresponding to the j-th index
 * from array A, where the value at the j-th index in array B is closest to the value at the i-th index of array B.
 * Assume that there is no ambiguity. The array B is given in a way that no two elements in array B have the same minimal
 * absolute difference with B[i].
 * Remember, the number of elements, n, and the range of the elements, imply that brute force solutions will not be efficient.
 * You should aim to leverage optimized algorithms and techniques to solve this task efficiently.
 * Example
 * Suppose we have A = {10, 20, 30, 40, 50} and B = {7, 5, 1, 2, 4}.
 * The function optimizedReplace(A, B) should work as follows:
 * For B[0] = 7, the closest number in B is 5 at index 1. Hence, C[0] = A[1] = 20.
 * For B[1] = 5, the closest number in B is 4 at index 4. Thus, C[1] = A[4] = 50.
 * For B[2] = 1, the closest number in B is 2 at index 3. Hence, C[2] = A[3] = 40.
 * For B[3] = 2, the closest number in B is 1 at index 2. So, C[3] = A[2] = 30.
 * Lastly, for B[4] = 4, the closest number in B is 5 at index 1. We have C[4] = A[1] = 20.
 * Thus, the function optimizedReplace({10, 20, 30, 40, 50}, {7, 5, 1, 2, 4}) should return {20, 50, 40, 30, 20}.
 */
public class ArrayOptimizeReplace {

    public static long[] optimizedReplace(long[] A, long[] B) {
        int n = A.length;
        long[] C = new long[n];
        Integer[] sortedIndices = new Integer[n];
        for (int i = 0; i < n; i++) {
            sortedIndices[i] = i;
        }
        // Sort indices by their corresponding values in B in ascending order
        Arrays.sort(sortedIndices, Comparator.comparingLong(i -> B[i]));
//        Arrays.sort(sortedIndices, (a, b) -> Long.compare(B[a], B[b]));

        // In sorted order, the closest value to B[sortedIndices[k]] is its left or right neighbor.
        for (int k = 0; k < n; k++) {
            int i = sortedIndices[k]; // sorted index of B
            int closest;
            if (k == 0) { // first element
                closest = sortedIndices[1]; // 2nd element
            } else if (k == n - 1) { // last element
                closest = sortedIndices[n - 2]; // 2nd last element
            } else { // middle element
                long leftDiff = B[i] - B[sortedIndices[k - 1]]; // difference between current and left neighbor
                long rightDiff = B[sortedIndices[k + 1]] - B[i]; // difference between current and right neighbor
                closest = leftDiff < rightDiff ? sortedIndices[k - 1] : sortedIndices[k + 1];
            }
            C[i] = A[closest]; //use the closest value retrived from B in A to fill C
        }
        return C;
    }

    public static void main(String[] args) {
        long[] A = {10, 20, 30, 40, 50};
        long[] B = {7, 5, 1, 2, 4};
        long[] C = {20, 50, 40, 30, 20};
        long[] result = optimizedReplace(A, B);
        for (long value : result) {
            System.out.print(value + " ");
        }
    }
}
