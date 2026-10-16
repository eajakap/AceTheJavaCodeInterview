package org.codesignal.debugging.problems;

import java.util.*;

public class VotingSystem {
    private Map<String, Integer> candidates;  // Stores candidate_id as key and votes as value
    private Map<String, VotingHistory> voters;  // Tracks each voter's voting history

    public VotingSystem() {
        this.candidates = new HashMap<>();  // Initialize candidates map
        this.voters = new HashMap<>();  // Initialize voters map
    }

    public boolean registerCandidate(String candidateId) {
        if (candidates.containsKey(candidateId)) {
            return false;  // Candidate is already registered
        }
        candidates.put(candidateId, 0);  // Initialize candidates with 0 votes
        return true;
    }

    public boolean vote(long timestamp, String voterId, String candidateId) {
        if (blockTime != null && timestamp > blockTime) {
            return false;  // Voting is blocked after the block time
        }
        if (!candidates.containsKey(candidateId)) {
            return false;  // Return false if candidate is not registered
        }
        VotingHistory voterHistory = voters.computeIfAbsent(voterId, k -> new VotingHistory());
        voterHistory.getVotes().add(candidateId);  // Record the vote
        voterHistory.getTimestamps().add(timestamp);  // Record the time of the vote
        candidates.put(candidateId, candidates.get(candidateId) + 1);  // Increment vote count for the candidate
        return true;
    }

    public Integer getVotes(String candidateId) {
        return candidates.get(candidateId);  // Retrieve vote count for a candidate or null if not found
    }

    public List<String> topNCandidates(int n) {
        return candidates.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();  // Return top n candidates based on votes
    }

    private Long blockTime = null;  // Time after which registrations are blocked

    public Map<String, Integer> getVotingHistory(String voterId) {
        if (!voters.containsKey(voterId)) {
            return null;
        }
        VotingHistory voterHistory = voters.get(voterId);
        Map<String, Integer> votingHistory = new HashMap<>();
        for (String candidateId : voterHistory.getVotes()) {
            votingHistory.put(candidateId, votingHistory.getOrDefault(candidateId, 0) + 1);
        }
        return votingHistory;  // Returns a map with each candidate voted for and the number of times they were voted for
    }

    public boolean blockVoterRegistration(long timestamp) {
        this.blockTime = timestamp;  // Records the timestamp after which no registration is allowed
        return true;
    }

    public boolean changeVote(long timestamp, String voterId, String oldCandidateId, String newCandidateId) {
        // Verify existence in the voting system
        if (!candidates.containsKey(oldCandidateId) || !candidates.containsKey(newCandidateId)) {
            return false;
        }

        // Check if the voter_id is valid and has previously voted
        if (!voters.containsKey(voterId) || voters.get(voterId).getVotes().isEmpty()) {
            return false;  // Voter is either not registered or has not voted yet
        }

        VotingHistory voterHistory = voters.get(voterId);
        // Confirm voter has voted for the old candidate
        if (!voterHistory.getVotes().contains(oldCandidateId)) {
            return false;
        }

        // Ensure the operation is within the permitted timeframe
        int lastVoteIndex = voterHistory.getVotes().lastIndexOf(oldCandidateId);
        if (timestamp - voterHistory.getTimestamps().get(lastVoteIndex) > 86400) {  // 24 hours in seconds
            return false;
        }

        // Perform the vote change
        // Remove the old vote
        voterHistory.getVotes().remove(lastVoteIndex);
        voterHistory.getTimestamps().remove(lastVoteIndex);

        // Append the new vote
        voterHistory.getVotes().add(newCandidateId);
        voterHistory.getTimestamps().add(timestamp);

        // Update candidates' vote counts
        candidates.put(oldCandidateId, candidates.get(oldCandidateId) - 1);
        candidates.put(newCandidateId, candidates.get(newCandidateId) + 1);

        return true;
    }

    private static class VotingHistory {
        private final List<String> votes;
        private final List<Long> timestamps;

        public VotingHistory() {
            this.votes = new ArrayList<>();
            this.timestamps = new ArrayList<>();
        }

        public List<String> getVotes() {
            return votes;
        }

        public List<Long> getTimestamps() {
            return timestamps;
        }
    }

    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        votingSystem.registerCandidate("Alice");
        votingSystem.registerCandidate("Bob");
        votingSystem.vote(1, "voter1", "Alice");
        votingSystem.vote(2, "voter2", "Bob");
        votingSystem.vote(3, "voter1", "Bob");  // voter1 votes for Bob
        System.out.println(votingSystem.getVotes("Alice"));  // Output: 1
        System.out.println(votingSystem.getVotes("Bob"));    // Output: 2
        System.out.println(votingSystem.topNCandidates(1));  // Output: [Bob]
    }
}