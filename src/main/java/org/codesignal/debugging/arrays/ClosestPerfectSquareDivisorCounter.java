package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given an array of n integers. Your task is to return an array in which the i-th element of the array
 * is the number of divisors of the closest perfect square to the i-th integer in the input array.
 * The "closest" perfect square to a number x is defined as the perfect square that is nearest to x
 * in terms of the smallest absolute distance.
 *
 * Please note that you are expected to solve this problem within 100 milliseconds.
 *
 * Constraints: $1 \leq n \leq 100000$, $1 \leq arr[i] \leq 10000$
 *
 * Example: For arr = [7, 17, 20, 7, 3], the output should be [3, 5, 5, 3, 3].
 *
 * For arr[0] = 7, the closest perfect square is 9, which has 3 divisors - [1, 3, 9].
 * For arr[1] = 17, the closest perfect square is 16, which has 5 divisors - [1, 2, 4, 8, 16].
 * For arr[2] = 20, the closest perfect square is 16, which has 5 divisors.
 * For arr[3] = 7, the closest perfect square is 9, which has 3 divisors.
 * For arr[4] = 3, the closest perfect square is 4, which has 3 divisors - [1, 2, 4].
 */
public class ClosestPerfectSquareDivisorCounter {
    public static List<Integer> solution(List<Integer> arr) {

        // Find the maximum input value
        int max = 0;
        for (int num : arr) {
            max = Math.max(max, num);
        }

        // Largest root we may need, including the next perfect square
        int maxRoot = (int) Math.sqrt(max) + 1;

        // Calculate divisor counts for all required perfect squares
        int[] divisorCountOfSquare = new int[maxRoot + 1];

        for (int root = 1; root <= maxRoot; root++) {
            divisorCountOfSquare[root] = countDivisorsOfSquare(root);
        }

        // Precompute answer for every number up to max
        int[] closestSquareDivisors = new int[max + 1];

        for (int number = 1; number <= max; number++) {

            int lowerRoot = (int) Math.sqrt(number);
            int upperRoot = lowerRoot + 1;

            int lowerSquare = lowerRoot * lowerRoot;
            int upperSquare = upperRoot * upperRoot;

            int closestRoot;

            if (number - lowerSquare < upperSquare - number) {
                closestRoot = lowerRoot;
            } else {
                closestRoot = upperRoot;
            }

            closestSquareDivisors[number] =
                    divisorCountOfSquare[closestRoot];
        }

        // Build result
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            result.add(closestSquareDivisors[arr.get(i)]);
        }

        return result;
    }

    private static int countDivisorsOfSquare(int root) {

        int number = root;
        int divisorCount = 1;

        for (int factor = 2; factor * factor <= number; factor++) {

            int exponent = 0;

            while (number % factor == 0) {
                number /= factor;
                exponent++;
            }

            if (exponent > 0) {
                divisorCount *= (2 * exponent + 1);
            }
        }

        if (number > 1) {
            divisorCount *= 3;
        }

        return divisorCount;
    }

    public static void main(String[] args) {
        System.out.println(solution(List.of(7, 17, 20, 7, 3)));
        // [3, 5, 5, 3, 3]
    }
}
