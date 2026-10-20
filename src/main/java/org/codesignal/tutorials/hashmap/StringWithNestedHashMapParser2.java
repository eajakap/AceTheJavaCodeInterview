package org.codesignal.tutorials.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringWithNestedHashMapParser2 {

    public Map<String, Map<String, String>> parseString(String s) {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        Map<String, String> currentUser = null;
        int i = 0, n = s.length();

        while (i < n) {
            i = skipSpaces(s, i);
            if (i >= n) break;

            int eq = s.indexOf('=', i);
            int colon = s.indexOf(':', i);

            // "User1:" → a ':' before the next '=' starts a new user
            if (colon != -1 && (eq == -1 || colon < eq)) {
                currentUser = new LinkedHashMap<>();
                result.put(s.substring(i, colon).trim(), currentUser);
                i = colon + 1;
                continue;
            }
            if (eq == -1 || currentUser == null) {
                throw new IllegalArgumentException("Malformed input near position " + i);
            }

            // Attribute key
            String key = s.substring(i, eq).trim();
            i = skipSpaces(s, eq + 1);

            // Attribute value: either {nested} or plain text up to ';'
            String value;
            if (i < n && s.charAt(i) == '{') {
                int close = s.indexOf('}', i);
                value = normalizeNested(s.substring(i + 1, close));
                i = close + 1;
            } else {
                int semi = s.indexOf(';', i);
                if (semi == -1) semi = n;
                value = s.substring(i, semi).trim();
                i = semi;
            }
            currentUser.put(key, value);

            i = skipSpaces(s, i);
            if (i < n && s.charAt(i) == ';') i++;  // skip separator
        }
        return result;
    }

    public void updateMap(Map<String, Map<String, String>> map, int userIndex, String key, String value) {
        Map<String, String> user = map.get("User" + userIndex);
        if (user == null) {
            throw new IllegalArgumentException("No user with index " + userIndex);
        }

        for (Map.Entry<String, String> attr : user.entrySet()) {
            String v = attr.getValue();
            if (v.startsWith("{") && v.endsWith("}")) {
                Map<String, String> prefs = parsePairs(v.substring(1, v.length() - 1));
                if (prefs.containsKey(key)) {
                    prefs.put(key, value);
                    attr.setValue(toNestedString(prefs));
                    return;
                }
            }
        }
        throw new IllegalArgumentException("User" + userIndex + " has no preference " + key);
    }

    public Map<String, Map<String, String>> updatePreference(String inputString, int userIndex,
                                                             String prefKey, String newValue) {
        Map<String, Map<String, String>> map = parseString(inputString);
        updateMap(map, userIndex, prefKey, newValue);
        return map;
    }

    // ---------- Helpers ----------

    // "Food1=Italian; Sport1=Fencing" → {Food1=Italian, Sport1=Fencing}
    private Map<String, String> parsePairs(String s) {
        Map<String, String> pairs = new LinkedHashMap<>();
        for (String token : s.split(";")) {
            token = token.trim();
            if (token.isEmpty()) continue;
            int eq = token.indexOf('=');
            pairs.put(token.substring(0, eq).trim(), token.substring(eq + 1).trim());
        }
        return pairs;
    }

    private String toNestedString(Map<String, String> pairs) {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, String> e : pairs.entrySet()) {
            if (sb.length() > 1) sb.append(';');
            sb.append(e.getKey()).append('=').append(e.getValue());
        }
        return sb.append('}').toString();
    }

    private String normalizeNested(String inner) {
        return toNestedString(parsePairs(inner));  // strips stray spaces
    }

    private int skipSpaces(String s, int i) {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
        return i;
    }

    public static void main(String[] args) {
        StringWithNestedHashMapParser2 app = new StringWithNestedHashMapParser2();
        String inputString = "User1:Age1=21;Location1=USA;Preferences1={Food1=Italian; Sport1=Fencing};"
                + "User2:Age2=30; Location2=Canada; Preferences2={Music2=Jazz; Color2=Blue}";
        int userIndex = 1;
        String prefKey = "Sport1";      // was "User1"
        String newValue = "Hockey";
        System.out.println("Input String: " + inputString);
        Map<String, Map<String, String>> updatedMap = app.updatePreference(inputString, userIndex, prefKey, newValue);
        System.out.println("Updated Map: " + updatedMap);
    }
}