package org.codesignal.tutorials.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonParser {
    private static final String json = "{\"key1\": \"value1\", \"key2\": {\"key3\": \"value3\", \"key4\": \"value4\"}, \"key5\":\"value5\"}";
    private static final String TARGET_KEY = "key4";

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> solution(String jsonString, String updateValue) {
        Map<String, Object> parsed = new Parser(jsonString).parse();
        Map<String, Map<String, String>> result = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : parsed.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Map<String, String> inner = new LinkedHashMap<>();

            if (value instanceof Map) {
                // Nested object: copy entries, updating key4 if present
                for (Map.Entry<String, Object> e : ((Map<String, Object>) value).entrySet()) {
                    String v = TARGET_KEY.equals(e.getKey()) ? updateValue : String.valueOf(e.getValue());
                    inner.put(e.getKey(), v);
                }
            } else {
                // Outer string/number: update if key4, then wrap as {value: ""}
                String v = TARGET_KEY.equals(key) ? updateValue : String.valueOf(value);
                inner.put(v, "");
            }
            result.put(key, inner);
        }
        return result;
    }

    // ---------- Minimal JSON parser: objects, strings, numbers ----------
    private static class Parser {
        private final String s;
        private int pos = 0;

        Parser(String s) { this.s = s; }

        Map<String, Object> parse() {
            Map<String, Object> result = parseObject();
            skipWhitespace();
            if (pos != s.length()) throw error("Unexpected trailing characters");
            return result;
        }

        private Map<String, Object> parseObject() {
            Map<String, Object> map = new LinkedHashMap<>();
            expect('{');
            skipWhitespace();
            if (peek() == '}') { pos++; return map; }

            while (true) {
                skipWhitespace();
                String key = parseString();
                skipWhitespace();
                expect(':');
                map.put(key, parseValue());
                skipWhitespace();
                char ch = next();
                if (ch == '}') return map;
                if (ch != ',') throw error("Expected ',' or '}'");
            }
        }

        private Object parseValue() {
            skipWhitespace();
            char ch = peek();
            if (ch == '{') return parseObject();
            if (ch == '"') return parseString();
            return parseNumber();
        }

        private String parseString() {
            expect('"');
            StringBuilder sb = new StringBuilder();
            while (true) {
                char ch = next();
                if (ch == '"') return sb.toString();
                if (ch == '\\') {
                    char esc = next();
                    switch (esc) {
                        case 'n': sb.append('\n'); break;
                        case 't': sb.append('\t'); break;
                        case 'r': sb.append('\r'); break;
                        default:  sb.append(esc);  // handles \" \\ \/
                    }
                } else {
                    sb.append(ch);
                }
            }
        }

        private String parseNumber() {
            int start = pos;
            while (pos < s.length() && "+-0123456789.eE".indexOf(s.charAt(pos)) >= 0) pos++;
            if (start == pos) throw error("Expected a value");
            return s.substring(start, pos);  // kept as text; output map is String-typed
        }

        private void skipWhitespace() {
            while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) pos++;
        }

        private char peek() {
            if (pos >= s.length()) throw error("Unexpected end of input");
            return s.charAt(pos);
        }

        private char next() {
            char ch = peek();
            pos++;
            return ch;
        }

        private void expect(char expected) {
            if (next() != expected) throw error("Expected '" + expected + "'");
        }

        private IllegalArgumentException error(String msg) {
            return new IllegalArgumentException(msg + " at position " + pos);
        }
    }

    public static void main(String[] args) {
        String input = json;
        String updateKey = "key4";
        String updateValue = "updateValue";
        JsonParser app = new JsonParser();
        System.out.println("input: " + input + ", updateKey: " + updateKey + ", updateValue: " + updateValue);
        Map<String, Map<String, String>> updatedMap = app.solution(input, updateValue);
        System.out.println(updatedMap);
    }
}