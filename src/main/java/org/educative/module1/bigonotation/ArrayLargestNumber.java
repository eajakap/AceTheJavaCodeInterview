package org.educative.module1.bigonotation;

public class ArrayLargestNumber {
    public static int  largestNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
//            if (numbers[i] > max) {
//                max = numbers[i];
//            }
            max = Math.max(numbers[i], max);
        }
        return max;
    }
    public static void main(String[] args) {
        System.out.println(largestNumber(new int[] { 1, 2, 3, 4, 5 }));
    }
}
