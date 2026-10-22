package org.codesignal.debugging.graph;

import java.util.*;

/**
 * You have been given a list of pairs, each representing a connection between two people.
 * Every person is represented by a unique positive integer that falls within the range of $1$ to $1001$, inclusive.
 *
 * A pair (a, b) indicates that person a connects with person b. This connection is not direction-sensitive,
 * meaning that (a, b) and (b, a) represent the same connection. The list might include both forms.
 *
 * Your task is to identify the person who has the potential to connect the most people within two degrees of connection.
 * In other words, you aim to find the person with the most extensive network of friends of friends.
 *
 */
public class FindInfluencerGraph {

    private static enum SolutionApproach {
        HASHMAP, GRAPH
    }

    private static final int MAX_ID = 1001;

    private static class Node {
        int id;
        List<Node> neighbors;

        public Node(int id) {
            this.id = id;                       // The person's ID
            this.neighbors = new ArrayList<>();  // Store connected neighboring Nodes
        }

        // Add a neighboring Node to this Node's neighbor list
        public void addNeighbor(Node node) {
            neighbors.add(node);
        }
    }

    private static class Graph {
        // Keyed by person ID; TreeMap iterates in ascending ID order
        TreeMap<Integer, Node> nodes;

        public Graph() {
            this.nodes = new TreeMap<>();  // Store all Nodes in the graph
        }

        // Add a person to the graph if not already present
        public Node addNode(int id) {
            return nodes.computeIfAbsent(id, Node::new);
        }

        // Add an undirected edge between two people, creating them if needed
        public void addEdge(int id1, int id2) {
            Node node1 = addNode(id1);
            Node node2 = addNode(id2);
            node1.addNeighbor(node2);   // Connect node1 to node2
            node2.addNeighbor(node1);   // Connect node2 to node1
        }
    }

    /**
     * Steps:
     * 1. Build an undirected Graph: one Node per person, with each connection added both ways.
     * 2. Visit people in ascending ID order (the Graph keeps nodes in a TreeMap) so a strict '>' keeps the
     *    lowest ID on ties.
     * 3. For each person, count the distinct people among their friends and their friends' friends,
     *    excluding the person. A stamp array marks visited people without clearing it between persons.
     * 4. Return the person with the largest count, or 0 if there are no connections.
     * 5. Time Complexity: O(E + sum of deg(u)^2), at most O(V * E)
     * 6. Space Complexity: O(V + E)
     *
     * @param connections the pairs of connected people
     * @return the person with the largest network within two degrees, preferring the lower ID on ties
     */
    public static int findInfluencerUsingGraph(List<int[]> connections) {
        Graph graph = new Graph();
        for (int[] pair : connections) {
            graph.addEdge(pair[0], pair[1]);
        }

        int influencer = 0;
        int maxReach = -1;
        // Person IDs are positive, so using the ID as the stamp never collides with the initial 0
        int[] seen = new int[MAX_ID + 1];
        for (Node person : graph.nodes.values()) {
            int reach = 0;
            seen[person.id] = person.id;
            for (Node friend : person.neighbors) {
                if (seen[friend.id] != person.id) {
                    seen[friend.id] = person.id;
                    reach++;
                }
                for (Node friendOfFriend : friend.neighbors) {
                    if (seen[friendOfFriend.id] != person.id) {
                        seen[friendOfFriend.id] = person.id;
                        reach++;
                    }
                }
            }
            if (reach > maxReach) {
                maxReach = reach;
                influencer = person.id;
            }
        }
        return influencer;
    }

    public static int findInfluencerUsingMap(List<int[]> connections) {
        Map<Integer, Set<Integer>> friends = new TreeMap<>();
        for (int[] pair : connections) {
            friends.computeIfAbsent(pair[0], k -> new HashSet<>()).add(pair[1]);
            friends.computeIfAbsent(pair[1], k -> new HashSet<>()).add(pair[0]);
        }

        int influencer = 0;
        int maxReach = -1;
        // TreeMap iterates in ascending order, so a strict '>' keeps the lowest person on ties
        for (Map.Entry<Integer, Set<Integer>> person : friends.entrySet()) {
            Set<Integer> reach = new HashSet<>(person.getValue());
            for (int friend : person.getValue()) {
                reach.addAll(friends.get(friend));
            }
            reach.remove(person.getKey());
            if (reach.size() > maxReach) {
                maxReach = reach.size();
                influencer = person.getKey();
            }
        }
        return influencer;
    }

    public static int findInfluencer(List<int[]> connections, SolutionApproach approach) {
        switch (approach) {
            case HASHMAP:
                return findInfluencerUsingMap(connections);
            case GRAPH:
                return findInfluencerUsingGraph(connections);
            default:
                throw new IllegalArgumentException("Unknown approach: " + approach);
        }
    }

    /**
     * Person 100 connects with 200, 300, and 400. The friends of friends are 600 (from 200), 500 (from 400).
     * Therefore, person 100 is connected to five different people within two degrees.
     * Person 200 connects with 100, 300, and 600. The friends of friends are 400 (from 100), and 500 (from 600).
     * Therefore, person 200 is connected to five people within two degrees.
     * Person 300 connects with 100 and 200. The friends of friends are 400 (from 100), and 600 (from 200).
     * Therefore, person 300 is connected to four people within two degrees, and so on.
     * In this scenario, you should return 100, 200, 400, or 600, as they connect with the highest number of
     * people within two degrees.
     * However, the order of preference is for the lower integer. Therefore, you should return 100.
     * @param args
     */
    public static void main(String[] args) {
        List<int[]> connections = Arrays.asList(new int[]{100, 200}, new int[]{200, 300}, new int[]{100, 300},
                new int[]{100, 400}, new int[]{400, 500}, new int[]{500, 600}, new int[]{200, 600});
        System.out.println("HASHMAP approach: O(E + sum of deg^2), allocates a HashSet per person");
        long start = System.nanoTime();
        int hashMapResult = findInfluencer(connections, SolutionApproach.HASHMAP);
        System.out.println(hashMapResult + " (took " + (System.nanoTime() - start) / 1000 + " us)");

        System.out.println("GRAPH approach: O(E + sum of deg^2), reuses one stamp array");
        start = System.nanoTime();
        int graphResult = findInfluencer(connections, SolutionApproach.GRAPH);
        System.out.println(graphResult + " (took " + (System.nanoTime() - start) / 1000 + " us)");
    }
}
