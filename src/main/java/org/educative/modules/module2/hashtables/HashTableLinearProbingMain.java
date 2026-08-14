package org.educative.modules.module2.hashtables;

// Hash Table using Linear Probing
// This example shows how collisions are handled by checking the next slots.
public class HashTableLinearProbingMain {

    static class HashTableLinearProbing {
        private int size;
        private String[][] table;  // Each slot is a [key, value] pair or null

        public HashTableLinearProbing(int size) {
            this.size = size;
            this.table = new String[size][];
        }

        private int hash(String key) {
            int total = 0;
            for (char ch : key.toCharArray()) {
                total += (int) ch;
            }
            return total % size;
        }

        public int[] insert(String key, String value) {
            int startIndex = hash(key);
            int index = startIndex;
            int probes = 0;

            for (int i = 0; i < size; i++) {
                if (table[index] == null) {
                    table[index] = new String[]{key, value};
                    return new int[]{startIndex, index, probes, 0};  // 0 = inserted
                }
                if (table[index][0].equals(key)) {
                    table[index][1] = value;
                    return new int[]{startIndex, index, probes, 1};  // 1 = updated
                }
                index = (index + 1) % size;
                probes++;
            }
            throw new RuntimeException("Hash table is full");
        }

        public int[] search(String key) {
            int startIndex = hash(key);
            int index = startIndex;
            int probes = 0;

            for (int i = 0; i < size; i++) {
                if (table[index] == null) {
                    return new int[]{startIndex, index, probes, -1};  // -1 = not found
                }
                if (table[index][0].equals(key)) {
                    return new int[]{startIndex, index, probes, 1};   // 1 = found
                }
                index = (index + 1) % size;
                probes++;
            }
            return new int[]{startIndex, index, probes, -1};
        }

        public String[][] getTable() {
            return table;
        }
    }

    static class Main {
        public static void main(String[] args) {
            System.out.println("Demonstrating Collision Handling in Hash Tables using Linear Probing\n");

            HashTableLinearProbing ht = new HashTableLinearProbing(11);

            String[][] entries = {{"ab", "1"}, {"ba", "2"}, {"alice", "91"}};
            for (String[] entry : entries) {
                String key = entry[0];
                String value = entry[1];
                int[] result = ht.insert(key, value);
                int startIndex = result[0], finalIndex = result[1], probes = result[2], status = result[3];

                if (status == 0 && probes == 0) {
                    System.out.println("Insert \"" + key + "\" (value " + value + ")");
                    System.out.println("  Hashed to index " + startIndex);
                    System.out.println("  Slot " + startIndex + " was empty, so it was inserted there.\n");
                } else if (status == 0 && probes > 0) {
                    System.out.println("Insert \"" + key + "\" (value " + value + ")");
                    System.out.println("  Hashed to index " + startIndex);
                    System.out.println("  Collision at index " + startIndex);
                    System.out.println("  Linear probing checked next slot(s)");
                    System.out.println("  Inserted at index " + finalIndex + " after " + probes + " probe(s).\n");
                } else {
                    System.out.println("Insert \"" + key + "\" (value " + value + ")");
                    System.out.println("  Key already existed, value updated at index " + finalIndex + ".\n");
                }
            }

            System.out.println("Current Hash Table:");
            String[][] table = ht.getTable();
            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    System.out.println("Index " + i + ": Empty");
                } else {
                    System.out.println("Index " + i + ": (" + table[i][0] + ", " + table[i][1] + ")");
                }
            }

            System.out.println();

            for (String key : new String[]{"ab", "ba", "alice", "bob"}) {
                int[] result = ht.search(key);
                int startIndex = result[0], foundIndex = result[1], probes = result[2], status = result[3];

                System.out.println("Search \"" + key + "\"");
                System.out.println("  Start at index " + startIndex);
                if (status == 1) {
                    System.out.println("  Found at index " + foundIndex + " after " + probes + " probe(s)");
                    System.out.println("  Value = " + table[foundIndex][1] + "\n");
                } else {
                    System.out.println("  Key not found after " + probes + " probe(s)\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
