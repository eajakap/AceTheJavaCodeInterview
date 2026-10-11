package org.educative.modules.module2.maps;

import org.educative.modules.module2.maps.PlayerSolution.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerSolutionTest {
    @Test
    void playerComparesByScoreOnly() {
        assertTrue(new Player("Brad", 75).compareTo(new Player("Amy", 87)) < 0);
        assertEquals(0, new Player("Amy", 80).compareTo(new Player("Brad", 80)));
    }

    @Test
    void playerEqualsHashCodeAndToStringUseNameAndScore() {
        Player player = new Player("Amy", 87);

        assertEquals(player, new Player("Amy", 87));
        assertEquals(player.hashCode(), new Player("Amy", 87).hashCode());
        assertNotEquals(player, new Player("Amy", 75));
        assertNotEquals(player, new Player("Brad", 87));
        assertEquals("Player{name='Amy', score=87}", player.toString());
    }

    @Test
    void treeMapTreatsPlayersWithSameScoreAsOneKey() {
        TreeMap<Player, String> tournament = new TreeMap<>();
        tournament.put(new Player("Amy", 80), "first");
        tournament.put(new Player("Brad", 80), "second");

        assertEquals(1, tournament.size());
        assertEquals("second", tournament.firstEntry().getValue());
    }

    @Test
    void mainPrintsPlayersInAscendingScoreOrder() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> PlayerSolution.main(new String[0]));

        assertEquals(List.of(
                "Player{name='Brad', score=75}",
                "Player{name='Amy', score=87}"
        ), lines);
    }
}
