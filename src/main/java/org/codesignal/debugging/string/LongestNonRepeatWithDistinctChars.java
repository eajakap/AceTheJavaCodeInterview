package org.codesignal.debugging.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Finds the length of the longest substring that contains at most K distinct characters.
 *
 * Given a string s of 1 to 100000 alphanumeric characters (A-Za-z0-9) and an integer K between 1 and 100000,
 * inclusive, return the length of the longest substring of s with at most K distinct characters.
 * Only the length is returned, not the substring itself.
 * Characters are case-sensitive, so 'A' and 'a' are different characters.
 * The solution runs in {@code O(n)} time, where n is the length of s.
 *
 * Example: for s = "acaabcc" and K = 2, the longest substring with at most 2 distinct characters is "acaa",
 * so the result is 4.
 */
public class LongestNonRepeatWithDistinctChars {

    /**
     * Returns the length of the longest substring of {@code s} with at most {@code k} distinct characters.
     *
     * Steps:
     * 1. Use a hash map to count the occurrences of each character inside the current window.
     * 2. Expand the window by moving the right pointer one character at a time.
     * 3. While the window holds more than k distinct characters, shrink it from the left,
     *    removing a character from the map when its count reaches zero.
     * 4. After each step, update the maximum window length.
     * 5. Return the maximum length.
     * 6. Time Complexity: O(n)
     * 7. Space Complexity: O(k)
     *
     * @param s the input string
     * @param k the maximum number of distinct characters allowed
     * @return the length of the longest valid substring, or 0 if {@code s} is null or empty or {@code k} is 0
     */
    public int longestSubstringWithKDistinctChars(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }
        System.out.println("s= " + s + " length= " + s.length());
        Map<Character, Integer> charCountMap = new HashMap<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            charCountMap.put(rightChar, charCountMap.getOrDefault(rightChar, 0) + 1);

            // shrink the window if the count exceeds k - sliding window size equals k
            while (charCountMap.size() > k) {
                // shrink the window
                char leftChar = s.charAt(left);
                charCountMap.put(leftChar, charCountMap.get(leftChar) - 1); // decrement count
                if (charCountMap.get(leftChar) == 0) {
                    charCountMap.remove(leftChar); // remove the key if count is 0
                }
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestNonRepeatWithDistinctChars app = new LongestNonRepeatWithDistinctChars();
        System.out.println(app.longestSubstringWithKDistinctChars("acaabcc", 2));
        System.out.println(app.longestSubstringWithKDistinctChars("abcd1112222cccc", 4));
    }
}
