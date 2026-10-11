package org.educative.modules.module2.queues;

import java.util.ArrayDeque;
import java.util.Deque;

import java.util.Map;
import java.util.TreeMap;


/**
 * A FIFO queue backed by a {@link Deque}, with examples using strings and players.
 *
 * @param <T> type of elements stored in the queue
 */
public class FiFoQueueSolution<T> {
    /**
     * A player ordered first by score and then by name.
     */
    public static class Player implements Comparable<Player> {
        String name;
        int score;

        /**
         * Creates a player with the given name and score.
         *
         * @param name player's name
         * @param score player's score
         */
        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        /**
         * Compares players by ascending score, using name to break score ties.
         *
         * @param other player to compare with
         * @return a negative value, zero, or a positive value as this player sorts
         *         before, equal to, or after {@code other}
         */
        @Override
        public int compareTo(Player other) {
            if (this.score == other.score) {
                return this.name.compareTo(other.name);
            }
            return Integer.compare(this.score, other.score);
        }

        /**
         * Checks whether another object has the same name and score.
         *
         * @param obj object to compare
         * @return {@code true} if the object is an equal player; {@code false} otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Player player = (Player) obj;
            return score == player.score && name.equals(player.name);
        }

        /**
         * Computes a hash code from this player's name and score.
         *
         * @return hash code for this player
         */
        @Override
        public int hashCode() {
            return (name + score).hashCode(); // Simple hash code generation
        }

        /**
         * Returns this player's name and score.
         *
         * @return player details in {@code (name, score)} format
         */
        @Override
        public String toString() {
            return "(" + name + ", " + score + ")";
        }

        /**
         * Demonstrates storing players in score order using a {@link TreeMap}.
         *
         * @param args command-line arguments (not used)
         */
        public static void main(String[] args) {
            TreeMap<Player, Integer> scores = new TreeMap<>();

            Player player1 = new Player("John", 900);
            Player player2 = new Player("Doe", 1000);

            // Adding players to the TreeMap
            scores.put(player1, player1.score);
            scores.put(player2, player2.score);

            // TreeMap iterates entries in the order defined by Player.compareTo.
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                System.out.println(entry.getKey()); // e.g., (John, 900)
            }
        }
    }

    /** Queue storage; the deque's front is the queue's front. */
    private Deque<T> buffer;

    /**
     * Creates an empty queue.
     */
    public FiFoQueueSolution() {
        buffer = new ArrayDeque<>();
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param val element to enqueue
     * @throws NullPointerException if {@code val} is {@code null}
     */
    public void enqueue(T val) {
        buffer.addLast(val);
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the oldest enqueued element
     * @throws IllegalStateException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return buffer.removeFirst();
    }

    /**
     * Checks whether the queue contains no elements.
     *
     * @return {@code true} if the queue is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    /**
     * Returns the number of elements currently in the queue.
     *
     * @return current queue size
     */
    public int size() {
        return buffer.size();
    }

    /**
     * Demonstrates adding and removing strings and players from FIFO queues.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        FiFoQueueSolution<String> restaurantQueue = new FiFoQueueSolution<>();

        restaurantQueue.enqueue("Order 1");
        restaurantQueue.enqueue("Order 2");

        System.out.println("Dequeued: " + restaurantQueue.dequeue());
        System.out.println("Dequeued: " + restaurantQueue.dequeue());

        FiFoQueueSolution<Player> playerQueue = new FiFoQueueSolution<>();
        playerQueue.enqueue(new Player("John", 900));
        playerQueue.enqueue(new Player("Doe", 1000));

        System.out.println("Dequeued player: " + playerQueue.dequeue());
        System.out.println("Dequeued player: " + playerQueue.dequeue());
    }
}