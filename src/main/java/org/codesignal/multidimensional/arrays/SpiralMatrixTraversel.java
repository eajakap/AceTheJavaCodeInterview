package org.codesignal.multidimensional.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finds the zero-based traversal positions of vowels in a character grid's clockwise spiral order.
 */
public class SpiralMatrixTraversel {

    enum Direction {
        RIGHT(0, 1), // same row, next column
        DOWN(1, 0),  // next row, same column
        LEFT(0, -1), // same row, previous column
        UP(-1, 0);   // previous row, same column

        final int dRow;
        final int dCol;

        Direction(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }

        // RIGHT → DOWN → LEFT → UP → RIGHT ...
        Direction turnClockwise() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

    /**
     * Traverses the grid clockwise from its top-left cell and returns the positions
     * of vowels in that traversal.
     *
     * @param grid a rectangular grid of lowercase letters
     * @return zero-based positions of vowels in clockwise spiral order
     */
    public static int[] spiralTraverseAndVowels(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new int[0];
        }

        int rows = grid.length, cols = grid[0].length;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;

        int row = 0, col = 0;
        Direction dir = Direction.RIGHT;
        List<Integer> vowels = new ArrayList<>();
        char[] spiralGrid = new char[rows * cols];

        for (int i = 0; i < rows * cols; i++) {
            spiralGrid[i] = grid[row][col];
            if (isVowel(grid[row][col])) vowels.add(i);

            int nextRow = row + dir.dRow, nextColumn = col + dir.dCol;
            if (nextRow < top || nextRow > bottom || nextColumn < left || nextColumn > right) {
                // Finished a side: shrink that boundary, then turn
                switch (dir) {
                    case RIGHT: top++;    break;
                    case DOWN:  right--;  break;
                    case LEFT:  bottom--; break;
                    case UP:    left++;   break;
                }
                dir = dir.turnClockwise();
                nextRow = row + dir.dRow;
                nextColumn = col + dir.dCol;
            }
            row = nextRow;
            col = nextColumn;
        }
        printArray(spiralGrid);
        return vowels.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean isVowel(char ch) {
        return "aeiou".indexOf(ch) >= 0;
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
        SpiralMatrixTraversel app = new SpiralMatrixTraversel();
        char[][] matrix = {
                {'a', 'b', 'c', 'd'},
                {'e', 'f', 'g', 'h'},
                {'i', 'j', 'k', 'l'}
        };
        printMatrix(matrix);
        int[] result = app.spiralTraverseAndVowels(matrix);
        printArray(result);
    }

}
