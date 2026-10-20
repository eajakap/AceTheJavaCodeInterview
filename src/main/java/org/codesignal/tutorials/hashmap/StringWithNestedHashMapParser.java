package org.codesignal.tutorials.hashmap;

import java.util.HashMap;

public class StringWithNestedHashMapParser {

    public static HashMap<String, HashMap<String, String>> parseString(String inputString) {
        HashMap<String, HashMap<String, String>> result = new HashMap<>();

        String key = ""; // to store the outer map key
        HashMap<String, String> innerMap = new HashMap<>(); // to store the inner map
        boolean inInnerMap = false; // flag to check if we are inside an inner map
        int i = 0; // to iterate through the string
        while (i < inputString.length()) {
            if (inputString.charAt(i) == '{') {
                // Entering an inner map
                inInnerMap = true;
                i++; // Skip the '{'
            } else if (inputString.charAt(i) == '}') {
                // Exiting an inner map
                result.put(key, innerMap);
                innerMap = new HashMap<>();
                inInnerMap = false;
                i++; // Skip the '}'
                if (i < inputString.length() && inputString.charAt(i) == ',') {
                    i++; // Skip the ',' after '}'
                }
            }
            else if (!inInnerMap) {
                // Parsing key-value pairs in the outer map
                int equalPos = inputString.indexOf('=', i);
                int commaPos = inputString.indexOf(',', equalPos);
                if (commaPos == -1) commaPos = inputString.length();

                key = inputString.substring(i, equalPos);
                String value = inputString.substring(equalPos + 1, commaPos);

                if (value.contains("{")) {
                    // Value is a nested map, will be processed separately
                    i = equalPos + 1; // Move forward to process the nested part
                } else {
                    // Value is a simple string, add to result map
                    result.put(key, new HashMap<String, String>() {{
                        put("", value);
                    }});
                    i = commaPos + 1; // Move past the comma
                }
            }
            else if (inInnerMap) {
                // Parsing key-value pairs inside the inner map
                int equalPos = inputString.indexOf('=', i);
                int commaPos = inputString.indexOf(',', equalPos);
                int bracePos = inputString.indexOf('}', equalPos);

                // Determine the next delimiter that ends the current key-value pair
                int endPos = Math.min(commaPos < bracePos ? commaPos : inputString.length(), bracePos);
                if (endPos == inputString.length()) endPos = Math.max(commaPos, bracePos);

                String innerKey = inputString.substring(i, equalPos);
                String innerValue = inputString.substring(equalPos + 1, endPos);
                innerMap.put(innerKey, innerValue);

                i = endPos;
                if (i < inputString.length() && inputString.charAt(i) == ',') {
                    i++; // Skip the comma
                }
            }
        }

        return result;
    }
    public static void updateMap(HashMap<String, HashMap<String, String>> map, String key, String value) {
        for (HashMap<String, String> innerMap : map.values()) {
            if (innerMap.containsKey(key)) {
                // Key found, update the value
                innerMap.put(key, value);
                return;
            }
        }
    }
    public static HashMap<String, HashMap<String, String>> parseStringAndUpdateValue(String inputString, String updateKey, String newValue) {
        // Parse the input string into a nested HashMap
        HashMap<String, HashMap<String, String>> map = parseString(inputString);
        // Update the specific key-value pair
        updateMap(map, updateKey, newValue);
        return map;
    }

    public static void main(String[] args) {
        String input = "A1=B1,C1={D1=E1,F1=G1},I1=J1";
        String updateKey = "F1";
        String newValue = "NewValue";

        System.out.println("input: " + input + ", updateKey: " + updateKey + ", newValue: " + newValue);
        HashMap<String, HashMap<String, String>> updatedMap = parseStringAndUpdateValue(input, updateKey, newValue);
        System.out.println(updatedMap);
    }
}