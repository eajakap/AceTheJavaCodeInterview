package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Dota2SenateTest {
    private final Dota2Senate solution = new Dota2Senate();

    @Test
    void radiantWins() {
        assertEquals("Radiant", solution.predictPartyVictory("RRDD"));
        assertEquals("Radiant", solution.predictPartyVictory("RRDDD"));
        assertEquals("Radiant", solution.predictPartyVictory("R"));
    }

    @Test
    void direWins() {
        assertEquals("Dire", solution.predictPartyVictory("DDRR"));
        assertEquals("Dire", solution.predictPartyVictory("DRRD"));
        assertEquals("Dire", solution.predictPartyVictory("DRDRDRDRD"));
        assertEquals("Dire", solution.predictPartyVictory("D"));
    }

    @Test
    void singlePartySenateWinsImmediately() {
        assertEquals("Radiant", solution.predictPartyVictory("RRR"));
        assertEquals("Dire", solution.predictPartyVictory("DDD"));
    }

    @Test
    void largeSenateOfAlternatingPartiesFavoursFirstSenator() {
        assertEquals("Radiant", solution.predictPartyVictory("RD".repeat(500)));
        assertEquals("Dire", solution.predictPartyVictory("DR".repeat(500)));
    }

    @Test
    void majorityWins() {
        assertEquals("Radiant", solution.predictPartyVictory("DRRR"));
        assertEquals("Dire", solution.predictPartyVictory("RDDD"));
    }

    @Test
    void firstSenatorToActWinsTwoPersonSenate() {
        assertEquals("Radiant", solution.predictPartyVictory("RD"));
        assertEquals("Dire", solution.predictPartyVictory("DR"));
    }
}
