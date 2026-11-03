package org.educative.modules.module3.algorithms.dp;

/**
 * Solves the 0/1 knapsack problem using recursive and dynamic programming approaches.
 *
 * Each item has a weight and a profit, and can be selected at most once. The goal is to maximize
 * total profit without exceeding the knapsack capacity. The implementations expect corresponding
 * profit and weight arrays of equal length, non-negative capacity, and non-negative item weights
 * and profits. They return {@code 0} for a non-positive capacity, no items, or mismatched declared
 * array lengths.
 *
 * For the dynamic programming approaches, let {@code n} be the number of items and {@code W} the
 * capacity. Their time complexity is O(nW); the tabulated approach uses O(nW) space, while the
 * space-optimized approach uses O(W).
 */
public class KnapsackProblemSolution {
    /**
     * Exhaustively tries including or excluding each item.
     *
     * Time complexity: O(2^n) in the worst case. Space complexity: O(n) for the recursion stack.
     */
    static class BruteForceKnapsackProblem {
        /**
         * Returns the greatest profit obtainable from the items starting at {@code currentIndex}.
         *
         * Logical steps:
         * 1. Stop when capacity is exhausted or there are no items left to consider.
         * 2. If the current item fits, calculate the profit from including it.
         * 3. Calculate the profit from excluding it and return the better choice.
         *
         * @param profits profit for each item
         * @param profitsLength number of items in {@code profits}
         * @param weights weight for each item
         * @param weightsLength number of items in {@code weights}
         * @param capacity remaining knapsack capacity
         * @param currentIndex index of the next item to consider
         * @return maximum profit achievable from the remaining items
         * @implNote This implementation explores both choices for every item and does not cache
         *           intermediate results.
         */
        public static int knapSack(int profits[], int profitsLength, int weights[], int weightsLength, int capacity, int currentIndex) {
            // No capacity or items remain, or the supplied array lengths do not match.
            if (capacity <= 0 || currentIndex >= profitsLength || currentIndex < 0 || weightsLength != profitsLength) {
                return 0;
            }

            // Choice 1: take the item only when it fits, then continue with the next item.
            int profit1 = 0;
            if (weights[currentIndex] <= capacity) {
                profit1 = profits[currentIndex] +
                        knapSack(profits, profitsLength, weights, weightsLength, capacity - weights[currentIndex], currentIndex + 1);
            }
            // Choice 2: leave the item and continue with the same capacity.
            int profit2 = knapSack(profits, profitsLength, weights, weightsLength,  capacity, currentIndex + 1);

            // Keep whichever choice yields more profit.
            return Math.max(profit1, profit2);
        }

