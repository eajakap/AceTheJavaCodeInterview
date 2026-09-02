package org.educative.modules.module2.arrays;

import java.util.Arrays;

/**
 * This class provides a solution to the problem of finding the maximum profit from buying and selling stocks.
 * The input is an array of stock prices, where the index represents the day and the value represents the price
 * of the stock on that day.
 * The goal is to determine the maximum profit that can be achieved by buying on one day and selling
 * on another day after the buying day.
 */
public class ArrayBuySellMaxProfit {

    // Time Complexity: O(n), Space Complexity: O(1)
    public int maxProfit(int[] prices) {
        int maxProfit = 0; // compute the maximum profit
        int minPrice = prices[0]; // price on Day 0 - BUY PRICE (DAY - 0)
        for (int i = 1; i < prices.length; i++) {
            // Update maximum profit seen so far
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            // Update minimum price seen so far
            minPrice = Math.min(minPrice, prices[i]);
//            if (prices[i] < minPrice) {
//                minPrice = prices[i];
//            }
//            if (prices[i] > prices[i - 1]) {
//                int profit = prices[i] - minPrice;
//                maxProfit = Math.max(maxProfit, profit);
//            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        ArrayBuySellMaxProfit sol = new ArrayBuySellMaxProfit();

        int[][] testCases = {
                {2, 4, 1},
                {1, 2, 3, 4, 5},
                {3, 3, 3, 3, 3},
                {10, 8, 2, 9, 1, 7},
                {5}
        };

        int y = 1;
        for (int[] prices : testCases) {
            int result = sol.maxProfit(prices);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(prices));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
