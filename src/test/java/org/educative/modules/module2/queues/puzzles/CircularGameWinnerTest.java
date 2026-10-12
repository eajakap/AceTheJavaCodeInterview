package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircularGameWinnerTest {
    private final CircularGameWinner solution = new CircularGameWinner();

    @Test
    void findsWinnerForTypicalGames() {
        assertEquals(1, solution.findTheWinner(4, 2));
        assertEquals(4, solution.findTheWinner(7, 3));
        assertEquals(5, solution.findTheWinner(10, 4));
    }

    @Test
    void singleFriendAlwaysWins() {
        assertEquals(1, solution.findTheWinner(1, 1));
        assertEquals(1, solution.findTheWinner(1, 5));
    }

    @Test
    void kEqualToN() {
        assertEquals(4, solution.findTheWinner(8, 8));
    }

    @Test
    void kOfOneMakesLastFriendWin() {
        assertEquals(6, solution.findTheWinner(6, 1));
        assertEquals(100, solution.findTheWinner(100, 1));
    }

    @Test
    void twoFriends() {
        assertEquals(2, solution.findTheWinner(2, 1));
        assertEquals(1, solution.findTheWinner(2, 2));
        assertEquals(2, solution.findTheWinner(2, 3));
    }

    @Test
    void kLargerThanN() {
        assertEquals(1, solution.findTheWinner(3, 5));
        assertEquals(4, solution.findTheWinner(5, 6));
    }
}
