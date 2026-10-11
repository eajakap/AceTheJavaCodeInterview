package org.educative.modules.module2.maps;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * P1 D3 - First unique so far           target 20 minutes
 *
 * Decline codes arrive one at a time. After each arrival, report the FIRST code seen so far that
 * has appeared exactly once - "first" meaning earliest arrival, not alphabetical. If every code
 * seen so far has appeared more than once, report "".
 *
 *   {"A","B","A","C"}  ->  {"A","A","B","B"}
 *   {"A","A"}          ->  {"A",""}
 *
 * The answer has one entry per input code. 0 <= codes.length <= 200000, so re-scanning
 * everything after each arrival will not pass the large case.
 */
public class FirstUniqueAfterEach {
    public static String[] firstUniqueAfterEach(String[] elements) {
       Map<String, Integer> countMap = new LinkedHashMap<>();
       Deque<String> deque = new ArrayDeque<>();
       String[] result = new String[elements.length];
       for (int i=0; i< elements.length; i++) {
           String code = elements[i];
           countMap.put(code, countMap.getOrDefault(code, 0) + 1);
           if (countMap.get(code) == 1) {
               deque.addLast(code);
           }
           while (!deque.isEmpty() && countMap.get(deque.peekFirst()) > 1) {
               deque.pollFirst();
           }
           result[i] = deque.isEmpty() ? "" : deque.peekFirst();
       }

      return result;
    }

    public static void main(String[] args) {
        String[] elements = {"a", "b", "a", "c", "b", "d"};
        String[] result = firstUniqueAfterEach(elements);
        for (String s : result) {
            System.out.print(s + " ");
        }
    }
}
