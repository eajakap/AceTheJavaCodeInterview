package org.educative.modules.module3.algorithms.greedy;

import java.util.ArrayList;

/**
 * This class implements a greedy algorithm to determine the minimum number of coins needed to make change for a given amount.
 * It uses a predefined set of coin denominations and iteratively subtracts the largest possible denomination from the amount until the amount reaches zero.
 * The algorithm is efficient and works well for standard coin systems where the greedy choice property holds.
 * The simple greedy idea is to start from the largest possible coin available (in our case, it is a
 *  quarter, or 25 cents) and to keep adding coins while the remaining value is greater than 0.
 * Time Complexity: O(n), where n is the number of coin denominations.
 * Space Complexity: O(m), where m is the number of coins used to make change.
 */
public class CoinChangeMachine {
    // a public collection of available coins
    public static int [] coins = {25, 10, 5, 1};

    /**
     * This method returns the minimum number of coins needed to make change for a given amount.
     * @param amount The amount of money to make change for.
     * @return An ArrayList of integers representing the coins used to make change.
     */
    public static ArrayList<Integer> getMinCoins(int amount)  // function to recieve change in the form of coins
    {
        ArrayList<Integer> change = new ArrayList<Integer>();
        // iterate through the available coins, starting with the largest denomination
        for (int i = 0; i < coins.length && amount > 0; i++) {
            // while the current coin can be used to make change,
            // add it to the change list and subtract its value from the amount
            while (amount >= coins[i]) {
                amount -= coins[i];
                change.add(coins[i]);
            }
        }
        return change;
    }

    /**
     * The main method to test the getMinCoins function with different amounts.
     * It prints the amount and the corresponding coins used to make change.
     * @param args Command line arguments (not used).
     */
    public static void main(String args[])
    {
        // Play around with this amount to see how many coins you get!
        int amount = 1;
        System.out.println(amount + " --> " + getMinCoins(amount));

        amount = 17;
        System.out.println(amount + " --> " + getMinCoins(amount));

        amount = 33;
        System.out.println(amount + " --> " + getMinCoins(amount));

        amount = 99;
        System.out.println(amount + " --> " + getMinCoins(amount));
    }

}
