package org.educative.modules.module2.heaps;

import java.util.*;

/**
 * Given a positive integer num, you may swap any two digits of num that have the same parity
 * (i.e., both even digits or both odd digits).
 * Return the largest possible value of num after any number of swaps.
 * Constraints:
 * 1 <= num <= 10^9
 *
 *
 */
public class LargestNumberAfterDigitSwapByParity {

    /**
     * This method takes a positive integer num and returns the largest possible value of num after
     * any number of swaps of digits with the same parity.
     * It uses two priority queues to store odd and even digits separately,
     * and then constructs the largest number by replacing each digit with the largest available digit of the same parity.
     * Time complexity: O(n log n), where n is the number of digits in num (at most 10).
     * Space complexity: O(n), for storing the digits and the priority queues.
     * @param num a positive integer
     * @return the largest possible value of num after any number of swaps
     */
    public static int largestInteger(int num) {
        String numStr = Integer.toString(num);
        int[] digits = new int[numStr.length()];
        for (int i = 0; i < numStr.length(); i++) {
            digits[i] = numStr.charAt(i) - '0';
        }

        PriorityQueue<Integer> oddHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> evenHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int d : digits) {
            if (d % 2 == 0) {
                evenHeap.add(d);
            } else {
                oddHeap.add(d);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int d : digits) {
            if (d % 2 == 0) {
                result.append(evenHeap.poll());
            } else {
                result.append(oddHeap.poll());
            }
        }

        return Integer.parseInt(result.toString());
    }

    public static int largestInteger2 (int num) {

        // Replace this placeholder return statement with your code
        List<Integer> digits = new ArrayList<>();
        PriorityQueue<Integer> oddPQ = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> evenPQ = new PriorityQueue<>(Collections.reverseOrder());

        // num % 10 extracts digits right-to-left, so digits is stored in REVERSE order
        while ( num > 0) {
            int digit = num % 10;
            digits.add(digit);
            boolean isEven = (digit % 2 == 0);
            if (isEven) {
                evenPQ.add(digit);
            } else {
                oddPQ.add(digit);
            }
            num /= 10;
        }

        // Rebuild from the most significant digit (end of list) so the largest
        // digits of each parity are placed in the highest positions
        int result = 0;
        for (int i = digits.size() - 1; i >= 0; i--) {
            int d = digits.get(i);
            int next = (d % 2 == 0) ? evenPQ.poll() : oddPQ.poll();
            result = result * 10 + next;
        }

        return result;

    }

    public static void main(String[] args) {
        int[] testCases = {1234, 65875, 4321, 2468, 98123};
        for (int num : testCases) {
            System.out.println("\tInput number: " + num);
            System.out.println("\tOutput number: " + largestInteger(num));
            System.out.println("\tOutput number 2: " + largestInteger2(num));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
