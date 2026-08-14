package org.educative.modules.module2.hashtables;

import java.util.*;

public class HashTableChainingMain {

    // Hash Table using Separate Chaining
    // Collision handling is shown through output in the main section only.
    static class HashTableChaining {
        private int size;
        private List<List<int[]>> table;  // Each bucket is a list of [key-hashcode, value] pairs
        private List<List<String[]>> strTable; // For String keys

        public HashTableChaining(int size) {
            this.size = size;
            this.strTable = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                strTable.add(new ArrayList<>());
            }
        }

        private int hash(String key) {
            int total = 0;
            for (char ch : key.toCharArray()) {
                total += (int) ch;
            }
            return total % size;
        }

        public int[] insert(String key, String value) {
            int index = hash(key);
            List<String[]> bucket = strTable.get(index);

            for (int i = 0; i < bucket.size(); i++) {
                if (bucket.get(i)[0].equals(key)) {
                    bucket.get(i)[1] = value;
                    return new int[]{index, 1};  // 1 = updated
                }
            }

            boolean collision = !bucket.isEmpty();
            bucket.add(new String[]{key, value});
            return new int[]{index, collision ? 2 : 0};  // 2 = collision, 0 = inserted
        }

        public Object[] search(String key) {
            int index = hash(key);
            for (String[] entry : strTable.get(index)) {
                if (entry[0].equals(key)) {
                    return new Object[]{index, entry[1]};
                }
            }
            return new Object[]{index, null};
        }

        public int[] delete(String key) {
            int index = hash(key);
            List<String[]> bucket = strTable.get(index);
            int originalSize = bucket.size();
            bucket.removeIf(entry -> entry[0].equals(key));
            return new int[]{index, bucket.size() < originalSize ? 1 : 0};
        }

        public List<List<String[]>> getTable() {
            return strTable;
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Collision Handling in Hash Tables using Separate Chaining\n");

        HashTableChaining ht = new HashTableChaining(7);

        String[][] entries = {{"ab", "1"}, {"ba", "2"}, {"alice", "91"}};
        for (String[] entry : entries) {
            String key = entry[0];
            String value = entry[1];
            int[] result = ht.insert(key, value);
            int index = result[0];
            int status = result[1];

            if (status == 0) {
                System.out.println("Insert \"" + key + "\" -> index " + index + ": No collision");
            } else if (status == 2) {
                System.out.println("Insert \"" + key + "\" -> index " + index + ": Collision occurred");
            } else {
                System.out.println("Insert \"" + key + "\" -> index " + index + ": Value updated");
            }
        }

        System.out.println();

        System.out.println("Current Hash Table:");
        List<List<String[]>> table = ht.getTable();
        for (int i = 0; i < table.size(); i++) {
            List<String[]> bucket = table.get(i);
            if (!bucket.isEmpty()) {
                StringBuilder chain = new StringBuilder();
                for (String[] pair : bucket) {
                    if (chain.length() > 0) chain.append(" -> ");
                    chain.append("(").append(pair[0]).append(", ").append(pair[1]).append(")");
                }
                System.out.println("Index " + i + ": " + chain);
            } else {
                System.out.println("Index " + i + ": Empty");
            }
        }

        System.out.println();

        for (String key : new String[]{"ab", "ba", "alice", "bob"}) {
            Object[] result = ht.search(key);
            int index = (int) result[0];
            String value = (String) result[1];
            if (value != null) {
                System.out.println("Search \"" + key + "\" -> index " + index + ": Found value " + value);
            } else {
                System.out.println("Search \"" + key + "\" -> index " + index + ": Not found");
            }
        }
    }
}