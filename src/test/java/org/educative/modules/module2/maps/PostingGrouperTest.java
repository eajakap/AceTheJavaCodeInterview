package org.educative.modules.module2.maps;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostingGrouperTest {
    @Test
    void groupsMatchingPostingsInOrderOfFirstAppearance() {
        String[][] groups = PostingGrouper.groupMatchingPostings(
                new String[]{"p1|500|ACME", "p2|500|ACME", "p3|700|ACME", "p4|500|BETA", "p5|500|ACME"});

        assertArrayEquals(new String[][]{{"p1", "p2", "p5"}, {"p3"}, {"p4"}}, groups);
    }

    @Test
    void emptyInputReturnsNoGroups() {
        assertEquals(0, PostingGrouper.groupMatchingPostings(new String[]{}).length);
    }

    @Test
    void singlePostingFormsSingletonGroup() {
        assertArrayEquals(new String[][]{{"p1"}},
                PostingGrouper.groupMatchingPostings(new String[]{"p1|100|ACME"}));
    }

    @Test
    void amountAndCounterpartyMustBothMatch() {
        String[][] groups = PostingGrouper.groupMatchingPostings(
                new String[]{"a|100|X", "b|100|Y", "c|200|X", "d|100|X"});

        assertArrayEquals(new String[][]{{"a", "d"}, {"b"}, {"c"}}, groups);
    }

    @Test
    void negativeAmountsAreDistinctFromPositiveOnes() {
        String[][] groups = PostingGrouper.groupMatchingPostings(
                new String[]{"a|-500|ACME", "b|500|ACME", "c|-500|ACME"});

        assertArrayEquals(new String[][]{{"a", "c"}, {"b"}}, groups);
    }

    @Test
    void counterpartiesWithDigitsAreKeptApart() {
        String[][] groups = PostingGrouper.groupMatchingPostings(
                new String[]{"a|10|ACME1", "b|10|ACME2", "c|10|ACME1"});

        assertArrayEquals(new String[][]{{"a", "c"}, {"b"}}, groups);
    }

    @Test
    void surroundingWhitespaceInFieldsIsIgnored() {
        String[][] groups = PostingGrouper.groupMatchingPostings(
                new String[]{" a | 100 | ACME ", "b|100|ACME"});

        assertArrayEquals(new String[][]{{"a", "b"}}, groups);
    }

    @Test
    void handlesLargeInput() {
        String[] postings = new String[200000];
        for (int i = 0; i < postings.length; i++) {
            postings[i] = "p" + i + "|" + (i % 2) + "|ACME";
        }

        String[][] groups = PostingGrouper.groupMatchingPostings(postings);

        assertEquals(2, groups.length);
        assertEquals(100000, groups[0].length);
        assertEquals("p0", groups[0][0]);
        assertEquals("p1", groups[1][0]);
    }

    @Test
    void mainPrintsEachGroup() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> PostingGrouper.main(new String[0]));

        assertEquals(List.of("{p1, p2, p5}", "{p3}", "{p4}"), lines);
    }
}
