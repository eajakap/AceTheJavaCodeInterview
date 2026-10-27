package org.educative.modules.module2.trees;

import java.util.*;

public class DependencyFinder {
    private final Map<String, Set<String>> deps = new LinkedHashMap<>();

    public DependencyFinder(Map<String, ? extends Collection<String>> direct) {
        direct.forEach((m, ds) -> {
            deps.computeIfAbsent(m, k -> new LinkedHashSet<>()).addAll(ds);
            ds.forEach(d -> deps.computeIfAbsent(d, k -> new LinkedHashSet<>())); // leaves too
        });
    }

    /** Transitive dependencies of module; iterative, cycle-safe. */
    public Set<String> transitiveDependencies(String module) {
        if (!deps.containsKey(module)) throw new IllegalArgumentException("Unknown module: " + module);
        Set<String> seen = new TreeSet<>();
        Deque<String> stack = new ArrayDeque<>(deps.get(module));
        while (!stack.isEmpty()) {
            String m = stack.pop();
            if (seen.add(m)) stack.addAll(deps.get(m));
        }
        return seen;
    }

    /** Everything that depends on module (reverse graph). */
    public Set<String> dependents(String module) {
        Map<String, Set<String>> rev = new HashMap<>();
        deps.forEach((m, ds) -> ds.forEach(d -> rev.computeIfAbsent(d, k -> new HashSet<>()).add(m)));
        Set<String> seen = new TreeSet<>();
        Deque<String> stack = new ArrayDeque<>(rev.getOrDefault(module, Set.of()));
        while (!stack.isEmpty()) {
            String m = stack.pop();
            if (seen.add(m)) stack.addAll(rev.getOrDefault(m, Set.of()));
        }
        return seen;
    }

    /** Dependencies first (Kahn's algorithm); throws on a cycle. */
    public List<String> buildOrder() {
        Map<String, Integer> remaining = new HashMap<>();
        Map<String, List<String>> users = new HashMap<>();
        deps.forEach((m, ds) -> {
            remaining.put(m, ds.size());
            ds.forEach(d -> users.computeIfAbsent(d, k -> new ArrayList<>()).add(m));
        });
        PriorityQueue<String> ready = new PriorityQueue<>();
        remaining.forEach((m, n) -> { if (n == 0) ready.add(m); });
        List<String> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            String m = ready.poll();
            order.add(m);
            for (String u : users.getOrDefault(m, List.of()))
                if (remaining.merge(u, -1, Integer::sum) == 0) ready.add(u);
        }
        if (order.size() != deps.size()) throw new IllegalStateException("Cycle: " + findCycle());
        return order;
    }

    /** One cycle as [A, B, C, A], or empty. Three-colour DFS. */
    public List<String> findCycle() {
        Map<String, Integer> color = new HashMap<>();   // 1 = on path, 2 = done
        Deque<String> path = new ArrayDeque<>();
        for (String start : deps.keySet()) {
            List<String> c = dfs(start, color, path);
            if (!c.isEmpty()) return c;
        }
        return List.of();
    }

    private List<String> dfs(String m, Map<String, Integer> color, Deque<String> path) {
        Integer c = color.get(m);
        if (c != null && c == 2) return List.of();
        if (c != null && c == 1) {                       // back edge
            List<String> cycle = new ArrayList<>();
            Iterator<String> it = path.descendingIterator();
            while (it.hasNext()) { String p = it.next(); if (!cycle.isEmpty() || p.equals(m)) cycle.add(p); }
            cycle.add(m);
            return cycle;
        }
        color.put(m, 1);
        path.push(m);
        for (String d : deps.get(m)) {
            List<String> cycle = dfs(d, color, path);
            if (!cycle.isEmpty()) return cycle;
        }
        path.pop();
        color.put(m, 2);
        return List.of();
    }

    /** Shortest chain from -> to (BFS), or empty. */
    public List<String> path(String from, String to) {
        Map<String, String> parent = new HashMap<>();
        Deque<String> q = new ArrayDeque<>(List.of(from));
        parent.put(from, null);
        while (!q.isEmpty()) {
            String m = q.poll();
            if (m.equals(to)) {
                LinkedList<String> p = new LinkedList<>();
                for (String x = to; x != null; x = parent.get(x)) p.addFirst(x);
                return p;
            }
            for (String d : deps.getOrDefault(m, Set.of()))
                if (!parent.containsKey(d)) { parent.put(d, m); q.add(d); }
        }
        return List.of();
    }

    public static void main(String[] args) {
        DependencyFinder finder = new DependencyFinder(Map.of(
                "A", Set.of("B", "C"),
                "B", Set.of("D"),
                "C", Set.of("D"),
                "D", Set.of()
        ));
        System.out.println(finder.transitiveDependencies("A"));
        System.out.println(finder.dependents("A"));
        System.out.println(finder.buildOrder());
    }
}