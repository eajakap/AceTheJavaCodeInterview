package org.codesignal.debugging.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Analyzes student competition logs and ranks students by their accumulated scores.
 */
public class StudentScoreCard {
    /**
     * Summarizes each student's solved problems and failed attempts from the competition log.
     *
     * <p>Each entry has the form {@code <studentId> <action> <timestamp> [score]}.
     * The action is either {@code solve}, which requires a score, or {@code fail},
     * which has no score. Entries are separated by commas. Timestamps are accepted
     * as part of the entry but are not used in the analysis.
     *
     * <p>Each returned array has the form
     * {@code [studentId, totalScore, successfulAttempts, failedAttempts]}. Results
     * are sorted by descending total score, and students with no successful attempts
     * are omitted. An empty or whitespace-only log returns an empty list.
     *
     * @param logs comma-separated competition log entries
     * @return student summaries ordered by descending total score
     * @throws NullPointerException if {@code logs} is {@code null}
     * @throws IllegalArgumentException if an entry is malformed or has an unsupported action
     * @throws NumberFormatException if a student ID or score is not a valid integer
     */
    public List<int[]> analyzeCompetition(String logs) {
        Objects.requireNonNull(logs, "logs must not be null");
        Map<Integer, int[]> studentData = new HashMap<>();

        if (logs.isBlank()) {
            return new ArrayList<>();
        }

        for (String log : logs.split(",\\s*", -1)) {
            String[] parts = log.trim().split("\\s+");
            if (parts.length < 3) {
                throw new IllegalArgumentException("Invalid log entry: " + log);
            }

            int id = Integer.parseInt(parts[0]);
            int[] data = studentData.computeIfAbsent(id, key -> new int[3]);
            switch (parts[1]) {
                case "solve":
                    if (parts.length != 4) {
                        throw new IllegalArgumentException("Invalid solve entry: " + log);
                    }
                    data[0] += Integer.parseInt(parts[3]);
                    data[1]++;
                    break;
                case "fail":
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Invalid fail entry: " + log);
                    }
                    data[2]++;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action in log entry: " + log);
            }
        }

        List<int[]> results = new ArrayList<>();
        for (Map.Entry<Integer, int[]> entry : studentData.entrySet()) {
            int id = entry.getKey();
            int[] data = entry.getValue();
            if (data[1] > 0) {
                results.add(new int[] {id, data[0], data[1], data[2]});
            }
        }
        results.sort((a, b) -> Integer.compare(b[1], a[1]));
        return results;
    }

    public static void main(String[] args) {
        StudentScoreCard app = new StudentScoreCard();
        String logs = "1 solve 09:00 50, 2 solve 10:00 60, 1 fail 11:00, 3 solve 13:00 40, 2 fail 14:00, 3 solve 15:00 70";
        System.out.println(logs);
        List<int[]> results = app.analyzeCompetition(logs);
        results.forEach(result -> System.out.println(Arrays.toString(result)));
    }
}