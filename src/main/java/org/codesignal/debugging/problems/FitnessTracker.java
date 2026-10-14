package org.codesignal.debugging.problems;

import java.util.*;
import java.util.function.Predicate;

public class FitnessTracker {
    public record Event (String activityType, int distance, long eventTime) {
    }

    Comparator<Event> comparator = Comparator.comparingLong(Event::eventTime)
            .thenComparing(Event::activityType)
            .thenComparingInt(System::identityHashCode);

    private Map<String, List<Event>> scheduledUsers = new HashMap<>();

    private Map<String, Map<String, Integer>> users = new HashMap<>();

    public boolean addActivity(String userId, String activityType, int distance) {
        if (!users.containsKey(userId)) {
            users.put(userId, new HashMap<>());
        }

        int futureDistance = 0;
        // get future activity
        List<Event> events = scheduledUsers.get(userId);
        if (events != null ){
            Predicate<Event> inRange = e -> e.activityType() == activityType; // && e.eventTime() >= System.currentTimeMillis();
            List<Event> filtered = events.stream()
                    .filter(inRange)
                    .toList();
            for (Event event : filtered){
                futureDistance += event.distance();
            }
        }

        if (distance < 0 || users.get(userId).containsKey(activityType)) {
            return false;
        }
        users.get(userId).put(activityType, distance+futureDistance);
        return true;
    }

    public boolean updateActivity(String userId, String activityType, int distance) {
        if (users.containsKey(userId) && users.get(userId).containsKey(activityType)) {
            users.get(userId).merge(activityType, distance, Integer::sum);
            return true;
        }
        return false;
    }

    public Integer getActivity(String userId, String activityType) {
        return users.getOrDefault(userId, Collections.emptyMap()).get(activityType);
    }

    public Map<String, Integer> activitySummary(String userId) {
        return users.get(userId);
    }

    public boolean scheduleEvent(long timestamp, String userId, String activityType, int distance, long eventTime) {
        if (timestamp >= eventTime) {
            return false;
        }

        Event event = new Event(activityType, distance, eventTime);
        if (!scheduledUsers.containsKey(userId)) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            scheduledUsers.put(userId, events);
            return true;
        }
        List<Event> events = scheduledUsers.get(userId);
        events.add(event);
        scheduledUsers.put(userId, events);
        //addActivity(userId, activityType, distance);
        return true;
    }

    public List<Map<String, Object>> getAgenda(String userId, long fromTime, long toTime) {
        if (!scheduledUsers.containsKey(userId)) {
            return new ArrayList<>();
        }
        List<Event> events = scheduledUsers.get(userId);
        Predicate<Event> inRange = e -> e.eventTime() >= fromTime && e.eventTime() <= toTime;
        List<Event> filterSorted = events.stream()
                .filter(inRange)
                .sorted(Comparator.comparingLong(e -> e.eventTime()))
                .toList();

        List<Map<String, Object>> response = new ArrayList<>();
        for (Event event :filterSorted){
            Map<String, Object> resMap = Map.of(
                    "activityType", event.activityType(),
                    "distance", event.distance(),
                    "eventTime", event.eventTime()
            );
            response.add(resMap);
        }
        return response;
    }

    public static void main(String[] args) {

        FitnessTracker solution = new FitnessTracker();
        solution.addActivity("user1", "running", 5);
        solution.addActivity("user1", "cycling", 10);
        solution.updateActivity("user1", "running", 3);
        System.out.println(solution.getActivity("user1", "running")); // Output: 8
        System.out.println(solution.activitySummary("user1")); // Output: {running=8, cycling=10}

        long currentTime = System.currentTimeMillis();
        solution.scheduleEvent(currentTime, "user1", "running", 2, currentTime + 10000);
        solution.scheduleEvent(currentTime, "user1", "cycling", 5, currentTime + 20000);
        List<Map<String, Object>> agenda = solution.getAgenda("user1", currentTime, currentTime + 30000);
        System.out.println(agenda); // Output: [{activityType=running, distance=2, eventTime=<timestamp>}, {activityType=cycling, distance=5, eventTime=<timestamp>}]
    }
}