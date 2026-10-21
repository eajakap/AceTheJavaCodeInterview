package org.codesignal.debugging.hashmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestDistanceBetweenWords {

    public Map<String, Integer> solution(List<String> wordList) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> seen = new HashMap<>();
        for (int index = 0; index < wordList.size(); index++ ) {
            String word = wordList.get(index);
            if (seen.containsKey(word)) {
                int lastIndex = seen.get(word);
                int distance = index - lastIndex;
                int shortest = (result.containsKey(word)) ? Math.min(result.get(word), distance) : distance ;
                seen.put(word, index);
                result.put(word, shortest);
            } else {
                seen.put(word, index);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ShortestDistanceBetweenWords app = new ShortestDistanceBetweenWords();
        System.out.println(app.solution(List.of("a", "b", "c", "a", "b", "c")));
    }
}