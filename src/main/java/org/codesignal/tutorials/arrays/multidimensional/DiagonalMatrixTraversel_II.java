package org.codesignal.tutorials.arrays.multidimensional;

import java.util.ArrayList;
import java.util.List;

/**
 *Given a matrix of n x m integers, where n represents the number of rows and m represents the number of columns.
 * Both n and m range from 1 to 100, inclusive.
 *
 * The matrix cells may contain either a positive, a negative integer, or zero, with values ranging from -100 to 100, inclusive.
 *
 * In this task, you are required to traverse the matrix diagonally from the top-left cell
 * to the bottom-right cell in a zigzag pattern.
 * Start from the top-left cell, move one cell to the right (if it exists),
 * then move one step diagonally down-left. After reaching a left (bottom) boundary,
 * move one step down (right) and start moving diagonally up-right.
 * Continue this pattern until you reach the last cell of the matrix.
 * Your task is to return a list of tuples, each tuple containing the index pair (in 0-based indexing format) of cells
 * with negative integers encountered during your traversal.
 */
public class DiagonalMatrixTraversel_II {
    private void printMatrix(int[][] matrix) {
        System.out.println("_".repeat(100));
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<int[]> solution(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        List<int[]> result = new ArrayList<>();
        int row = 0, col = 0;
        boolean movingUpRight = true;

        for (int i = 0; i < rows * cols; i++) {
            if (matrix[row][col] < 0) {
                result.add(new int[] {row, col});
            }

            if (i + 1 == rows * cols) {
                break;
            }

            if (movingUpRight) {
                if (col == cols - 1) {
                    row++;
                    movingUpRight = false;
                } else if (row == 0) {
                    col++;
                    movingUpRight = false;
                } else {
                    row--;
                    col++;
                }
            } else {
                if (row == rows - 1) {
                    col++;
                    movingUpRight = true;
                } else if (col == 0) {
                    row++;
                    movingUpRight = true;
                } else {
                    row++;
                    col--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DiagonalMatrixTraversel_II app = new DiagonalMatrixTraversel_II();
        int[][] matrix = {
                {1, -2, 3, -4},
                {5, -6, 7, 8},
                {-9, 10, -11, 12}
        };
        app.printMatrix(matrix);
        List<int[]> result = app.solution(matrix);
        System.out.print("Negative numbers: [");
        for (int[] coords : result) {
            System.out.print("[" + coords[0] + ", " + coords[1] + "], ");
        }
        System.out.println("]");
    }
}