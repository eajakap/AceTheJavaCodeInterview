package org.educative.modules.module2.maps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * P1 D2 - Group matching postings       target 15 minutes
 *
 * Each posting is "id|amountCents|counterparty". Two postings MATCH when they have the same
 * amount AND the same counterparty.
 *
 * Return the groups of matching postings: one array of ids per group, groups ordered by where
 * their first member appears, ids within a group in input order. Singleton groups are included.
 *
 *   {"p1|500|ACME", "p2|500|ACME", "p3|700|ACME", "p4|500|BETA", "p5|500|ACME"}
 *       ->  {{"p1","p2","p5"}, {"p3"}, {"p4"}}
 *
 * Amounts may be negative. Counterparties may contain digits. The list may be empty.
 * 0 <= postings.length <= 200000.
 * The PostingGrouper class is a placeholder for functionality related to grouping postings.
 * This class can be expanded with methods and properties as needed to implement the desired behavior.
 */
public class PostingGrouper {
    public static String[][] groupMatchingPostings(String[] postings) {
        // Implementation goes here
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (String posting : postings) {
            String[] parts = posting.split("\\|");
            String id = parts[0].trim();
            String amount = parts[1].trim();
            String counterparty = parts[2].trim();
            String key = amount + "|" + counterparty;
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(id);
        }

        List<String[]> groupedPostings = new ArrayList<>();
        for (List<String> ids : result.values()) {
            groupedPostings.add(ids.toArray(new String[0]));
        }
        return groupedPostings.toArray(new String[0][]);
    }

    public static void main(String[] args) {
        String[] postings = {"p1|500|ACME", "p2|500|ACME", "p3|700|ACME", "p4|500|BETA", "p5|500|ACME"};
        String[][] groupedPostings = groupMatchingPostings(postings);
        for (String[] group : groupedPostings) {
            System.out.print("{");
            for (int i = 0; i < group.length; i++) {
                System.out.print(group[i]);
                if (i < group.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
    }
}
