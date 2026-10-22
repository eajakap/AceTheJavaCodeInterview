package org.codesignal.debugging.string;

import java.util.HashMap;
import java.util.Map;

/**
 * You are tasked with an intriguing string manipulation problem. You are provided with a string s and an integer K.
 * The string s contains at most $100000$ alphanumeric (A-Za-z0-9) characters and has at least one character.
 * The integer K ranges from $1$ to $100000$, inclusive.
 * You need to write a Java function that identifies the length of the longest possible substring within s
 * that contains at most K distinct characters.
 * If multiple substrings share the longest length, you should select the substring that appears first.
 * Your solution should have a time complexity of $O(n)$, where n is the length of the string s,
 * to ensure efficiency given the constraints.
 * To offer more clarity, let's consider an example. Suppose s is "acaabcc" and K is 2.
 * The longest possible substring with at most 2 distinct characters is "acaa",
 * so your function should return 4, which is the length of "acaa".
 * Remember, the function should not return the actual substring but the length of the longest possible
 * substring that meets the specified criteria.
 * Note: Alphanumeric characters are case-sensitive, i.e., 'A' and 'a' are treated as different characters.
 */
public class LongestNonRepeatWithDistinctChars {

    /**
     * Steps:
     * 1. Use a hash map to count the number of occurrences of each character in the string.
     * 2. Maintain a sliding window of size k.
     * 3. As we add more characters to the window, shrink the window by removing the leftmost character
     * if its count exceeds k.
     * 4. Keep track of the maximum length of the window.
     * 5. Return the maximum length of the window.
     * 6. Time Complexity: O(n)
     * 7. Space Complexity: O(k)
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstringWithKDistinctChars(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }

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
