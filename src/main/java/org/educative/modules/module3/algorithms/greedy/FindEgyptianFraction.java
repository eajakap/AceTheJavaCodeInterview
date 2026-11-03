package org.educative.modules.module3.algorithms.greedy;

/**
 * Explore how to design and implement a greedy algorithm to express any positive fraction as a sum of unique unit fractions,
 * known as Egyptian fractions.
 * Every positive fraction can be represented as the sum of its unique unit fractions.
 * A fraction is a unit fraction if the numerator is 1 and the denominator is a positive integer.
 * For example, 1/3 is a unit fraction. Such a representation is called Egyptian fraction.
 * Egyptian Fraction:
 * A fraction of the form 1/x1 + 1/x2 + 1/x3 + ... + 1/xn, where x1, x2, x3, ..., xn are distinct positive integers.
 * Given a number n, find the Egyptian Fraction representation of n.
 * Input: A positive fraction n = p/q, where p and q are positive integers.
 * Output: A list of distinct positive integers x1, x2, x3, ..., xn such that n = 1/x1 + 1/x2 + 1/x3 + ... + 1/xn.
 * Example:
 * Input: n = 2/3
 * Output: 1/2 + 1/6
 * Explanation: 2/3 = 1/2 + 1/6
 * Input: n = 3/4
 * Output: 1/2 + 1/4
 * Explanation: 3/4 = 1/2 + 1/4
 *
 * Approach (greedy, Fibonacci-Sylvester): repeatedly subtract the largest unit fraction
 * not exceeding the remaining fraction p/q, which is 1/ceil(q/p), and continue with the
 * remainder until it becomes a unit fraction itself.
 */
public class FindEgyptianFraction {
    /** Egyptian fraction printing for a positive fraction. */
    static class Fraction {
        /**
         * Prints the Egyptian fraction representation of {@code numerator/denominator}
         * to standard output, with terms separated by " , ".
         * If the fraction is improper, the whole-number part is printed first.
         * Does nothing if either argument is zero. Arguments are expected to be positive;
         * very large inputs may overflow {@code int} because the denominator grows quickly.
         * Time Complexity: The time complexity is O(logn) where n is the denominator of the fraction.
         *      The reason for this is that in each recursive call, we are reducing the fraction by subtracting the
         *      largest unit fraction not exceeding the remaining fraction. This reduction is done in the form of division,
         *      which reduces the size of the denominator significantly in each step.
         * Space Complexity: O(logn) due to the recursive call stack.
         * @param numerator   numerator of the fraction
         * @param denominator denominator of the fraction
         */
        public static void printEgyptianFraction(int numerator, int denominator) {
            //Nothing to print for an invalid or zero fraction
            if (denominator == 0 || numerator == 0) {
                return;
            }
            //Already a unit fraction (after reduction): print it and stop
            if (denominator % numerator == 0) {
                System.out.print("1/" + denominator / numerator);
                return;
            }
            //Whole number: print it and stop
            if (numerator % denominator == 0) {
                System.out.println(numerator / denominator);
                return;
            }
            //Improper fraction: print the whole part, then continue with the proper remainder
            if (numerator > denominator) {
                System.out.println(numerator / denominator + " , ");
                printEgyptianFraction(numerator % denominator, denominator);
                return;
            }
            //Largest unit fraction not exceeding the fraction is 1/ceil(denominator/numerator)
            int n = denominator / numerator + 1;
            System.out.print("1/" + n + " , ");
            //Recurse on the remainder: numerator/denominator - 1/n
            printEgyptianFraction(numerator * n - denominator, denominator * n);
        }

        /** Driver that prints the Egyptian fraction representation of 6/14 and 2/3. */
        public static void main(String[] args){

            //Example 1
            int numerator = 6, denominator = 14;
            System.out.print("Egyptian Fraction Representation of " + numerator + "/" + denominator + " is\n ");
            Fraction.printEgyptianFraction(numerator, denominator);
            System.out.println();

            //Example 2
            numerator = 2;
            denominator = 3;
            System.out.print("Egyptian Fraction Representation of " + numerator + "/" + denominator + " is\n ");
            Fraction.printEgyptianFraction(numerator, denominator);

        }
    }
}
