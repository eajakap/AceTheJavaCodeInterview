package org.codesignal.debugging.problems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Potluck {
    private Map<String, Long> participants;
    private Map<String, String> dishes;
    private Map<String, String> votes;

    public Potluck() {
        participants = new HashMap<>();
        dishes = new HashMap<>();
        votes = new HashMap<>();
    }

    public boolean addParticipant(String memberId) {
        if (participants.containsKey(memberId)) {
            return false;
        } else {
            participants.put(memberId, System.currentTimeMillis());
            return true;
        }
    }

    public boolean removeParticipant(String memberId) {
        if (!participants.containsKey(memberId)) {
            return false;
        } else {
            participants.remove(memberId);
            dishes.remove(memberId);
            votes.remove(memberId);
            return true;
        }
    }

    public boolean addDish(String memberId, String dishName) {
        if (!participants.containsKey(memberId) || dishes.containsKey(memberId)) {
            return false;
        } else {
            dishes.put(memberId, dishName);
            return true;
        }
    }

    public boolean vote(String memberId, String voteId) {
        if (participants.containsKey(memberId) && !votes.containsKey(memberId)) {
            votes.put(memberId, voteId);
            return true;
        } else {
            return false;
        }
    }

    public String dishOfTheDay() {
        if (votes.isEmpty()) {
            return null;
        }

        // Count votes for each dish
        Map<String, Integer> voteCount = new HashMap<>();
        for (String vote : votes.values()) {
            voteCount.put(vote, voteCount.getOrDefault(vote, 0) + 1);
        }

        // Determine maximum votes
        int maxVotes = voteCount.values().stream().max(Integer::compare).orElse(0);

        // Collect all dishes with maximum votes
        Set<String> maxVoteDishes = new HashSet<>();
        for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() == maxVotes) {
                maxVoteDishes.add(entry.getKey());
            }
        }

        // Find the earliest joining time among the tied dishes
        long earliestJoinTime = Long.MAX_VALUE;
        String dishOfTheDayMember = null;
        for (String memberId : maxVoteDishes) {
            if (participants.get(memberId) < earliestJoinTime) {
                earliestJoinTime = participants.get(memberId);
                dishOfTheDayMember = memberId;
            }
        }

        return "Participant: '" + dishOfTheDayMember + "', Dish: '" + dishes.get(dishOfTheDayMember) + "'";
    }

    public static void main(String[] args) {
        Potluck potluck = new Potluck();
        potluck.addParticipant("Alice");
        potluck.addParticipant("Bob");
        potluck.addParticipant("Charlie");

        potluck.addDish("Alice", "Pasta");
        potluck.addDish("Bob", "Salad");
        potluck.addDish("Charlie", "Pizza");

        potluck.vote("Alice", "Bob"); // Alice votes for Bob's dish
        potluck.vote("Bob", "Charlie"); // Bob votes for Charlie's dish
        potluck.vote("Charlie", "Alice"); // Charlie votes for Alice's dish

        System.out.println(potluck.dishOfTheDay()); // Should print the dish with the most votes
    }
}