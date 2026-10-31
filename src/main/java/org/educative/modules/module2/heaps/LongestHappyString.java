package org.educative.modules.module2.heaps;

import java.util.PriorityQueue;

/**
 * This class provides a solution to generate the longest happy string using a greedy approach with a max heap (priority queue).
 * A happy string is defined as a string that does not contain three consecutive identical characters.
 * The algorithm uses a priority queue to always append the character with the highest remaining count, while ensuring that no three consecutive characters are the same.
 * The time complexity of this solution is O(n log k), where n is the total number of characters and k is the number of distinct characters (in this case, 3).
 * The space complexity is O(k) for the priority queue.
 */
class LongestHappyString {
    // Helper class for storing character counts
    static class Pair {
        int count;
        char ch;

        Pair(int count, char ch) {
            this.count = count;
            this.ch = ch;
        }
    }

    /*
     * Generates the longest happy string using a greedy approach with a max heap (priority queue).
     * A happy string is defined as a string that does not contain three consecutive identical characters.
     * Steps:
     * 1. Create a max heap (priority queue) to store the counts of 'a', 'b', and 'c' along with their respective characters.
     * 2. While the priority queue is not empty, pop the character with the highest count.
     * 3. If the last two characters in the result are the same as the current character,
     *      pop the next character from the priority queue and append it to the result.
     * 4. If the count of the current character is still greater than zero,
     *      push it back into the priority queue.
     * 5. Repeat until the priority queue is empty or no valid characters can be added to the result.
     * 6. Return the constructed happy string.
     * Time Complexity: O(n log k), where n is the total number of characters and k is the number of distinct characters (in this case, 3).
     * Space Complexity: O(k) for the priority queue.
     * @param a the count of character 'a'
     * @param b the count of character 'b'
     * @param c the count of character 'c'
     * @return the longest happy string that can be formed with the given counts of 'a', 'b', and 'c'
     */
    public String longestDiverseString(int a, int b, int c)
    {
        // Create a max heap (priority queue) to store the counts of 'a', 'b', and 'c' along with their respective characters.
        PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p2.count - p1.count);
        // Add the counts of 'a', 'b', and 'c' to the priority queue if they are greater than zero.
        if (a > 0) pq.add(new Pair(a, 'a'));
        if (b > 0) pq.add(new Pair(b, 'b'));
        if (c > 0) pq.add(new Pair(c, 'c'));
        // Initialize a StringBuilder to construct the result string.
        StringBuilder result = new StringBuilder();
        // While the priority queue is not empty, pop the character with the highest count.
        while (!pq.isEmpty()) {
            // Get the character with the highest count from the priority queue.
            Pair top = pq.poll();
            // Check if the last two characters in the result are the same as the current character.
            int len = result.length();
            if (len >= 2 && result.charAt(len - 1) == top.ch && result.charAt(len - 2) == top.ch) {
                // If the last two characters are the same as the current character,
                // pop the next character from the priority queue and append it to the result.
                if (pq.isEmpty()) break; // Priority queue is empty, no valid character can be added, so break the loop.
                // Get the next character with the highest count from the priority queue.
                Pair second = pq.poll();
                // Append the next character to the result.
                result.append(second.ch);
                // If the count of the next character is still greater than zero, push it back into the priority queue.
                if (--second.count > 0) pq.add(second);
                // Push the current character back into the priority queue for future consideration.
                pq.add(top);
            }
            else {
                // If the last two characters are not the same as the current character,
                result.append(top.ch);
                // If the count of the current character is still greater than zero, push it back into the priority queue.
                if (--top.count > 0) pq.add(top);
            }
        }

        return result.toString();
    }


    // Driver code
    public static void main(String[] args) {
        LongestHappyString solution = new LongestHappyString();

        int[][] testCases = {
                {1, 1, 7},
                {2, 2, 1},
                {7, 2, 0},
                {0, 0, 0},
                {10, 5, 3},
                {3, 3, 3}
        };

        int testCaseNum = 1;
        for (int[] testCase : testCases) {
            int a = testCase[0], b = testCase[1], c = testCase[2];
            System.out.println(testCaseNum++ + ".\t a: " + a + ", b: " + b + ", c: " + c);
            String result = solution.longestDiverseString(a, b, c);
            System.out.println("\n\t Longest Happy String: " + result);
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}