package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardPlayersGameTest {
    private final CardPlayersGame game = new CardPlayersGame();

    private void assertRounds(int expected, int[] a, int[] b) {
        assertEquals(expected, game.solutionUsingQueue(a, b), "queue based");
        assertEquals(expected, game.solutionUsingArrays(a, b), "array based");
        assertEquals(expected, game.solution(a, b, CardPlayersGame.SolutionType.QUEUE_BASED));
        assertEquals(expected, game.solution(a, b, CardPlayersGame.SolutionType.ARRAY_BASED));
    }

    @Test
    void sampleGame() {
        assertRounds(10, new int[]{5, 3, 8, 6}, new int[]{4, 7, 2, 9});
    }

    @Test
    void singleRoundWhenBothHaveOneCard() {
        assertRounds(1, new int[]{1}, new int[]{2});
        assertRounds(1, new int[]{2}, new int[]{1});
        assertRounds(1, new int[]{3}, new int[]{3});
    }

    @Test
    void tiesGoToPlayerA() {
        assertRounds(2, new int[]{10, 10, 10}, new int[]{10, 10});
    }

    @Test
    void longerGames() {
        assertRounds(13, new int[]{2, 9, 4}, new int[]{3, 3, 8});
        assertRounds(19, new int[]{7, 7, 1, 3, 9}, new int[]{7, 8, 2, 6, 4});
    }

    @Test
    void handlesVeryLargeCardValues() {
        assertRounds(9, new int[]{1_000_000_000, Integer.MAX_VALUE, 5},
                new int[]{Integer.MAX_VALUE, 1, 1_000_000_000});
    }

    @Test
    void emptyDeckMeansNoRounds() {
        assertRounds(0, new int[]{}, new int[]{1, 2});
        assertRounds(0, new int[]{1, 2}, new int[]{});
    }

    @Test
    void queueAndArrayVersionsAgreeOnManyRandomGames() {
        java.util.Random random = new java.util.Random(42);
        for (int game = 0; game < 200; game++) {
            int[] a = random.ints(1 + random.nextInt(8), 1, 11).toArray();
            int[] b = random.ints(1 + random.nextInt(8), 1, 11).toArray();
            // Games are only guaranteed to finish for some inputs; skip non-terminating ones.
            int expected = runWithRoundLimit(a, b);
            if (expected < 0) {
                continue;
            }
            assertEquals(expected, this.game.solutionUsingArrays(a, b));
        }
    }

    private static int runWithRoundLimit(int[] a, int[] b) {
        java.util.ArrayDeque<Integer> deckA = new java.util.ArrayDeque<>();
        java.util.ArrayDeque<Integer> deckB = new java.util.ArrayDeque<>();
        for (int c : a) deckA.add(c);
        for (int c : b) deckB.add(c);
        int rounds = 0;
        while (!deckA.isEmpty() && !deckB.isEmpty()) {
            if (++rounds > 10_000) {
                return -1;
            }
            int x = deckA.remove();
            int y = deckB.remove();
            if (x >= y) {
                deckA.add(x);
                deckA.add(y);
            } else {
                deckB.add(y);
                deckB.add(x);
            }
        }
        return rounds;
    }

    @Test
    void playerBWinsWhenHoldingStrongerCards() {
        assertRounds(1, new int[]{1}, new int[]{10});
        assertRounds(3, new int[]{1, 1, 1}, new int[]{9, 9, 9});
    }

    @Test
    void inputDecksAreNotModified() {
        int[] a = {5, 3, 8, 6};
        int[] b = {4, 7, 2, 9};
        game.solutionUsingQueue(a, b);
        game.solutionUsingArrays(a, b);
        assertArrayEquals(new int[]{5, 3, 8, 6}, a);
        assertArrayEquals(new int[]{4, 7, 2, 9}, b);
    }
}