        /** Runs sample inputs for the exhaustive recursive solution. */
        public static void main(String args[])
        {
            int profits[] = {1, 6, 10, 16}; // The values of the jewelry
            int weights[] = {1, 2, 3, 5}; // The weight of each
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4,  7, 0));
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4, 6, 0));
        }
    };

    /**
     * Top-down dynamic programming: recursively explores choices and caches subproblem results.
     *
     * Time and space complexity: O(nW) in the usual memoized case, with O(nW) table space and
     * up to O(n) recursion depth. The table uses zero as its "not computed" marker, so states whose
     * computed optimum is zero may be recalculated.
     */
    static class TopDownKnapsackProblem {
        /**
         * Computes a subproblem using the supplied memoization table.
         *
         * Logical steps:
         * 1. Return zero when no items or capacity remain.
         * 2. Return the cached result if this item-capacity state was already solved.
         * 3. Compute the best profit both including and excluding the current item.
         * 4. Store and return the larger result.
         *
         * @param lookupTable memoization table indexed by item and remaining capacity
         * @param profits profit for each item
         * @param profitsLength number of items in {@code profits}
         * @param weights weight for each item
         * @param weightsLength number of items in {@code weights}
         * @param capacity remaining knapsack capacity
         * @param currentIndex index of the next item to consider
         * @return maximum profit achievable from the remaining items
         * @implNote A memoized state is identified by the item index and remaining capacity.
         *           Zero is used as the unsolved marker, so states with a true optimum of zero
         *           can be recomputed.
         */
        public static int knapsackRecursive(int [][] lookupTable, int profits[], int profitsLength, int weights[], int weightsLength, int capacity, int currentIndex) {

            // No capacity or items remain, or the supplied array lengths do not match.
            if (capacity <= 0 || currentIndex >= profitsLength || currentIndex < 0 || weightsLength != profitsLength)
                return 0;

            // Reuse a previously computed non-zero result.
            if (lookupTable[currentIndex][capacity] != 0)
                return lookupTable[currentIndex][capacity];

            // Choice 1: include the item if it fits and solve the remaining subproblem.
            int profit1 = 0;
            if (weights[currentIndex] <= capacity)
                profit1 = profits[currentIndex] + knapsackRecursive(lookupTable, profits, profitsLength, weights, weightsLength,
                        capacity - weights[currentIndex], currentIndex + 1);

            // Choice 2: exclude the item and retain the current capacity.
            int profit2 = knapsackRecursive(lookupTable, profits, profitsLength, weights, weightsLength, capacity, currentIndex + 1);

            // Cache and return the better of the two choices.
            lookupTable[currentIndex][capacity] = Math.max(profit1, profit2);
            return lookupTable[currentIndex][capacity];
        }

        /**
         * Allocates the memoization table and starts the top-down search at the first item.
         *
         * Logical steps: create one row per item and one column per capacity, initialize all
         * cells to zero, then delegate to the recursive memoized search.
         *
         * @param profits profit for each item
         * @param profitsLength number of items in {@code profits}
         * @param weights weight for each item
         * @param weightsLength number of items in {@code weights}
         * @param capacity maximum knapsack capacity
         * @return maximum profit achievable without exceeding {@code capacity}
         * @implNote The declared lengths must match the corresponding array lengths. The memo table
         *           is initialized with zero, which also serves as the marker for an unsolved state.
         */
        public static int knapSack(int profits[], int profitsLength, int weights[], int weightsLength, int capacity)
        {
            // Each cell represents the best result for one item index and one capacity.
            int lookupTable[][] = new int [profitsLength][];

            for (int i = 0; i < profitsLength; i++) {
                lookupTable[i] = new int[capacity + 1];
                // Initialize each item-capacity subproblem as unsolved.
                for (int j = 0; j < capacity + 1; j++)
                    lookupTable[i][j] = 0;
            }
            return knapsackRecursive(lookupTable, profits, profitsLength, weights, weightsLength, capacity, 0);
        }

        /** Runs sample inputs for the top-down memoized solution. */
        public static void main(String args[])
        {
            int profits[] = {1, 6, 10, 16}; // The values of the jewelry
            int weights[] = {1, 2, 3, 5}; // The weight of each
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4,  7));
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4, 6));
        }
    };

    /**
     * Bottom-up dynamic programming using a table for every item prefix and capacity.
     *
     * Time complexity: O(nW). Space complexity: O(nW).
     */
    static class BottomsUpKnapsackProblem {
        /**
         * Fills a table where each cell is the best profit using the first {@code i} items at capacity
         * {@code w}.
         *
         * Logical steps:
         * 1. Initialize the zero-item and zero-capacity cases to zero.
         * 2. For each item and capacity, choose between including the item (if it fits) and excluding it.
         * 3. Return the result for all items and the requested capacity.
         *
         * @param profits profit for each item
         * @param profitsLength number of items in {@code profits}
         * @param weights weight for each item
         * @param weightsLength number of items in {@code weights}
         * @param capacity maximum knapsack capacity
         * @return maximum profit achievable without exceeding {@code capacity}
         * @implNote The first table dimension represents item prefixes, so each transition reads
         *           from the preceding row and cannot reuse the same item.
         */
        public static int knapSack(int profits[], int profitsLength, int weights[], int weightsLength, int capacity) {
            // Basic checks
            if (capacity <= 0 || profitsLength == 0 || weightsLength != profitsLength)
                return 0;

            int i, w;
            int[][]lookupTable = new int[profitsLength + 1][capacity + 1];

            // Build solutions for increasing item counts and capacities.
            for (i = 0; i <= profitsLength; i++) {
                for (w = 0; w <= capacity; w++) {
                    // With no items or no capacity, the best profit is zero.
                    if (i == 0 || w == 0)
                        lookupTable[i][w] = 0;
                    else if (weights[i - 1] <= w)
                        // Compare including this item with the best result that excludes it.
                        lookupTable[i][w] = Math.max(profits[i - 1] + lookupTable[i - 1][w - weights[i - 1]], lookupTable[i - 1][w]);
                    else
                        // The item does not fit, so carry forward the result without it.
                        lookupTable[i][w] = lookupTable[i - 1][w];
                }
            }

            return lookupTable[profitsLength][capacity];
        }

        /** Runs sample inputs for the bottom-up tabulated solution. */
        public static void main(String args[])
        {
            int profits[] = {1, 6, 10, 16}; // The values of the jewelry
            int weights[] = {1, 2, 3, 5}; // The weight of each
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4,  7));
            System.out.println("Total knapsack profit ---> " + knapSack(profits, 4, weights, 4, 6));
        }
    };

    /**
     * Bottom-up dynamic programming that stores results for only the current item prefix.
     *
     * Time complexity: O(nW). Space complexity: O(W). Capacities are processed in descending
     * order so an item cannot be reused during its own iteration.
     */
    static class SpaceOptimizationKnapsackProblem {
        /**
         * Returns the maximum profit using a one-dimensional capacity table.
         *
         * Logical steps:
         * 1. Initialize each capacity with the first item's profit when that item fits.
         * 2. For each remaining item, scan capacities from high to low.
         * 3. At each capacity, keep the greater of including or excluding the item.
         * 4. Return the best result at the requested capacity.
         *
         * @param profits profit for each item
         * @param profitsLength number of items in {@code profits}
         * @param weights weight for each item
         * @param weightsLength number of items in {@code weights}
         * @param capacity maximum knapsack capacity
         * @return maximum profit achievable without exceeding {@code capacity}
         * @implNote Capacities must be updated in descending order for the 0/1 constraint.
         */
        static int Knapsack(int profits[], int profitsLength, int weights[], int weightsLength, int capacity) {
            // basic checks
            if (capacity <= 0 || profitsLength == 0 || weightsLength != profitsLength)
                return 0;

            int[] lookupTable = new int[capacity + 1];
            // Initialize the table with the first item's profit wherever that item fits.
            for (int c = 0; c <= capacity; c++) {
                if (weights[0] <= c)
                    lookupTable[c] = profits[0];
            }

            // Incorporate each remaining item into the best results for all capacities.
            for (int i = 1; i < profitsLength; i++) {
                // Descending order preserves previous-item results and prevents reusing this item.
                for (int c = capacity; c >= 0; c--) {
                    int profit1 = 0, profit2 = 0;
                    // Choice 1: include the item if it fits.
                    if (weights[i] <= c)
                        profit1 = profits[i] + lookupTable[c - weights[i]];
                    // Choice 2: exclude the item and keep the current table value.
                    profit2 = lookupTable[c];
                    lookupTable[c] = Math.max(profit1, profit2);
                }
            }
            return lookupTable[capacity];
        }

        /** Runs sample inputs for the one-dimensional dynamic programming solution. */
        public static void main(String args[]) {
            int profits[] = {1, 6, 10, 16}; // The values of the jewelry
            int weights[] = {1, 2, 3, 5}; // The weight of each
            System.out.println("Total knapsack profit ---> " + Knapsack(profits, 4, weights, 4, 7));
            System.out.println("Total knapsack profit ---> " + Knapsack(profits, 4, weights, 4, 6));
        }

    };

    /** Runs a sample through all four implementations and prints their results. */
    public static void main(String args[]) {
        int profits[] = {1, 6, 10, 16}; // The values of the jewelry
        int weights[] = {1, 2, 3, 5}; // The weight of each
        System.out.println("Total knapsack profit ---> Brute Force Approach-->" + BruteForceKnapsackProblem.knapSack(profits, 4, weights, 4, 7, 0));
        System.out.println("Total knapsack profit ---> Top Down Approach-->" + TopDownKnapsackProblem.knapSack(profits, 4, weights, 4, 7));
        System.out.println("Total knapsack profit ---> Bottom Up Approach-->" + BottomsUpKnapsackProblem.knapSack(profits, 4, weights, 4, 7));
        System.out.println("Total knapsack profit ---> Space Optimization Approach-->" + SpaceOptimizationKnapsackProblem.Knapsack(profits, 4, weights, 4, 7));
    }
}
