package org.codesignal.debugging.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In the legendary kingdom of Alphaland, exists a cryptic scroll named s, inscribed with a string of elegant lowercase letters.
 * As a renowned linguist, your quest is to decipher this scroll by partitioning it into as many chapters (substrings) as your wit allows,
 * under the condition that each letter graces only a maximum of one chapter.
 *
 * This is a sacred task, for the chapters must follow the progression of the original text, reflecting the flow of the narrative
 * embedded within it. To illustrate, if you were to partition the text "abacdcd" into the chapters "aba" and "cdcd",
 * your resultant report of chapter lengths should list as [3, 4], preserving the original sequence.
 *
 * The ancient scroll s stretches on to a length n, varying anywhere from 1 to 1000000 characters.
 * Its diverse inhabitants range from the scribed characters from a to z.
 *
 * Your mission, dear linguist, is to draw the linguistics of Alphaland in the form of the Java method public List<Integer> solution(String s).
 * This magical method shall read the string of characters and spin out a list of integers representing the lengths of each chapter,
 * forming the crucial code to decipher the legend of Alphaland.
 * More formally, the task involves partitioning a string into as many substrings as possible while ensuring that each character
 * appears in only one substring (no substrings have common characters).
 * The substrings should retain the original order of characters from the input string.
 *
 * Constraints:
 *
 * The length of the input string n will be in the range of 1 ≤ n ≤ 1000000.
 * All characters in the string will be lower-case English alphabets ('a' to 'z').
 * The time complexity of the solution should be $O(n)$.
 */
public class CrypticScrollApp {

    public List<Integer> findChapterLengths(String s) {
        HashMap<Character, Integer> lastOccurrence = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        List<Character> list = new ArrayList<>();
        if (s.isEmpty()) return result;
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence.put(s.charAt(i), i);
        }
        int chapterStart = 0;
        int chapterEnd = 0;
        for (int i = 0; i < s.length(); i++) {
            chapterEnd = Math.max(chapterEnd, lastOccurrence.get(s.charAt(i)));
            if (i == chapterEnd) {
                result.add(chapterEnd - chapterStart + 1);
                chapterStart = i + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CrypticScrollApp app = new CrypticScrollApp();
        System.out.println(app.findChapterLengths("abacbcdefgf"));
    }
}
