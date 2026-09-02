package org.educative.modules.module1.big_o_notation;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Time Complexity: O(n log n), where n is the length of the array.
 * Space Complexity: O(n), as we are using a priority queue to merge the sorted arrays.
 * This program demonstrates the merge sort algorithm on an array of integers.
 */
public class ArrayMergeSortDemo {
        private static int SIZE = 100;
        private static Random random = new Random(System.currentTimeMillis());
        static private int[] input = new int[SIZE];
        static PriorityQueue<Integer> q = new PriorityQueue<>(SIZE);

        private static void mergeSort(int start, int end, int[] input) {

            if (start >= end) {
                return;
            } else if (start + 1 == end) {
                // if there are only two elements, sort them
                if (input[start] > input[end]) {
                    // swap the two elements
                    int temp = input[start];
                    input[start] = input[end];
                    input[end] = temp;
                }
                return;
            }

            // find the one third of the array
            int oneThird = (end - start) / 3;

            // sort first part
            mergeSort(start, start + oneThird, input);

            // sort second part
            mergeSort(start + oneThird + 1, start + 1 + (2 * oneThird), input);

            // sort third part
            mergeSort(start + 2 + (2 * oneThird), end, input);

            // merge the three sorted arrays using a priority queue
            int k;

            for (k = start; k <= end; k++) {
                q.add(input[k]);
            }

            k = start;
            while (!q.isEmpty()) {
                input[k] = q.poll();
                k++;
            }
        }

        private static void printArray(int[] input) {
                System.out.println();
                for (int i = 0; i < input.length; i++)
                    System.out.print(" " + input[i] + " ");
                System.out.println();
        }

        private static void createTestData() {
            for (int i = 0; i < SIZE; i++) {
                input[i] = random.nextInt(10000);
            }
        }

        public static void main( String args[] ) {
            createTestData();
            long start = System.currentTimeMillis();
            mergeSort(0, input.length - 1, input);
            long end = System.currentTimeMillis();
            System.out.println("Time taken = " + (end - start));
            printArray(input);
        }

}
