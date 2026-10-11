package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BipartiteGraphTest {
    private final BipartiteGraph.Solution solution = new BipartiteGraph.Solution();

    @Test
    void emptyAndEdgelessGraphsAreBipartite() {
        assertTrue(solution.isBipartite(new int[][]{}));
        assertTrue(solution.isBipartite(new int[][]{{}}));
        assertTrue(solution.isBipartite(new int[][]{{}, {}, {}}));
    }

    @Test
    void evenCyclesAndStarsAreBipartite() {
        assertTrue(solution.isBipartite(new int[][]{{1}, {0}}));
        assertTrue(solution.isBipartite(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}}));
        assertTrue(solution.isBipartite(new int[][]{{1, 2, 3}, {0}, {0}, {0}}));
    }

    @Test
    void oddCyclesAreNotBipartite() {
        assertFalse(solution.isBipartite(new int[][]{{1, 2}, {0, 2}, {0, 1}}));
        assertFalse(solution.isBipartite(new int[][]{{1, 4}, {0, 2}, {1, 3}, {2, 4}, {3, 0}}));
    }

    @Test
    void checksEveryDisconnectedComponent() {
        // Component {0,1} is bipartite; component {2,3,4} is a triangle
        int[][] graph = {{1}, {0}, {3, 4}, {2, 4}, {2, 3}};

        assertFalse(solution.isBipartite(graph));
    }

    @Test
    void mainReportsExpectedResults() {
        List<String> results = ConsoleOutputCapture.lines(() -> BipartiteGraph.main(new String[0])).stream()
                .filter(line -> line.startsWith("\tResult: "))
                .map(line -> line.substring("\tResult: ".length()))
                .collect(Collectors.toList());

        assertEquals(List.of("true", "true", "false", "true", "true", "false"), results);
    }
}
