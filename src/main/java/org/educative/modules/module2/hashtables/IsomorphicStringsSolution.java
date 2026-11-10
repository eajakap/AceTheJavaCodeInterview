package org.educative.modules.module2.hashtables;

import java.util.*;

/**
 * Isomorphic Strings
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character, but a character may map to itself.
 * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true
 * Example 2:
 * Input: s = "foo", t = "bar"
 * Output: false
 * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 * Constraints:
 * 1 <= s.length <= 5 * 10^4
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */
public class IsomorphicStringsSolution {
    /**
     * Determines if two strings are isomorphic.
     * Steps to solve the problem:
     * 1. Check if the lengths of the strings are equal. If not, they cannot be isomorphic.
     * 2. Create two hash maps to store the character mappings from s to t and from t to s.
     * 3. Iterate through the characters of both strings simultaneously. For each character pair (charS, charT):
     *    a. If charS is already mapped to a different character than charT, return false.
     *    b. If charT is already mapped to a different character than charS, return false.
     * 4. If no conflicts are found, the strings are isomorphic.
     * 5. Return true if the strings are isomorphic, false otherwise.
     * Time complexity: O(n), where n is the length of the strings.
     * Space complexity: O(n), where n is the number of unique characters in the strings
     * @param s the first string
     * @param t the second string
     * @return true if the strings are isomorphic, false otherwise
     */

    public boolean isIsomorphic(String s, String t) {
        // Edge case: if either string is empty, they are considered isomorphic
        if (s.isEmpty() && t.isEmpty()) {
            return true;
        } else if (s.length() != t.length()) {
            return false;
        }
        // HashMap to store mapping from s -> t
        Map<Character, Character> mapSToT = new HashMap<>();
        // HashMap to store mapping from t -> s (ensures bijection)
        Map<Character, Character> mapTToS = new HashMap<>();

        // Iterate through both strings simultaneously
        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            // Check if charS already has a mapping
            if (mapSToT.containsKey(charS)) {
                // If existing mapping doesn't match current charT, not isomorphic
                if (mapSToT.get(charS) != charT) {
                    return false;
                }
            } else {
                // Check if charT is already mapped to by a different character in s
                if (mapTToS.containsKey(charT)) {
                    return false;
                }
                // Establish the bidirectional mapping
                mapSToT.put(charS, charT);
                mapTToS.put(charT, charS);
            }
        }
        System.out.println("Mapping from s to t: " + mapSToT);
        System.out.println("Mapping from t to s: " + mapTToS);
        // All characters mapped consistently and bijectively
        return true;
    }

    public static void main(String[] args) {
        IsomorphicStringsSolution sol = new IsomorphicStringsSolution();

        String[][] testCases = {
                {"ab", "aa"},
                {"abcabc", "xyzxyz"},
                {"a", "z"},
                {"abab", "cdcd"},
                {"abcd", "aabb"}
        };

        String[] comments = {
                "'a'->'a', 'b'->'a' fails bijection",
                "perfect 1-to-1 mapping",
                "single character, trivially isomorphic",
                "alternating pattern, valid mapping",
                "4 distinct chars map to 2, fails bijection"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            String s = testCases[t][0];
            String tStr = testCases[t][1];
            boolean result = sol.isIsomorphic(s, tStr);
            System.out.println(y++ + ".\ts: \"" + s + "\"");
            System.out.println("\tt: \"" + tStr + "\"");
            System.out.println("\n\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}