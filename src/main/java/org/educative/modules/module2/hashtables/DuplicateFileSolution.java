package org.educative.modules.module2.hashtables;

import java.util.*;

class DuplicateFileSolution {
    public List<List<String>> findDuplicate(String[] paths) {
        // HashMap to group file paths by their content
        Map<String, List<String>> contentMap = new HashMap<>();

        for (String path : paths) {
            // Split the string into parts: first part is directory, rest are files
            String[] parts = path.split(" ");
            String directory = parts[0];

            // Process each file entry
            for (int i = 1; i < parts.length; i++) {
                // Extract file name and content by splitting on '('
                int parenIdx = parts[i].indexOf('(');
                String fileName = parts[i].substring(0, parenIdx);
                // Content is between '(' and ')', so strip the closing ')'
                String content = parts[i].substring(parenIdx + 1, parts[i].length() - 1);
                // Build full file path and group by content
                String fullPath = directory + "/" + fileName;
                contentMap.computeIfAbsent(content, k -> new ArrayList<>()).add(fullPath);
            }
        }

        // Filter out groups with fewer than 2 files (no duplicates)
        List<List<String>> result = new ArrayList<>();
        for (List<String> group : contentMap.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        DuplicateFileSolution sol = new DuplicateFileSolution();

        String[][] testCases = {
                {"dir1 a.txt(hello)", "dir2 b.txt(hello)", "dir3 c.txt(hello)"},
                {"home/user doc.txt(xyz) notes.txt(abc)", "home/backup doc2.txt(xyz)", "tmp notes2.txt(abc)", "var log.txt(unique)"},
                {"folder f1.txt(same) f2.txt(same) f3.txt(diff)"},
                {"a/b/c/d file1.txt(data1)", "x/y/z file2.txt(data1)", "a/b file3.txt(data2)", "x file4.txt(data2)", "m/n file5.txt(data3)"},
                {"root/p q.txt(alpha)", "root/r s.txt(beta)", "root/t u.txt(gamma)"}
        };

        String[] comments = {
                "Three files share same content across different directories",
                "Multiple groups of duplicates",
                "Single directory with duplicate content files",
                "Deep nested directories with duplicates",
                "No duplicates at all — expect empty result"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            List<List<String>> result = sol.findDuplicate(testCases[t]);
            System.out.println(y++ + ".\tpaths: " + Arrays.toString(testCases[t]) + " -> " + comments[t]);
            System.out.println("\n\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}