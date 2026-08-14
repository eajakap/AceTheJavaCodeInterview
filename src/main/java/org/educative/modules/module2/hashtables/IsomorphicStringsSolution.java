package org.educative.modules.module2.hashtables;

import java.util.*;

public class IsomorphicStringsSolution {
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