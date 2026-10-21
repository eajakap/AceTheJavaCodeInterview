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

    public static char[] commonElements(String s, char[] letters) {
        Set<Character> setA = new HashSet<>();
        for (Character c : s.toCharArray()) {
            setA.add(c);
        }
        Set<Character> setB = new HashSet<>();
        for (char c : letters) {
            setB.add(c);
        }
        List<Character> common = new ArrayList<>();
        for (char c : setB) {
            if (setA.contains(c)) {
                common.add(c);
            }
        }

        Collections.sort(common);
        char[] result = new char[common.size()];
        for (int i = 0; i < common.size(); i++) {
            result[i] = common.get(i);
        }
        return result;
    }

    public static Boolean[] commonElements(String[] A, String[] B) {
        Set<String> setB = new HashSet<>();
        for (String str : B) {
            setB.add(str);
        }
        List<Boolean> common = new ArrayList<>();
        for (String str : A) {
            if (setB.contains(str)) {
                common.add(true);
            } else {
                common.add(false);
            }
        }
        return common.toArray(new Boolean[0]);
    }

    public static void testIntArray() {
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
        System.out.println();
    }

    public static void testString() {
        String s = "hello world";
        char[] letters = {'h', 'e', 'l', 'o', 'w', 'r', 'd', 'x'};
        char[] result = commonElements(s, letters);
        System.out.println(Arrays.toString(result));  // Prints: [d, e, h, l, o, r, w]

        String[] Array1 = {"mars", "jupiter", "venus", "earth"};
        String[] Array2 = {"earth", "mars", "neptune"};
        Boolean[] result2 = commonElements(Array1, Array2);
        System.out.println(Arrays.toString(result2));
    }

    public static void main(String[] args) {
        testIntArray();
        testString();
    }

}