package org.educative.modules.module2.queues.puzzles;

import java.util.*;

public class Dota2Senate {
    private static class Solution {
        public String predictPartyVictory(String senate) {
            int n = senate.length();
            Queue<Integer> radiantQueue = new LinkedList<>();
            Queue<Integer> direQueue = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                if (senate.charAt(i) == 'R') {
                    radiantQueue.add(i);
                } else {
                    direQueue.add(i);
                }
            }

            while (!radiantQueue.isEmpty() && !direQueue.isEmpty()) {
                int rIndex = radiantQueue.poll();
                int dIndex = direQueue.poll();

                if (rIndex < dIndex) {
                    // Radiant senator bans Dire senator; Radiant re-enters next round
                    radiantQueue.add(rIndex + n);
                } else {
                    // Dire senator bans Radiant senator; Dire re-enters next round
                    direQueue.add(dIndex + n);
                }
            }

            // The party with remaining senators wins
            return radiantQueue.isEmpty() ? "Dire" : "Radiant";
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            String[] testCases = {
                    "RRDD",
                    "DDRR",
                    "DRRD",
                    "RRDDD",
                    "DRDRDRDRD"
            };

            int y = 1;
            for (String senate : testCases) {
                String result = sol.predictPartyVictory(senate);
                System.out.println(y++ + ".\tInput: \"" + senate + "\"");
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}