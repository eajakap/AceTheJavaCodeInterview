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

    public char lastNonrepeatingCharacter(String str) {
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

    public char lastRepeatingCharacterWithMaxCounts(String str) {
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
        char result = obj.lastNonrepeatingCharacter(str);
        System.out.println("Last non-repeating character: " + result);
        char result2 = obj.lastRepeatingCharacterWithMaxCounts(str);
        System.out.println("Last repeating character: " + result2);
    }
}
