package org.codesignal.multidimensional.arrays;

import java.util.ArrayList;
import java.util.List;

public class GridTraversalDemo {
    // Finding Ascending Paths in 2D Arrays in Java
    public static void findAscendingPaths(int[][] grid, int row, int col, String path) {
        // Check for out-of-bounds
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return;
        }

        // Append current cell to the path
        path += grid[row][col] + " ";

        // Explore all four directions (up, down, left, right)
        findAscendingPaths(grid, row - 1, col, path); // Up
        findAscendingPaths(grid, row + 1, col, path); // Down
        findAscendingPaths(grid, row, col - 1, path); // Left
        findAscendingPaths(grid, row, col + 1, path); // Right

        // Print the path when reaching a boundary or visited cell
        System.out.println(path);
    }

    public static List<Integer> pathTraverse(int[][] grid, int startRow, int startCol) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Check the validity of the input
        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols) {
            System.err.println("Invalid input");
            return new ArrayList<>();
        }

        // Directions for moving in the grid: up, down, left, right (rowoffset, coloffset)
        int[][] directions = {
                {-1, 0},  // Up
                {1, 0},   // Down
                {0, -1},  // Left
                {0, 1}    // Right
        };
        // Start with the value at the starting cell
        List<Integer> visited = new ArrayList<>();
        visited.add(grid[startRow][startCol]);

        while (true) {
            // Initialize the current maximum as negative one
            int currMax = -1;
            int nextRow = -1, nextCol = -1;

            // Loop over each adjacent cell in all the directions
            for (int[] dir : directions) {
                // Calculate the new cell's row and column indices
                int newRow = startRow + dir[0];
                int newCol = startCol + dir[1];

                // If the new cell is out of the grid boundary, ignore it
                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                    continue;
                }

                // If the new cell's value is greater than the current maximum
                if (grid[newRow][newCol] > currMax) {
                    // Save it as the next cell to visit
                    nextRow = newRow;
                    nextCol = newCol;
                    currMax = grid[newRow][newCol];
                }
            }

            // If we don't have any valid cell to visit, break from the loop
            if (currMax <= grid[startRow][startCol]) {
                break;
            }

            // Otherwise, go to the next cell
            startRow = nextRow;
            startCol = nextCol;

            // Append the cell's value to the result list
            visited.add(currMax);
        }
        // Return the list of visited cells
        return visited;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Grid Traversal:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Traversal path:");
        List<Integer> res = pathTraverse(grid, 1, 1);
        for (int val : res) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}
