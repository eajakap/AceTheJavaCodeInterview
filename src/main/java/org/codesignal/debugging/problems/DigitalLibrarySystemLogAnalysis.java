package org.codesignal.debugging.problems;

import java.util.*;

/**
 * You are provided with log data from a library's digital system, stored in string format.
 * The log represents books' borrowing activities, including the book ID and the time a book is borrowed and returned.
 * The structure of a log entry is as follows: <book_id> borrow <time>, <book_id> return <time>.
 *
 * The time is given in the HH:MM 24-hour format, and the book ID is a positive integer between 1 and 500.
 * The logs are separated by a comma, followed by a space (", ").
 *
 * Your task is to create a Java function named solution().
 * This function will take as input a string of logs and output a list of strings representing the books
 * with the longest borrowed duration. Each string contains the book ID and the book's borrowed duration,
 * concatenated by a space. By 'borrowed duration,' we mean the period from when the book is
 * borrowed until it is returned. If a book has been borrowed and returned multiple times,
 * the borrowed duration is the total cumulative sum of those durations.
 * If multiple books share the same longest borrowed duration, the function should return all such books
 * in ascending order of their IDs.
 *
 * For example, if we have a log string as follows:
 * "1 borrow 09:00, 2 borrow 10:00, 1 return 12:00, 3 borrow 13:00, 2 return 15:00, 3 return 16:00",
 * the function should return the list ["2 05:00"].
 *
 * Note: You can safely assume that all borrowing actions for a given book will have a corresponding return
 * action in the log, and vice versa. Also, the logs are sorted by the time of the action.
 */
public class DigitalLibrarySystemLogAnalysis {

    public List<String> solution(String logs) {
        Map<Integer, Integer> borrowDurations = new TreeMap<>();
        Map<Integer, Integer> borrowTimes = new HashMap<>();
        String[] logArray = logs.split(", ");
        for (String log : logArray) {
            String[] logParts = log.split(" ");
            String bookId = logParts[0];
            String action = logParts[1];
            String time = logParts[2];
            int bookIdValue = Integer.parseInt(bookId);
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3, 5));
            int currentTimeInMinutes = hour * 60 + minute;
            if (action.equals("borrow")) {
                // Handle borrow action
                borrowTimes.put(bookIdValue, currentTimeInMinutes);
            } else if (action.equals("return")) {
                // Handle return action
                int borrowTime = borrowTimes.get(bookIdValue);
                int duration = currentTimeInMinutes - borrowTime;
                borrowDurations.put(bookIdValue, borrowDurations.getOrDefault(bookIdValue, 0) + duration);
            }
        }

        int maxDuration = Collections.max(borrowDurations.values());

        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : borrowDurations.entrySet()) {
            int bookId = entry.getKey();
            int borrowedDuration = entry.getValue();
            if (borrowedDuration == maxDuration) {
                int hours = borrowedDuration / 60;
                int minutes = borrowedDuration % 60;
                String formattedTime = String.format("%02d:%02d", hours, minutes);
                String resultElement = bookId + " " + formattedTime;
                result.add(resultElement);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String logs = "1 borrow 09:00, 2 borrow 10:00, 1 return 12:00, 3 borrow 13:00, 2 return 15:00, 3 return 16:00";
        DigitalLibrarySystemLogAnalysis app = new DigitalLibrarySystemLogAnalysis();
        List<String> result = app.solution(logs);
        System.out.println("Longest borrowed duration: " + result);
    }
}
