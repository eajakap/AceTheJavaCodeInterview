package org.educative.modules.module3.algorithms.dp;

/**
 * Finds the length of the longest contiguous substring shared by two strings.
 *
 * A substring must use consecutive characters in both input strings. For example, the longest
 * common substring of "ABCDGH" and "ACDGHR" is "CDGH", with length 4. The nested classes show
 * brute-force, memoized, tabulated, and space-optimized approaches.
 */
public class LongestCommonSubstringSolution {

    /**
     * Recursively explores matching continuations and alternative starting positions.
     *
     * Time complexity is exponential in the lengths of the input strings because overlapping
     * subproblems are recomputed. The recursion stack uses O(m + n) space in the worst case.
     */
    static class BruteForceLongestCommonSubstring {
        /**
         * Computes the longest common substring length from the current pair of indices.
         *
         * Logical steps:
         * 1. Return the active match length when either string ends.
         * 2. If the current characters match, extend the active matching run.
         * 3. Restart matching after advancing in either string and keep the longest result.
         *
         * @param s1 first string
         * @param s2 second string
         * @param i1 current index in {@code s1}
         * @param i2 current index in {@code s2}
         * @param count length of the matching contiguous run so far
         * @return the longest common substring length reachable from this state
         */
        public static int longestCommonSubstrLengthRec(String s1, String s2, int i1, int i2, int count) {
            // Once either string ends, no further matching characters can be added.
            if (i1 == s1.length() || i2 == s2.length())
                return count;

            // Continue the current substring while corresponding characters match.
            if (s1.charAt(i1) == s2.charAt(i2))
                count = longestCommonSubstrLengthRec(s1, s2, i1 + 1, i2 + 1, count + 1);

            // Consider starting or continuing a match at later positions.
            int c1 = longestCommonSubstrLengthRec(s1, s2, i1, i2 + 1, 0);
            int c2 = longestCommonSubstrLengthRec(s1, s2, i1 + 1, i2, 0);

            return Math.max(count, Math.max(c1, c2));
        }

        /**
         * Starts the brute-force search at the beginning of both strings.
         *
         * @param s1 first string
         * @param s2 second string
         * @return the length of the longest common substring
         */
        public static int longestCommonSubstrLength(String s1, String s2)
        {
            return longestCommonSubstrLengthRec(s1, s2, 0, 0, 0);
        }

        /** Runs sample cases for the brute-force implementation. */
        public static void main(String args[])
        {
            String s1 = "0abc321";
            String s2 = "123abcdef";
            String s3 = "educative.io/expl";
            String s4 = "educative.io/edpr";
            System.out.println("Brute Force Approach for Longest Common Substring  Length Computation");
            System.out.println(longestCommonSubstrLength(s1, s2));
            System.out.println(longestCommonSubstrLength(s3, s4));
        }
    };

    /**
     * Uses memoization to cache recursive subproblems.
     *
     * The table tracks both string positions and the active matching-run length, using
     * O(m * n * min(m, n)) space. The worst-case running time is O(m * n * min(m, n)).
     */
    static class MemoizationLongestCommonSubstring {
        /**
         * Computes a recursive state, caching its result by both indices and the active run length.
         *
         * @param lookupTable memoization table, initialized with the not-computed marker -10
         * @param s1 first string
         * @param s2 second string
         * @param i1 current index in {@code s1}
         * @param i2 current index in {@code s2}
         * @param count length of the matching contiguous run so far
         * @return the longest common substring length reachable from this state
         */
        public static int longestCommonSubstrLengthRecursive(int[][][] lookupTable, String s1, String s2, int i1, int i2, int count)
        {
            // Return the active run when either string has been fully consumed.
            if (i1 == s1.length() || i2 == s2.length())
                return count;

            // Compute each state once, then reuse the cached maximum.
            if (lookupTable[i1][i2][count] == -10) {
                int c1 = count;
                // Extend the current run only when the characters at these positions match.
                if (s1.charAt(i1) == s2.charAt(i2))
                    c1 = longestCommonSubstrLengthRecursive(lookupTable, s1, s2, i1 + 1, i2 + 1, count + 1);
                // Restart the run when advancing either string independently.
                int c2 = longestCommonSubstrLengthRecursive(lookupTable, s1, s2, i1, i2 + 1, 0);
                int c3 = longestCommonSubstrLengthRecursive(lookupTable, s1, s2, i1 + 1, i2, 0);
                lookupTable[i1][i2][count] = Math.max(c1, Math.max(c2, c3));
            }
            return lookupTable[i1][i2][count];
        }

        /**
         * Allocates and initializes the memo table, then starts at the beginning of both strings.
         *
         * @param s1 first string
         * @param s2 second string
         * @return the length of the longest common substring
         */
        public static int longestCommonSubstrLength(String s1, String s2)
        {
            // A state is identified by both indices and the current matching-run length.
            int size1 = s1.length(), size2 = s2.length();
            int maxLength = Math.max(size1, size2);
            int [][][] lookupTable = new int [size1][][];

            for (int i = 0; i < size1; i++) {
                lookupTable[i] = new int [size2][];
                for (int j = 0; j < size2 ; j++) {
                    lookupTable[i][j] = new int[maxLength];
                    // Negative sentinel distinguishes an uncomputed state from valid lengths.
                    for (int k = 0; k < maxLength; k++)
                        lookupTable[i][j][k] = -10;
                }
            }
            return longestCommonSubstrLengthRecursive(lookupTable, s1, s2, 0, 0, 0);
        }

