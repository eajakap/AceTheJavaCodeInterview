package org.educative.modules.module2.maps;

import java.util.Map;
import java.util.TreeMap;

public class PlayerSolution {

    static class Player implements Comparable<Player> {
        String name;
        int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(this.score, other.score);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Player player = (Player) o;
            return score == player.score && name.equals(player.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode() + score;
        }

        @Override
        public String toString() {
           return "Player{name='" + name + "', score=" + score + "}";
        }
    }

    public static void main(String[] args) {
        TreeMap<Player, String> tournament = new TreeMap<>();
        // TODO: Add two Player objects to the tournament. Use name as 'Amy' with score 87 and 'Brad' with score 75. Assign them placements accordingly.

        // Your code here to print the players' details
        tournament.put(new Player("Amy", 87), "Amy");
        tournament.put(new Player("Brad", 75), "Brad");

        for (Map.Entry<Player, String> entry : tournament.entrySet()) {
            Player player = entry.getKey();
            System.out.println(player);
        }
    }

}