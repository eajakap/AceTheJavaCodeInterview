package org.codesignal.debugging.hashsets;

import java.util.*;

public class CommonElements {
    public static List<Integer> commonElements(List<Integer> list1, List<Integer> list2) {
        Set<Integer> set1 = new HashSet<>(list1);

        List<Integer> common = new ArrayList<>();
        for (Integer num : list2) {
            if (set1.contains(num)) {
                common.add(num);
            }
        }
        return common;
    }

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 5, 8, 13, 21, 34);
        List<Integer> list2 = Arrays.asList(2, 3, 5, 7, 13, 21, 31);
        List<Integer> result = commonElements(list1, list2);
        System.out.println(result);  // Prints: [2, 3, 5, 13, 21]

        Set<Integer> mySet = new HashSet<>(Arrays.asList(1, 2, 3));
        mySet.add(4);  // O(1)
        mySet.add(2);  // O(1) but this will have no effect since 2 is already in the set
        System.out.println(mySet.size());  // O(1), prints: 4

        System.out.println(mySet);  // Prints: [1, 2, 3, 4] (order may vary)

        System.out.println(mySet.contains(3));  // O(1), prints: true
        System.out.println(mySet.contains(6));  // O(1), prints: false

        mySet.remove(3);  // O(1)
        mySet.remove(5);  // O(1) but this will have no effect since 5 is not in the set

        System.out.println(mySet);  // Prints: [1,2,4] (order may vary)
        mySet.clear();  // O(n)
        System.out.println(mySet);  // Prints: []
    }

}