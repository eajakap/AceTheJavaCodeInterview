package org.educative.modules.module2.queues.puzzles;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Description
 * Imagine a fantasy card duel between two strategists. Each player has a deck, playerDeckA and playerDeckB,
 * and each card showcases a mystical creature with a power value between 1 to 10.
 * During each round, the top cards from both players' decks determine the victor of that round based on these simple rules:
 *
 * Each player reveals the top card of their deck.
 * If the creature's power from playerDeckA is greater than or equal to the one from playerDeckB, the first player
 * claims victory in that round, securing both cards to the bottom of playerDeckA with playerDeckB's card at
 * their card's bottom.
 * If the creature's power from playerDeckA is less than the one from playerDeckB, the second player claims both cards,
 * placing them at the bottom of playerDeckB with playerDeckA's card at their card's bottom.
 * The game ends when a player's deck is empty, leaving them unable to draw another creature for battle.
 * Determine how many rounds this duel lasts. It's guaranteed that the showdown will conclude at some point.
 */
public class CardPlayersGame {
    public enum SolutionType {
        QUEUE_BASED,
        ARRAY_BASED
    }
    /**
     * Simulates a card duel between two players and returns the total number of rounds played.
     * Steps:
     * 1. Initialize a round counter to zero.
     * 2. Convert the input arrays into queues to represent each player's deck.
     * 3. While both decks have cards, repeat the following:
     *    a. Increment the round counter.
     *    b. Draw the top card from each player's deck.
     *    c. Compare the drawn cards:
     *       - If playerDeckA's card is greater than or equal to playerDeckB's card, player A wins the
     *       - round and both cards are added to the bottom of playerDeckA.
     *       - If playerDeckA's card is less than playerDeckB's card, player B wins the round and both cards
     *       - are added to the bottom of playerDeckB.
     * 4. Once one of the decks is empty, return the total number of rounds played.
     * Time Complexity: O(n + m), where n is the number of cards in playerDeckA and m is the number of cards in playerDeckB.
     *                  Each card is processed once.
     * Space Complexity: O(n + m), as we are using queues to store the cards from both players' decks.
     * @param playerDeckA an array representing the first player's deck of cards
     * @param playerDeckB an array representing the second player's deck of cards
     * @return the total number of rounds played until one player's deck is empty
     */
    int solutionUsingQueue(int[] playerDeckA, int[] playerDeckB) {
        int rounds = 0;
        Queue<Integer> deckA = new ArrayDeque<>();
        Queue<Integer> deckB = new ArrayDeque<>();

        for (int card : playerDeckA) {
            deckA.add(card);
        }
        for (int card : playerDeckB) {
            deckB.add(card);
        }

        while (!deckA.isEmpty() && !deckB.isEmpty()) {
            rounds++;
            int cardA = deckA.remove();
            int cardB = deckB.remove();

            if (cardA >= cardB) {
                deckA.add(cardA);
                deckA.add(cardB);
            } else {
                deckB.add(cardB);
                deckB.add(cardA);
            }
        }

        return rounds;
    }

    /**
     * Same simulation as {@link #solutionUsingQueue(int[], int[])} but uses two primitive int circular buffers
     * instead of boxed queues, avoiding Integer allocation. Total cards never change, so each buffer
     * is sized to the combined card count.
     * Time Complexity: O(rounds). Space Complexity: O(n + m).
     */
    int solutionUsingArrays(int[] playerDeckA, int[] playerDeckB) {
        if (playerDeckA.length == 0 || playerDeckB.length == 0) {
            return 0;
        }

        int capacity = playerDeckA.length + playerDeckB.length;
        int[] deckA = new int[capacity];
        int[] deckB = new int[capacity];
        System.arraycopy(playerDeckA, 0, deckA, 0, playerDeckA.length);
        System.arraycopy(playerDeckB, 0, deckB, 0, playerDeckB.length);

        int headA = 0;
        int sizeA = playerDeckA.length;
        int headB = 0;
        int sizeB = playerDeckB.length;
        long rounds = 0;

        while (sizeA > 0 && sizeB > 0) {
            rounds++;
            int cardA = deckA[headA]; // Draw the top card from playerDeckA
            int cardB = deckB[headB]; // Draw the top card from playerDeckB
            // Move the head pointers forward and decrease the sizes of the decks
            headA = headA + 1 == capacity ? 0 : headA + 1; // Move headA forward in a circular manner
            sizeA--; // Decrease the size of playerDeckA
            headB = headB + 1 == capacity ? 0 : headB + 1; // Move headB forward in a circular manner
            sizeB--; // Decrease the size of playerDeckB

            if (cardA >= cardB) {
                // Player A wins the round, add both cards to the bottom of playerDeckA.
                // if the tail index reaches the capacity, wrap around to the beginning of the array to place playerDeckA's cardA.
                int tail = (headA + sizeA) % capacity;
                deckA[tail] = cardA;
                // if the tail index reaches the capacity, wrap around to the beginning of the array to place playerDeckA's cardB.
                deckA[tail + 1 == capacity ? 0 : tail + 1] = cardB;
                sizeA += 2;
            } else {
                // Player B wins the round, add both cards to the bottom of playerDeckB.
                // if the tail index reaches the capacity, wrap around to the beginning of the array to place playerDeckB's cardB.
                int tail = (headB + sizeB) % capacity;
                deckB[tail] = cardB;
                // if the tail index reaches the capacity, wrap around to the beginning of the array to place playerDeckB's cardA.
                deckB[tail + 1 == capacity ? 0 : tail + 1] = cardA;
                sizeB += 2;
            }
        }

        return (int) rounds;
    }

    int solution(int[] playerDeckA, int[] playerDeckB, SolutionType type) {
        switch (type) {
            case QUEUE_BASED:
                return solutionUsingQueue(playerDeckA, playerDeckB);
            case ARRAY_BASED:
                return solutionUsingArrays(playerDeckA, playerDeckB);
            default:
                throw new IllegalArgumentException("Unsupported solution type: " + type);
        }
    }

    public static void main(String[] args) {
        CardPlayersGame game = new CardPlayersGame();
        int[][] playerDecksA = {
                {5, 3, 8, 6},
                {1},
                {10, 10, 10},
                {2, 9, 4},
                {1000000000, 2147483647, 5},
                {7, 7, 1, 3, 9}
        };
        int[][] playerDecksB = {
                {4, 7, 2, 9},
                {2},
                {10, 10},
                {3, 3, 8},
                {2147483647, 1, 1000000000},
                {7, 8, 2, 6, 4}
        };

        for (int i = 0; i < playerDecksA.length; i++) {
            int[] playerDeckA = playerDecksA[i];
            int[] playerDeckB = playerDecksB[i];
            int roundsQueue = game.solution(playerDeckA, playerDeckB, SolutionType.QUEUE_BASED);
            int roundsArray = game.solution(playerDeckA, playerDeckB, SolutionType.ARRAY_BASED);
            System.out.println((i + 1) + ".\tPlayer A deck: " + Arrays.toString(playerDeckA));
            System.out.println("\tPlayer B deck: " + Arrays.toString(playerDeckB));
            System.out.println("\tRounds (queue): " + roundsQueue);
            System.out.println("\tRounds (arrays): " + roundsArray);
            System.out.println("-".repeat(100));
        }
    }
}
