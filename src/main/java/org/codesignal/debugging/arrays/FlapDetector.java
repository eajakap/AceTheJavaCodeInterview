package org.codesignal.debugging.arrays;

import java.util.*;

public class FlapDetector {
    private static class Pair<A, B> {
        final A first;
        final B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }

    /** Last flap timestamp for ONE sensor, or null if it never flaps. Pair = (alarm, timestamp). */
    private static Long findLastFlap(List<Pair<String, Long>> events, int k, long window) {
        Collections.sort(events, Comparator.comparingLong(p -> p.second)); // ascending order by timestamp

        // Step 1: collect transition timestamps (alarm differs from previous event)
        List<Long> transitions = new ArrayList<>();
        for (int i = 1; i < events.size(); i++) {
            if (!events.get(i).first.equals(events.get(i - 1).first)) {
                transitions.add(events.get(i).second);
            }
        }

        // Step 2: two-pointer sliding window over transitions
        Long lastFlap = null;
        int left = 0;
        for (int right = 0; right < transitions.size(); right++) {
            while (transitions.get(right) - transitions.get(left) >= window) {
                left++;  // shrink
            }
            if (right - left + 1 >= k) {
                lastFlap = transitions.get(right);   // k+ transitions inside window; keep latest
            }
        }
        return lastFlap;
    }

    // events for ONE sensor, sorted by timestamp
    public static Map<Integer,Long> findFlaps(List<String> sensorAlarmsData, int k, long window) {
        Map<Integer, List<Pair<String,Long>>> sensorAlarmInfoMap = new HashMap<>();
        for (String sensorAlarmInfo : sensorAlarmsData) {
            String[] p = sensorAlarmInfo.split(",");
            int sensorId = Integer.parseInt(p[0].trim());
            long ts = Long.parseLong(p[1].trim());
            String alarm = p[2].trim();
            //System.out.println("Sensor " + sensorId + " at " + ts + " triggered alarm " + alarm);
            sensorAlarmInfoMap.computeIfAbsent(sensorId, id -> new ArrayList<>()).add(new Pair<>(alarm, ts));
        }

        // scan the sensorAlarmInfoMap to find flaps for each sensor
        Map<Integer, Long> result = new TreeMap<>();
        for (Map.Entry<Integer, List<Pair<String, Long>>> entry : sensorAlarmInfoMap.entrySet()) {
            Long flap = findLastFlap(entry.getValue(), k, window);
            System.out.println("Sensor " + entry.getKey() + " last flapped at " + flap);
            if (flap != null) result.put(entry.getKey(), flap);
        }
        return result;
    }

    public static void main(String[] args) {
        // let us assume that the alarm states are: CRITICAL, MAJOR, MINOR, INFO, OFF
        String[] stream = {
                "1,100,OFF", "1,101,INFO", "1,103,INFO", "1,105,MINOR",   "1,108,MAJOR", "1,110,MAJOR",
                "1,115,CRITICAL", "1,117,INFO", "1,118,INFO", "1,119,MINOR", "1,120,MAJOR", "1,130,MAJOR", // flap at 120
                "2,100,OFF", "2,103,INFO", "2,106,MINOR", "2,109,MAJOR", "2,110,MAJOR", "2,112,MINOR", "2,115,CRITICAL" // 115
        };
        Map<Integer, Long> flaps = findFlaps(List.of(stream), 3, 10);
        flaps.forEach((id, ts) -> System.out.println("Sensor " + id + " flapped at " + ts));
    }
}
