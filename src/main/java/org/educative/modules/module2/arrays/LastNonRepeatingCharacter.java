package org.educative.modules.module2.arrays;

/*
 * Write a Java method to find the last non-repeating character in a string.
 * String str = "footbaall";
 * Time complexity: O(n)
 * Space complexity: O(n)
*/

import java.util.HashMap;
import java.util.Map;

public class LastNonRepeatingCharacter {
    static enum SolutionAlgorithm {
        ARRAY_BASED, // use an array of size 256 to count the occurrences of each character, Space Complexity- O(1)
        HASHMAP_BASED // use a HashMap to count the occurrences of each character, Space Complexity - O(n)
    }

    public char lastNonrepeatingCharacter(String str, SolutionAlgorithm algorithm) {
        switch (algorithm) {
            case ARRAY_BASED:
                return lastNonrepeatingCharacterUsingArray(str);
            case HASHMAP_BASED:
                return lastNonrepeatingCharacterUsingHashMap(str);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private char lastNonrepeatingCharacterUsingArray(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("str must be non-empty");
        }

        int[] count = new int[256];
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }

        for (int i = str.length() - 1; i >= 0; i--) {
            if (count[str.charAt(i)] == 1) {
                return str.charAt(i);
            }
        }

        throw new IllegalStateException("no non-repeating character in: " + str);
    }

    private char lastNonrepeatingCharacterUsingHashMap(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("str must be non-empty");
        }

        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // walk backwards: the first count-1 char we hit is the last non-repeating one
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (countMap.get(c) == 1) {
                return c;
            }
        }

        throw new IllegalStateException("no non-repeating character in: " + str);
    }

    public char lastRepeatingCharacterWithMaxCounts(String str, SolutionAlgorithm algorithm) {
        switch (algorithm) {
            case ARRAY_BASED:
                return lastRepeatingCharacterWithMaxCountsUsingArray(str);
            case HASHMAP_BASED:
                return lastRepeatingCharacterWithMaxCountsUsingHashMap(str);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private char lastRepeatingCharacterWithMaxCountsUsingArray(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("str must be non-empty");
        }

        int[] count = new int[256];
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }

        // walk backwards: the first count-1 char we hit is the last repeating one
        int maxCount = 0;
        char lastRepeatingChar = '\0';
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (count[c] > 1) {
                if (count[c] > maxCount) {
                    maxCount = count[c];
                    lastRepeatingChar = c;
                }
            }
        }

        if (lastRepeatingChar != '\0') {
            return lastRepeatingChar;
        }

        throw new IllegalStateException("no repeating character in: " + str);
    }

    private char lastRepeatingCharacterWithMaxCountsUsingHashMap(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("str must be non-empty");
        }

        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // walk backwards: the first count-1 char we hit is the last repeating one
        int maxCount = 0;
        char lastRepeatingChar = '\0';
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (countMap.get(c) > 1) {
                if (countMap.get(c) > maxCount) {
                    maxCount = countMap.get(c);
                    lastRepeatingChar = c;
                }
            }
        }

        if (lastRepeatingChar != '\0') {
            return lastRepeatingChar;
        }

        throw new IllegalStateException("no repeating character in: " + str);
    }

    public static void main(String[] args) {
        LastNonRepeatingCharacter obj = new LastNonRepeatingCharacter();
        String str = "footbbbbbaall";
        char resultMap = obj.lastNonrepeatingCharacter(str, SolutionAlgorithm.HASHMAP_BASED);
        char resultArray = obj.lastNonrepeatingCharacter(str, SolutionAlgorithm.ARRAY_BASED);
        System.out.println("Last non-repeating character (HashMap): " + resultMap);
        System.out.println("Last non-repeating character (Array): " + resultArray);

        char result2Map = obj.lastRepeatingCharacterWithMaxCounts(str, SolutionAlgorithm.HASHMAP_BASED);
        char result2Array = obj.lastRepeatingCharacterWithMaxCounts(str, SolutionAlgorithm.ARRAY_BASED);
        System.out.println("Last repeating character (HashMap): " + result2Map);
        System.out.println("Last repeating character (Array): " + result2Array);
    }
}
