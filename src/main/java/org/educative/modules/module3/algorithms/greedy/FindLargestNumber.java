package org.educative.modules.module3.algorithms.greedy;

/**
 * Explore how to design and implement a greedy algorithm in Java to find the largest possible number for a given number of digits and digit sum.
 * Problem:
 * Given a number of digits and a digit sum, find the largest possible number that can be formed.
 * Problem:
 * A girl lost the key to her locker (Note: The key is only numeric).
 * She remembers the number of digits N and the sum S of all the digits of her password.
 * She also knows that her password is the largest number of N digits that can be possible with the given sum S.
 * Implement a function that helps her retrieve her key.
 * Input: Two integers N and S, where N is the number of digits and S is the sum of all the digits.
 * Output: The largest number of N digits with a sum of S. If no such number exists, the methods print
 * a message indicating that no result is possible.
 * Example:
 * Input: N = 3, S = 20
 * Output: 992
 * Explanation: The largest number with 3 digits and a sum of 20 is 992
 * Input: N = 2, S = 9
 * Output: 90
 * Explanation: The largest number with 2 digits and a sum of 9 is 90
 */
public class FindLargestNumber {

    /**
     * Enumerates every number of the requested length and keeps the largest one with the target sum.
     *
     * <p>Time complexity: O(n * 10^n), where n is the digit count, because each n-digit candidate
     * is checked by summing its digits. Space complexity: O(1) auxiliary space.
     */
    static class BruteForceMaxNumber {

        /**
         * Calculates the sum of the decimal digits of a non-negative integer.
         *
         * @param num the number whose digits are summed
         * @return the sum of its decimal digits
         */
        public static int findSumOfDigits(int num) {

            int sum = 0;
            while (num != 0) {
                sum = sum + num % 10;
                num = num / 10;
            }
            return sum;
        }

        /**
         * Prints the largest number with the requested digit count and digit sum, if one exists.
         *
         * <p>Inputs are expected to have a positive digit count and a non-negative sum. A sum above
         * 9 times the digit count, or zero for a multi-digit number, is reported as impossible.
         *
         * @param numberOfDigits the required number of digits
         * @param sumOfDigits the required sum of the digits
         */
        public static void findLargestNumber(int numberOfDigits, int sumOfDigits) {
            int max = 0;
            int startRange = (int) Math.pow(10, (numberOfDigits - 1));
            int endRange = (int) Math.pow(10, numberOfDigits);

            if (sumOfDigits == 0) {
                if (numberOfDigits == 1)
                    System.out.println("Largest number is " + 0);
                else
                    System.out.println("Largest number is Not possible");
                return;
            }

            // sumOfDigits is greater than the maximum possible sum.
            if (sumOfDigits > 9 * numberOfDigits) {
                System.out.println("Largest number is Not possible");
                return;
            }

            while (startRange < endRange) {
                // Keep the greatest candidate whose digits add up to the requested sum.
                if (findSumOfDigits(startRange) == sumOfDigits) {
                    if (max < startRange)
                        max = startRange;
                }
                startRange++;
            }

            System.out.println("Largest number is " + max);
        }

        /** Runs sample cases for the exhaustive-search implementation. */
        public static void main(String[] args) {
            int sumOfDigits = 20;
            int numberOfDigits = 3;

            System.out.println("If sum of digits is 20 and number of digits is 3 then ");
            BruteForceMaxNumber.findLargestNumber(numberOfDigits, sumOfDigits);
            System.out.println();

            //Example 2
            sumOfDigits = 100;
            numberOfDigits = 2;

            System.out.println("If sum of digits is 100 and number of digits is 2 then ");
            BruteForceMaxNumber.findLargestNumber(numberOfDigits, sumOfDigits);

        }
    }

    /**
     * Constructs the number from left to right, assigning each position the largest feasible digit.
     *
     * <p>Choosing the largest possible digit at each position maximizes the number lexicographically.
     * Time complexity: O(n), where n is the digit count. Space complexity: O(n) for the digit array.
     */
    static class GreedyApproachMaxNumber {
        /**
         * Prints the largest number with the requested digit count and digit sum, if one exists.
         *
         * <p>Inputs are expected to have a positive digit count and a non-negative sum. If no valid
         * number exists, this method prints {@code None}.
         *
         * @param number_of_digits the required number of digits
         * @param sum_of_digits the required sum of the digits
         */
        public static void findLargestNumber(int number_of_digits, int sum_of_digits) {
            int[] result = new int[number_of_digits];

            if (sum_of_digits == 0) {
                if (number_of_digits == 1) {
                    System.out.print(0);
                } else {
                    System.out.print("None");
                }
                return;
            }

            if (sum_of_digits > 9 * number_of_digits) {
                System.out.print("None");
                return;
            }

            for (int i = 0; i < number_of_digits; i++) {
                // Fill higher place values first to produce the largest possible number.
                if (sum_of_digits >= 9) {
                    result[i] = 9;
                    sum_of_digits -= 9;
                } else {
                    result[i] = sum_of_digits;
                    sum_of_digits = 0;
                }
            }
            for (int i = 0; i < number_of_digits; i++) {
                // Print each digit in order; converting to an integer could drop leading zeroes.
                System.out.print(result[i]);
            }
        }

        /** Runs sample cases for the greedy implementation. */
        public static void main(String[] args) {
            int sumOfDigits = 20;
            int numberOfDigits = 3;

            System.out.println("If sum of digits is 20 and number of digits is 3 then ");
            GreedyApproachMaxNumber.findLargestNumber(numberOfDigits, sumOfDigits);
            System.out.println();

            //Example 2
            sumOfDigits = 100;
            numberOfDigits = 2;

            System.out.println("If sum of digits is 100 and number of digits is 2 then ");
            GreedyApproachMaxNumber.findLargestNumber(numberOfDigits, sumOfDigits);
        }
    }
}
