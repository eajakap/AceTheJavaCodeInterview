package org.codesignal.tutorials.arrays.multidimensional;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for traversing matrix diagonals and transforming their values.
 */
public class DiagonalMatrixTraversal {

    /**
     * Squares the values on the matrix's secondary diagonal.
     *
     * <p>The diagonal is selected by {@code row + column == rows - 1}. Values are
     * collected in row-major order before being squared.
     *
     * <p>Steps:
     * <ol>
     *   <li>Read the matrix dimensions and initialize the traversal and result lists.</li>
     *   <li>Visit each matrix cell and collect values that satisfy the secondary-diagonal condition.</li>
     *   <li>Square each collected value and return the squared values.</li>
     * </ol>
     *
     * @param matrix a non-empty rectangular matrix
     * @return the squared values from the secondary diagonal, in row-major order
     */
    public static List<Integer> diagonalTraverseAndSquaresV1(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        List<Integer> traversal = new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i + j == rows - 1) {
                    traversal.add(matrix[i][j]);
                }
            }
        }
        for (int num : traversal) {
            results.add(num * num);
        }
        return results;
    }

    /**
     * Traverses the matrix in alternating up-right and down-left diagonal order.
     *
     * <p>Steps:
     * <ol>
     *   <li>Start at the top-left cell, moving down-left.</li>
     *   <li>Append the current cell value to the traversal.</li>
     *   <li>At a matrix edge, move to the next diagonal's starting cell and reverse direction;
     *       otherwise, move one cell along the current diagonal.</li>
     *   <li>Repeat until every cell has been visited.</li>
     *   <li>Check each traversed value and collect its zero-based traversal index if it is a
     *       perfect square.</li>
     * </ol>
     *
     * <p>The traversal is also printed to standard output.
     *
     * @param matrix a non-empty rectangular matrix
     * @return zero-based indices of perfect-square values in the diagonal traversal order
     */
    public static List<Integer> diagonalTraverseAndSquares(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        List<Integer> traversal = new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        int row = 0, col = 0, dir = 1; // 1 - implies down-left, -1 - implies up-right.
        for (int i = 0; i < rows * cols; ++i) {  // Loop runs for the total number of cells in the matrix.
            traversal.add(matrix[row][col]);  // Append the current cell value to traversal.

            // Logic to control direction based on edges:
            if (dir == 1) {  // Moving down-left
                if (row == rows - 1) {
                    col += 1;
                    dir = -1; // reverse direction to move up-right.
                } else if (col == 0) {
                    row += 1; //
                    dir = -1; // reverse direction to move up-right.
                } else {
                    row += 1;
                    col -= 1;
                }
            } else {  // Moving up-right
                if (col == cols - 1) {
                    row += 1;
                    dir = 1; // reverse direction to move down-left.
                } else if (row == 0) {
                    col += 1;
                    dir = 1; // reverse direction to move down-left.
                } else {
                    row -= 1;
                    col += 1;
                }
            }
        }
        System.out.println(traversal);
        for (int idx = 0; idx < traversal.size(); ++idx) {
            int val = traversal.get(idx);
            int root = (int) Math.sqrt(val);
            if (root * root == val) {  // Check if the value is a perfect square number.
                results.add(idx);
            }
        }

        return results;
    }

    /**
     * Runs both diagonal methods with a sample matrix and prints their results.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(diagonalTraverseAndSquaresV1(matrix));
        System.out.println(diagonalTraverseAndSquares(matrix));

    }
}