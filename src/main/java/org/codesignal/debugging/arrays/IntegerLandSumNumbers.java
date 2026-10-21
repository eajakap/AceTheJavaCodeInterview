package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * In the realm of Integerland, there exist two towns named Town A and Town B. The towns are positioned along a
 * straight road, at points corresponding to different integers. The fascinating fact about Integerland is that the
 * locations of the towns could change. They can take any integer value between $1$ and $10^9$.
 *
 * Your mission, should you choose to accept it, is to create a very special kind of map – a function or method,
 * so to speak – that can calculate the total value of all the points (integer numbers) along the road between
 * Town A and Town B, both towns included, for a number of different queries.
 *
 * You might find towns A and B at the same location (i.e., a equals b) or Town A could be located before or
 * after Town B along the road.
 *
 * Just to give you an idea of what you're getting into, let's consider an instance when Town A is at point 1
 * (i.e., a=1) and Town B is at point 5 (so b=5). Here, the summation of all the points from Town A to Town B inclusive,
 * which are 1, 2, 3, 4, 5, comes up to 15. We hope your map (or function) could calculate this total in similar situations.
 *
 * Of course, in the best interests of Integerland and the occasional travelers, we'd like you to optimize this map.
 * It is essential that it works as efficiently as possible. Your understanding of complexity analysis and various
 * ways of optimizing this task will certainly come in handy.
 *
 * Best of luck on your endeavor!
 *
 * Input Specification:
 *
 * The function will receive a single argument, which is a list of pairs of unsigned integers.
 * Each pair (a, b) represents a query describing the bounds of the road to be considered.
 * Output Specification:
 *
 * For each query, output the total value of all the points (integer numbers) between Town A and Town B, inclusive, in a list.
 * Constraints
 *
 * Both a and b are integers.
 * $1 ≤ a, b ≤ 1000000000$.
 * $1 ≤ queries.size() ≤ 100000$.
 * Sample Input 1:
 * [[1, 3], [2, 5]]
 * Sample Output 1:
 * [6, 14]
 */
public class IntegerLandSumNumbers {
    public List<Long> sumNumbers(List<int[]> queries) {
        // TODO: Implement this method
        List<Long> sumList = new ArrayList<Long>();
        for (int[] query: queries) {
            long a = (long)query[0];
            long b = (long)query[1];

            long start = Math.min(a, b);
            long end = Math.max(a, b);

            long sumA = start * (start + 1) / 2;
            long sumB = end * (end + 1) / 2;
            long sum = (sumA > sumB) ? sumA - sumB + start : sumB - sumA + start;
            sumList.add(sum);
        }

        return sumList;
    }

    public static void main(String[] args) {
        IntegerLandSumNumbers app = new IntegerLandSumNumbers();
        List<int[]> queries = new ArrayList<>();
        queries.add(new int[]{1, 3});
        queries.add(new int[]{2, 5});
        List<Long> result = app.sumNumbers(queries);
        System.out.println(result); // Output: [6, 14]
    }
}