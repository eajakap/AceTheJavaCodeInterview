package org.educative.modules.module3.algorithms.greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Solves the "Connect N Pipes with Minimum Cost" problem with two greedy approaches.
 *
 * Problem: given n pipes of different lengths, connect them into one pipe. Connecting two
 * pipes costs the sum of their lengths, and the total cost should be minimal.
 * Input: an array of pipe lengths. Output: the minimum total cost.
 *
 * Example: [4, 2, 3, 7] -> 30
 * 1. Connect 2 and 3 -> cost 5, pipes [4, 5, 7]
 * 2. Connect 4 and 5 -> cost 9, pipes [9, 7]
 * 3. Connect 9 and 7 -> cost 16, pipes [16]
 * Total cost = 5 + 9 + 16 = 30.
 *
 * Contains {@link SortingMinimumCost} (sorted-array approach) and
 * {@link MinHeapMinimumCost} (min-heap approach).
 *
 * Which to use: the min-heap approach (O(n log n), O(n) space). A heap always hands back
 * the two shortest pipes, including merged ones, so it gives the true minimum for any input.
 * The sorting approach sorts only once and never re-orders merged pipes, so it can return a
 * cost above the minimum (e.g. {1, 1, 1, 1} gives 9 instead of 8). It also overwrites its input.
 */
public class ConnectNPipesWithMinCost {

    /** Sorted-array approach; see the caveat on {@link #minCost(int[])}. */
    static class SortingMinimumCost {
        /**
         * Connects the pipes by repeatedly merging the current smallest pipe into the next one.
         * The input array is sorted and overwritten in place, so it should not be reused.
         *
         * Caveat: the array is sorted only once. After a merge the combined length is not
         * re-sorted into place, so the result can exceed the true minimum when the merged
         * pipe is larger than later pipes (e.g. {1, 1, 1, 1} gives 9, but the minimum is 8).
         * Use {@link MinHeapMinimumCost#minCost(int[], int)} for a correct answer.
         *
         * Time complexity: O(n log n) for the sort plus O(n) for the merge loop.
         * Space complexity: O(1) beyond the in-place modification.
         *
         * @param pipes lengths of the pipes (modified in place)
         * @return total cost of connecting all pipes
         */
        public static int minCost(int[] pipes) {
            // Initialize the total cost to 0
            int cost = 0;
            // Sort the array of pipe lengths in ascending order
            Arrays.sort(pipes);
            // Iterate through the sorted array and connect the pipes
            for (int i = 0; i < pipes.length - 1; i++) {
                // Store the previous cost before connecting the next two pipes
                int prev_cost = cost;
                // Cost of this merge
                int curr_cost = pipes[i] + pipes[i + 1];
                // The merged pipe replaces the next slot so it takes part in the next merge
                pipes[i + 1] = curr_cost;
                // Add this merge to the running total
                cost = curr_cost + prev_cost;
            }

            return cost;
        }

        /** Driver that runs the sorting approach on two sample inputs. */
        public static void main(String[] args) {
            int[] pipes = {4, 3, 2, 6};
            System.out.println("Total cost for connecting pipes is " + SortingMinimumCost.minCost(pipes));
            int[] pipes1 = {1, 1, 2, 6};
            System.out.println("Total cost for connecting pipes1 is " + SortingMinimumCost.minCost(pipes1));
            int[] pipes2 = {1, 1, 1, 1};
            System.out.println("Total cost for connecting pipes2 is " + SortingMinimumCost.minCost(pipes2));
        }
    }

    /** Min-heap approach: always merges the two shortest pipes, which gives the true minimum cost. */
    static class MinHeapMinimumCost {
        /**
         * Repeatedly removes the two shortest pipes, adds their sum to the cost and pushes the
         * merged pipe back. The input array is not modified.
         *
         * Time complexity: O(n log n). Space complexity: O(n) for the heap.
         *
         * @param pipes lengths of the pipes
         * @param n     number of pipes (unused; kept for the original signature)
         * @return minimum total cost of connecting all pipes
         */
        static int minCost(int pipes[], int n) {

            int cost = 0;
            int minimum, secondMinimum;
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int pipe : pipes) {
                minHeap.add(pipe);
            }
            //Merge until a single pipe remains
            while (minHeap.size() > 1) {
                minimum = minHeap.poll();
                secondMinimum = minHeap.poll();
                cost += (minimum + secondMinimum);
                //The merged pipe goes back so it can be merged again later
                minHeap.add(minimum + secondMinimum);
            }
            return cost;
        }

        /** Driver that runs the min-heap approach on two sample inputs. */
        public static void main(String[] args) {
            int[] pipes = {4, 3, 2, 6};
            System.out.println("Total cost for connecting pipes is " + MinHeapMinimumCost.minCost(pipes, pipes.length));
            int[] pipes1 = {1, 1, 2, 6};
            System.out.println("Total cost for connecting pipes1 is " + MinHeapMinimumCost.minCost(pipes1, pipes1.length));
            int[] pipes2 = {1, 1, 1, 1};
            System.out.println("Total cost for connecting pipes2 is " + MinHeapMinimumCost.minCost(pipes2, pipes2.length));
        }
    }

    /** Runs the driver of each approach on the same sample inputs. */
    public static void main(String[] args) {
        System.out.println("Using Sorting approach:");
        SortingMinimumCost.main(args);
        System.out.println("Using MinHeap approach:");
        MinHeapMinimumCost.main(args);
    }
}