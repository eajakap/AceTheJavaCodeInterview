package org.educative.modules.module1.big_o_notation;

/**
 * This class is a placeholder for the Distinct3DigitsInRange problem.
 * The implementation of the solution will be added here.
 * Problem Statement:
 * Given a range of numbers, find all distinct 3-digit numbers that can be formed using the digits in that range.
 * Example:
 * Input: range = [100, 999]
 * Output: List of distinct 3-digit numbers
 * Constraints:
 * The range will always be between 100 and 999 inclusive.
 * Time Complexity: O(n), where n is the number of 3-digit numbers in the specified range.
 * Space Complexity: O(1), as we are using a constant amount of extra space for storing the distinct numbers.
 */
public class Distinct3DigitsInRange {
    /**
    Given a range of numbers [left, right], both ends inclusive, where 100 \le \text{left} \le \text{right} \le 999, count the number of three-digit numbers having all three digits pairwise distinct (meaning that no two digits are the same). It is guaranteed that all numbers within a range have exactly three digits.
    Note: You are not expected to provide the most optimal solution, but a solution with time complexity not worse than O(\text{right}^2) will fit within the execution time limit.
    Example
    For left = 876 and right = 890, the output should be solution(left, right) = 3.
    Explanation
    The numbers 876, 879, 890 are the only numbers in the range that have all three digits pairwise distinct.
    Input/Output

    [execution time limit] 3 seconds (java)
    [memory limit] 1 GB
    [input] integer left
    An integer representing the lower bound of the given number range.
    Guaranteed constraints: 100 \le \text{left} \le \text{right} \le 999.
    [input] integer right
    An integer representing the upper bound of the given number range.
    Guaranteed constraints: 100 \le \text{left} \le \text{right} \le 999.
    [output] integer
    The number of three-digit numbers in the given number range.
    */
    public int solution(int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            int hundreds = i / 100;
            int tens = (i / 10) % 10;
            int units = i % 10;
            if (hundreds != tens && hundreds != units && tens != units) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Distinct3DigitsInRange obj = new Distinct3DigitsInRange();
        int left = 876;
        int right = 890;
        int result = obj.solution(left, right);
        System.out.println("Count of distinct 3-digit numbers in range [" + left + ", " + right + "] is: " + result);
    }
}
