package org.codesignal.tutorials.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;


import java.util.ArrayList;
import java.util.List;

public class StringWithNestedHashMapParser3 {

    public static List<Map<String, Map<String, String>>> solution(String data, String userid,
                                                                  String key, String newValue) {
        List<Map<String, Map<String, String>>> users = new ArrayList<>();

        // 1. Parse each line into {ID → details}
        for (String line : data.split("\\r?\\n")) {
            if (line.trim().isEmpty()) continue;

            List<String> fields = splitTopLevel(line, ',');
            String id = fields.get(0).trim();
            Map<String, String> details = new LinkedHashMap<>();

            for (int i = 1; i < fields.size(); i++) {
                String field = fields.get(i).trim();
                int eq = field.indexOf('=');
                if (eq < 0) continue;

                String k = field.substring(0, eq).trim();
                String v = field.substring(eq + 1).trim();
                // if (v.startsWith("(") && v.endsWith(")")) {
                //     v = v.substring(1, v.length() - 1);   // strip parentheses
                // }
                details.put(k, v);
            }

            Map<String, Map<String, String>> user = new LinkedHashMap<>();
            user.put(id, details);
            users.add(user);
        }

        // 2. Update the requested user's key
        for (Map<String, Map<String, String>> user : users) {
            Map<String, String> details = user.get(userid);
            if (details == null) continue;

            if (details.containsKey(key)) {
                details.put(key, newValue);                // top-level key, e.g. Email
            } else {
                updateSubKey(details, key, newValue);      // nested key, e.g. City
            }
            break;
        }

        return users;
    }

    // Split on delim, ignoring any delim inside ( )
    private static List<String> splitTopLevel(String s, char delim) {
        List<String> parts = new ArrayList<>();
        int depth = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') depth++;
            else if (ch == ')') depth--;
            else if (ch == delim && depth == 0) {
                parts.add(s.substring(start, i));
                start = i + 1;
            }
        }
        parts.add(s.substring(start));
        return parts;
    }

    // Update a sub-key inside a nested value like "Street=Main St;City=NY;Zip=10001"
    private static void updateSubKey(Map<String, String> details, String key, String newValue) {
        for (Map.Entry<String, String> e : details.entrySet()) {
            String v = e.getValue();
            if (!v.contains("=")) continue;

            Map<String, String> sub = new LinkedHashMap<>();
            for (String token : v.split(";")) {
                int eq = token.indexOf('=');
                if (eq >= 0) sub.put(token.substring(0, eq).trim(), token.substring(eq + 1).trim());
            }

            if (sub.containsKey(key)) {
                sub.put(key, newValue);
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> s : sub.entrySet()) {
                    if (sb.length() > 0) sb.append(';');
                    sb.append(s.getKey()).append('=').append(s.getValue());
                }
                e.setValue(sb.toString());
                return;
            }
        }
    }

    public static void main(String[] args) {
        String data = "001,Age=25,Name=John,Address=(Street=Main St;City=NY;Zip=10001),Email=john@gmail.com\n"
                + "002,Age=30,Name=Jane,Address=(Street=2nd St;City=LA;Zip=90001),Email=jane@hotmail.com";
        System.out.println(new StringWithNestedHashMapParser3().solution(data, "001", "Email", "johndoe@gmail.com"));
    }
}