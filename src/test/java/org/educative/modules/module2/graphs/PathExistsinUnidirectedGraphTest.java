package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathExistsinUnidirectedGraphTest {
    @Test
    void mainReportsWhetherPathsExist() {
        List<String> results = ConsoleOutputCapture.lines(() -> PathExistsinUnidirectedGraph.main(new String[0]))
                .stream()
                .filter(line -> line.startsWith("\tResult: "))
                .map(line -> line.substring("\tResult: ".length()))
                .collect(Collectors.toList());

        // chain, disconnected, longer chain, source == destination, path via 1-3
        assertEquals(List.of("true", "false", "true", "true", "true"), results);
    }

    @Test
    void mainPrintsInputDetailsForEachCase() {
        String output = ConsoleOutputCapture.capture(() -> PathExistsinUnidirectedGraph.main(new String[0]));

        assertEquals(true, output.contains("\tEdges: [[0, 1], [0, 2], [3, 4]]"));
        assertEquals(true, output.contains("\tSource: 0, Destination: 4"));
    }
}
