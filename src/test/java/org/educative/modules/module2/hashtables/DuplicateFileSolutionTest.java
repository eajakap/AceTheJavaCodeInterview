package org.educative.modules.module2.hashtables;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateFileSolutionTest {
    private final DuplicateFileSolution solution = new DuplicateFileSolution();

    // Group and file order inside the result is not significant
    private static Set<Set<String>> asSets(List<List<String>> groups) {
        return groups.stream().map(HashSet::new).collect(Collectors.toSet());
    }

    @Test
    void groupsFilesWithIdenticalContentAcrossDirectories() {
        List<List<String>> result = solution.findDuplicate(
                new String[]{"dir1 a.txt(hello)", "dir2 b.txt(hello)", "dir3 c.txt(hello)"});

        assertEquals(Set.of(Set.of("dir1/a.txt", "dir2/b.txt", "dir3/c.txt")), asSets(result));
    }

    @Test
    void returnsMultipleDuplicateGroupsAndSkipsUniqueFiles() {
        List<List<String>> result = solution.findDuplicate(new String[]{
                "home/user doc.txt(xyz) notes.txt(abc)",
                "home/backup doc2.txt(xyz)",
                "tmp notes2.txt(abc)",
                "var log.txt(unique)"});

        assertEquals(Set.of(
                Set.of("home/user/doc.txt", "home/backup/doc2.txt"),
                Set.of("home/user/notes.txt", "tmp/notes2.txt")), asSets(result));
    }

    @Test
    void groupsFilesWithinSingleDirectory() {
        List<List<String>> result = solution.findDuplicate(
                new String[]{"folder f1.txt(same) f2.txt(same) f3.txt(diff)"});

        assertEquals(Set.of(Set.of("folder/f1.txt", "folder/f2.txt")), asSets(result));
    }

    @Test
    void returnsEmptyWhenNoDuplicatesExist() {
        assertTrue(solution.findDuplicate(
                new String[]{"root/p q.txt(alpha)", "root/r s.txt(beta)", "root/t u.txt(gamma)"}).isEmpty());
        assertTrue(solution.findDuplicate(new String[]{}).isEmpty());
    }

    @Test
    void mainPrintsResultsForAllCases() {
        String output = ConsoleOutputCapture.capture(() -> DuplicateFileSolution.main(new String[0]));

        assertTrue(output.contains("\tResult: []"));
        assertEquals(5, output.split("\tResult: ", -1).length - 1);
    }
}
