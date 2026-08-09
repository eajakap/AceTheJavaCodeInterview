package org.educative.modules.module2.maps;

import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {

        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "A");
        map.put(20, "B");
        map.put(30, "C");

        System.out.println("Ceiling key of '15': " + map.ceilingKey(15));
        System.out.println("Ceiling key of '20': " + map.ceilingKey(20));
        System.out.println("Ceiling key of '35': " + map.ceilingKey(35));

        System.out.println("Floor key of '25': " + map.floorKey(25));
        System.out.println("Floor key of '20': " + map.floorKey(20));
        System.out.println("Floor key of '5': " + map.floorKey(5));

        // TreeMap with fruits as keys and corresponding counts as values
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("banana", 3);
        treeMap.put("apple", 4);
        treeMap.put("pear", 1);
        treeMap.put("orange", 2);

        // Print the TreeMap
        System.out.println(treeMap);

        // 'apple' is less than or equal to 'apple'
        System.out.println("Ceiling key of 'apple': " + treeMap.ceilingKey("apple")); // Output: apple
        System.out.println("Ceiling key of 'banana': " + treeMap.ceilingKey("banana")); // Output: banana
        System.out.println("Ceiling key of 'pear': " + treeMap.ceilingKey("pear")); // Output: pear
        System.out.println("Ceiling key of 'appl': " + treeMap.ceilingKey("appl")); // Output: pear

        // 'apple' is less than or equal to 'apple'
        System.out.println("Floor key of 'apple': " + treeMap.floorKey("apple")); // Output: apple
        System.out.println("Floor key of 'banana': " + treeMap.floorKey("banana")); // Output: banana
        System.out.println("Floor key of 'pear': " + treeMap.floorKey("pear")); // Output: pear
        System.out.println("Floor key of 'appl': " + treeMap.floorKey("appl")); // Output: apple


        // Remove 'apple' and print the removed value
        Integer removedValue = treeMap.remove("apple");
        System.out.println("Removed value: " + removedValue); // Output: 4

        // Attempt to fetch a nonexisting key
        Integer value = treeMap.get("apple");
        if (value == null) {
            System.out.println("Value: Not found"); // Output: Value: Not found
        }

        // Get the last key-value pair
        System.out.println("Last entry: " + treeMap.lastEntry()); // Output: pear=1

        TreeMap<String, String> eventDates = new TreeMap<>();
        eventDates.put("2023-06-21", "Concert");
        eventDates.put("2023-07-12", "Conference");
        eventDates.put("2023-06-11", "Seminar");
        eventDates.put("2023-08-05", "Workshop");

        // Step 1: Get Seminar's date
        String seminarDate = null;
        for (var entry : eventDates.entrySet()) {
            if (entry.getValue().equals("Seminar")) {
                seminarDate = entry.getKey();
                break;
            }
        }

        // Step 2: Find the next chronological date
        String nextDate = eventDates.higherKey(seminarDate);

        // Step 3: Get the event name at that date
        String nextEvent = eventDates.get(nextDate);

        System.out.println(nextEvent);

        // TreeMap sorted by score (key)
        TreeMap<Integer, String> scores = new TreeMap<>();
        scores.put(85, "Alice");
        scores.put(92, "Bob");
        scores.put(78, "Charlie");
        scores.put(95, "Diana");
        scores.put(88, "Ethan");

        // Find the highest score (last key in sorted order)
        var topEntry = scores.lastEntry();

        // Display the star performer
        System.out.println("Top performer: " + topEntry.getValue() +
                " with score " + topEntry.getKey());
    }
}