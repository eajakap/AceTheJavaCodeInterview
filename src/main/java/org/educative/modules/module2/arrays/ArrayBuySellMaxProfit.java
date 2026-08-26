package org.educative.modules.module2.arrays;
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
        int minPrice = prices[0]; // price on Day 0
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
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
        ArrayBuySellMaxProfit solution = new ArrayBuySellMaxProfit();
        int[] prices = {7, 1, 5, 3, 6, 4};
        int maxProfit = solution.maxProfit(prices);
        System.out.println("Max Profit: " + maxProfit);
    }
}
