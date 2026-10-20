package org.codesignal.tutorials.arrays.multidimensional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Finds the zero-based traversal positions of vowels in a character grid's clockwise spiral order.
 */
public class ZigZagTraversel {

    enum Direction {
        RIGHT(1),
        LEFT(-1);

        final int dCol;

        Direction(int dCol) {
            this.dCol = dCol;
        }

        Direction flip() {
            return this == RIGHT ? LEFT : RIGHT;
        }
    }

    /**
     * Traverses the grid clockwise from its top-left cell and returns the positions
     * of vowels in that traversal.
     *
     * @param grid a rectangular grid of lowercase letters
     * @return zero-based positions of vowels in clockwise spiral order
     */
    public static int[] snakeTraverseAndVowels(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new int[0];
        }
        char[] snakeGrid = new char[grid.length * grid[0].length];

        int rows = grid.length, cols = grid[0].length;
        int r = 0, c = 0;
        Direction dir = Direction.RIGHT;
        List<Integer> vowels = new ArrayList<>();

        for (int i = 0; i < rows * cols; i++) {
            if (isVowel(grid[r][c])) vowels.add(i);
            snakeGrid[i] = grid[r][c];
            int nc = c + dir.dCol;
            if (nc < 0 || nc >= cols) {
                r++;               // end of row → drop down
                dir = dir.flip();  // reverse direction; column stays put
            } else {
                c = nc;
            }
        }
        printArray(snakeGrid);

        return vowels.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean isVowel(char ch) {
        return "aeiou".indexOf(ch) >= 0;
    }

    public Map<Integer, Integer> zigzagTraverseAndPrimes(int[][] matrix) {
        // TODO: implement
        Map<Integer, Integer> map = new HashMap<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return map;
        }
        int[] snakeGrid = new int[matrix.length * matrix[0].length];
        int rows = matrix.length, cols = matrix[0].length;
        int row = 0, col = 0;
        Direction dir = Direction.RIGHT;

        for (int i = 0; i < rows * cols; i++) {
            snakeGrid[i] = matrix[row][col];

            int nextCol = col + dir.dCol;
            if (nextCol < 0 || nextCol >= cols) {
                row++;               // end of row → drop down
                dir = dir.flip();  // reverse direction; column stays put
            } else {
                col = nextCol;
            }
        }
        printArray(snakeGrid);

        for (int i = 0; i < snakeGrid.length; i++) {
            if (isPrime(snakeGrid[i])) {
                map.put(i, snakeGrid[i]);
            }
        }
        return map;
    }

    private static boolean isPrime(int n) {
        // TODO: implement
        if (n < 2) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printArray(char[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void main(String[] args) {
        ZigZagTraversel app = new ZigZagTraversel();
        char[][] matrix = {
                {'a', 'b', 'c', 'd'},
                {'e', 'f', 'g', 'h'},
                {'i', 'j', 'k', 'l'}
        };
        printMatrix(matrix);
        int[] result = app.snakeTraverseAndVowels(matrix);
        printArray(result);

        int[][] matrix2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        Map<Integer, Integer> primePositions = app.zigzagTraverseAndPrimes(matrix2);
        System.out.println("Prime positions and values: " + primePositions);
    }

}
