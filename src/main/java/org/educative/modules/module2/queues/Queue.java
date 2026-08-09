package org.educative.modules.module2.queues;

import java.util.ArrayDeque;
import java.util.Deque;

import java.util.Map;
import java.util.TreeMap;

class Player implements Comparable<Player> {
    String name;
    int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Player other) {
        if (this.score == other.score) {
            return this.name.compareTo(other.name);
        }
        return Integer.compare(this.score, other.score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return score == player.score && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return (name + score).hashCode(); // Simple hash code generation
    }

    @Override
    public String toString() {
        return "(" + name + ", " + score + ")";
    }

    public static void main(String[] args) {
        TreeMap<Player, Integer> scores = new TreeMap<>();

        Player player1 = new Player("John", 900);
        Player player2 = new Player("Doe", 1000);

        // Adding players to the TreeMap
        scores.put(player1, player1.score);
        scores.put(player2, player2.score);

        // Print TreeMap
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey()); // e.g., (John, 900)
        }
    }
}

class Queue {
    private Deque<String> buffer;

    public Queue() {
        // Initializing an empty queue
        buffer = new ArrayDeque<>();
    }

    // Adding (enqueueing) an item to the queue
    public void enqueue(String val) {
        buffer.addLast(val);
    }

    // Removing (dequeuing) an item from the queue
    public String dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return buffer.removeFirst();
    }

    // Checking if the queue is empty
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    // Checking the size (number of items) in the queue
    public int size() {
        return buffer.size();
    }

    public static void main(String[] args) {
        Queue restaurantQueue = new Queue();

        restaurantQueue.enqueue("Order 1");
        restaurantQueue.enqueue("Order 2");

        System.out.println("Dequeued: " + restaurantQueue.dequeue());
        System.out.println("Dequeued: " + restaurantQueue.dequeue());
    }
}