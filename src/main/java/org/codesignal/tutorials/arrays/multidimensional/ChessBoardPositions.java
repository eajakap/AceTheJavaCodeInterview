package org.codesignal.tutorials.arrays.multidimensional;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardPositions {

    private static boolean isEmptyNeighbor(char[][] board, int x, int y) {
        int rows = board.length;
        int cols = board[0].length;

        // Check that (x, y) is within board boundaries
        if (x >= 0 && x < rows && y >= 0 && y < cols) {
            return board[x][y] == 'E';
        }
        return false;
    }

    public static List<int[]> findPositions(char[][] board) {
        List<int[]> positions = new ArrayList<>();

        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'E') {
                    if (isEmptyNeighbor(board, i - 1, j) || // top neighbor
                            isEmptyNeighbor(board, i + 1, j) || // bottom neighbor
                            isEmptyNeighbor(board, i, j - 1) || // left neighbor
                            isEmptyNeighbor(board, i, j + 1)) // right neighbor
                    {
                        positions.add(new int[]{i, j});
                    }
                }
            }
        }
        return positions;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'P', 'E', 'E', 'P'},
                {'E', 'P', 'E', 'P'},
                {'P', 'E', 'P', 'P'},
                {'P', 'E', 'P', 'E'}
        };

        List<int[]> positions = findPositions(board);

        for (int[] pos : positions) {
            System.out.println("(" + pos[0] + ", " + pos[1] + ")");
        }
    }


}