        /** Runs sample cases for the memoized implementation. */
        public static void main(String args[])
        {
            String S1 = "www.educative.io/explore";
            String S2 = "educative.io/edpresso";
            String S3 = "0abc321";
            String S4 = "123abcdef";
            System.out.println("Memoization Approach for Longest Common Substring  Length Computation");
            System.out.println(longestCommonSubstrLength(S3, S4));
            System.out.println(longestCommonSubstrLength(S1, S2));
        }
    };

    /**
     * Builds a two-dimensional dynamic programming table from shorter prefixes to longer ones.
     *
     * Time complexity is O(m * n), and the table uses O(m * n) space.
     */
    static class TabulationLongestCommonSubstring {
        /**
         * Returns the length of the longest common substring using a prefix table.
         *
         * For matching characters, the common suffix length extends the diagonal predecessor.
         * Nonmatching cells remain zero because a substring must be contiguous.
         *
         * @param s1 first string
         * @param s2 second string
         * @return the length of the longest common substring
         */
        public static int longestCommonSubstrLength(String s1, String s2)
        {
            // Cell [i][j] records the matching suffix length for prefixes ending at i - 1 and j - 1.
            int[][] lookupTable = new int[s1.length()+1][];

            // Initialize the extra prefix row and column for empty-string boundary cases.
            for(int i = 0; i <= s1.length(); i++)
                lookupTable[i] = new int[s2.length()+1];


            for (int i = 0; i < s1.length()+1; i++) {
                for (int j = 0; j < s2.length()+1; j++) {
                    lookupTable[i][j] = 0;
                }
            }
            int maxLength = 0;
            // Fill the table from shorter prefixes to longer prefixes.
            for (int i = 1; i <=s1.length(); i++) {
                for (int j = 1; j <= s2.length(); j++) {
                    // A match extends the common suffix ending at the preceding character pair.
                    if (s1.charAt(i-1) == s2.charAt(j-1)) {
                        lookupTable[i][j] = 1 + lookupTable[i - 1][j - 1];
                        maxLength = Math.max(maxLength, lookupTable[i][j]);
                    }
                }
            }
            return maxLength;
        }

        /** Runs sample cases for the tabulated implementation. */
        public static void main(String args[])
        {
            String S1 = "www.educative.io/explore";
            String S2 = "educative.io/edpresso";
            String S3 = "0abc321";
            String S4 = "123abcdef";
            System.out.println("Tabulation Approach for Longest Common Substring  Length Computation");
            System.out.println(longestCommonSubstrLength(S3, S4));
            System.out.println(longestCommonSubstrLength(S1, S2));
        }
    };

    /**
     * Stores only two rows of the prefix table, reducing auxiliary space to O(n), where n is
     * the length of the second string. Time complexity remains O(m * n).
     */
    static class SpaceOptimizedLongestCommonSubstring {
        /**
         * Computes the longest common substring length using alternating rows.
         *
         * Each cell in the current row depends only on the diagonal cell in the previous row.
         * Resetting a cell before use ensures mismatches break the contiguous substring.
         *
         * @param s1 first string
         * @param s2 second string
         * @return the length of the longest common substring
         */
        public static int longestCommonSubstrLength(String s1, String s2)
        {
            int[][] lookupTable = new int[s1.length()+1][];
            for(int i = 0; i <= s1.length(); i++)
                lookupTable[i] = new int[s2.length()+1];

            // Initializing all values in lookupTable to zero
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < s2.length()+1; j++) {
                    lookupTable[i][j] = 0;
                }
            }
            int maxLength = 0;
            for(int i=1; i <= s1.length(); i++)
            {
                for(int j=1; j <= s2.length(); j++)
                {
                    // Clear this column in the active row before computing the current pair.
                    lookupTable[i%2][j] = 0;
                    if(s1.charAt(i-1) == s2.charAt(j-1)) {
                        // Extend the diagonal match from the preceding row.
                        lookupTable[i%2][j] = 1 + lookupTable[(i-1)%2][j-1];
                        maxLength = Math.max(maxLength, lookupTable[i%2][j]);
                    }
                }
            }
            return maxLength;
        }

        /** Runs sample cases for the space-optimized implementation. */
        public static void main(String args[])
        {
            String S1 = "www.educative.io/explore";
            String S2 = "educative.io/edpresso";
            String S3 = "0abc321";
            String S4 = "123abcdef";
            System.out.println("Space Optimized Approach for Longest Common Substring  Length Computation");
            System.out.println(longestCommonSubstrLength(S3, S4));
            System.out.println(longestCommonSubstrLength(S1, S2));
        }
    };

    /** Runs the sample cases for all four implementations. */
    public static void main(String[] args) {
        BruteForceLongestCommonSubstring.main(args);
        MemoizationLongestCommonSubstring.main(args);
        TabulationLongestCommonSubstring.main(args);
        SpaceOptimizedLongestCommonSubstring.main(args);
    }
}
