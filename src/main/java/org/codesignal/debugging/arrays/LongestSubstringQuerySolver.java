package org.codesignal.debugging.arrays;

import java.util.Arrays;

/**
 * Answers queries about the longest substring that contains neither of two
 * specified lowercase letters.
 */
public class LongestSubstringQuerySolver {

    /**
     * For each query, returns the length of the longest contiguous substring
     * of {@code S} that contains neither queried character.
     *
     * <p>The method precomputes answers for every pair of lowercase letters.
     * For each pair, it scans {@code S} once, tracking the longest run of
     * characters that are not either letter. Query answers are then retrieved
     * from this table in constant time.</p>
     *
     * @param S the string to examine; expected to contain lowercase English letters
     * @param Q queries, each containing a pair of lowercase English letters
     * @return an array containing one maximum substring length per query
     */
    public static int[] solution(String S, char[][] Q) {
        int[][] best = new int[26][26];
        for (int start = 0; start < 26; start++) {
            for (int end = start + 1; end < 26; end++) {
                int cur = 0, max = 0;
                for (int i = 0; i < S.length(); i++) {
                    int c = S.charAt(i) - 'a';
                    if (c == start || c == end) {
                        // Either queried character breaks the current valid run.
                        cur = 0;
                    } else {
                        // Extend this run and update the best length for the pair.
                        max = Math.max(max, ++cur);
                    }
                }
                best[start][end] = best[end][start] = max;
            }
        }

        int[] res = new int[Q.length];
        for (int i = 0; i < Q.length; i++) {
            res[i] = best[Q[i][0] - 'a'][Q[i][1] - 'a'];
        }
        return res;
    }

    /**
     * Prints sample results for two sets of queries.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution("abcccacba",
                new char[][]{{'a', 'b'}, {'b', 'c'}})));            // [3, 1]
        System.out.println(Arrays.toString(solution("intelliaiassistant",
                new char[][]{{'a', 'i'}, {'n', 't'}})));            // [5, 11]
    }
}
