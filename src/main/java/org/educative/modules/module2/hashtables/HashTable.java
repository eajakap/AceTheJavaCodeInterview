package org.educative.modules.module2.hashtables;

public class HashTable {
    private int size;

    // Single array to store key-value pairs
    private Object[][] table;

    public HashTable(int size) {
        // Total number of slots in the table
        this.size = size;
        this.table = new Object[size][2];   // Each slot holds [key, value]
    }

    public HashTable() {
        this(10);   // Default size of 10
    }

    private int hash(String key) {
        // Compute an index using Java's built-in hashCode method
        return Math.abs(key.hashCode()) % size;
    }

    public void insert(String key, int value) {
        // Compute the index for the key
        int index = hash(key);

        // If the slot is empty, insert the new key-value pair
        if (table[index][0] == null) {
            table[index][0] = key;
            table[index][1] = value;
            return;
        }

        // If the key already exists, update its value
        if (table[index][0].equals(key)) {
            table[index][1] = value;
            return;
        }

        // A different key is already stored at this index
        throw new RuntimeException("Collision detected - collision handling required");
    }

    public int get(String key) {
        // Compute the index for the key
        int index = hash(key);

        // If the slot is empty, the key is not present
        if (table[index][0] == null) {
            throw new RuntimeException("Key '" + key + "' not found");
        }

        // If the key matches, return the associated value
        if (table[index][0].equals(key)) {
            return (int) table[index][1]; // value is stored as Object, so we need to cast it to int
        }

        // A different key is present at this index
        throw new RuntimeException("Collision detected - collision handling required");
    }

    public void delete(String key) {
        // Compute the index for the key
        int index = hash(key);

        // If the slot is empty, the key is not present
        if (table[index][0] == null) {
            throw new RuntimeException("Key '" + key + "' not found");
        }

        // If the key matches, remove it
        if (table[index][0].equals(key)) {
            table[index][0] = null;
            table[index][1] = null;
            return;
        }

        // A different key is present at this index
        throw new RuntimeException("Collision detected - collision handling required");
    }

    public void display() {
        // Print the contents of the hash table
        System.out.println("\nCurrent hash table:");
        for (int i = 0; i < size; i++) {
            if (table[i][0] == null) {
                System.out.println("Index " + i + ": null");
            } else {
                System.out.println("Index " + i + ": (" + table[i][0] + ", " + table[i][1] + ")");
            }
        }
    }

    public static void main(String[] args) {
        HashTable table = new HashTable(10);

        System.out.println("=== Test Case 1: Insert key-value pairs ===");
        table.insert("alice", 91);
        table.insert("bob", 85);
        table.insert("charlie", 78);
        table.display();

        System.out.println("\n=== Test Case 2: Retrieve existing keys ===");
        System.out.println("Value for 'alice': " + table.get("alice"));
        System.out.println("Value for 'bob': " + table.get("bob"));
        System.out.println("Value for 'charlie': " + table.get("charlie"));

        System.out.println("\n=== Test Case 3: Update an existing key ===");
        table.insert("alice", 95);
        System.out.println("Updated value for 'alice': " + table.get("alice"));
        table.display();

        System.out.println("\n=== Test Case 4: Delete a key ===");
        table.delete("bob");
        System.out.println("Deleted key 'bob'");
        table.display();

        System.out.println("\n=== Test Case 5: Search for a deleted key ===");
        try {
            System.out.println("Value for 'bob': " + table.get("bob"));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 6: Delete a missing key ===");
        try {
            table.delete("david");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}