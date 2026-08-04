package org.educative.module2.datastructures.arrays;

public class ArrayBuySellMaxProfit {

    // Time Complexity: 0(n), Space Complexity: O(1)
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = prices[0];
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
