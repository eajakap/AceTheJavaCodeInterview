package org.educative.modules.module2.queues.puzzles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrowserHistorySolutionTest {
    private BrowserHistorySolution browser;

    @BeforeEach
    void setUp() {
        browser = new BrowserHistorySolution();
        browser.visit("example.com");
        browser.visit("wikipedia.org");
        browser.visit("twitter.com");
    }

    @Test
    void backAndForwardMoveThroughHistory() {
        assertEquals("example.com", browser.back(2));
        assertEquals("wikipedia.org", browser.forward(1));
    }

    @Test
    void backStopsAtFirstPage() {
        assertEquals("example.com", browser.back(10));
    }

    @Test
    void forwardStopsAtLatestPage() {
        browser.back(2);
        assertEquals("twitter.com", browser.forward(10));
    }

    @Test
    void zeroStepsStaysOnCurrentPage() {
        assertEquals("twitter.com", browser.back(0));
        assertEquals("twitter.com", browser.forward(0));
    }

    @Test
    void visitClearsForwardHistory() {
        browser.back(1);
        browser.visit("github.com");
        assertEquals("github.com", browser.forward(1));
        assertEquals("wikipedia.org", browser.back(1));
    }

    @Test
    void revisitingSamePageIsRecordedAgain() {
        browser.visit("twitter.com");

        assertEquals("twitter.com", browser.back(1));
        assertEquals("twitter.com", browser.forward(1));
    }

    @Test
    void backThenForwardRestoresCurrentPage() {
        browser.back(2);

        assertEquals("twitter.com", browser.forward(2));
    }

    @Test
    void emptyBrowserReturnsNull() {
        BrowserHistorySolution empty = new BrowserHistorySolution();
        assertNull(empty.back(1));
        assertNull(empty.forward(1));
    }
}
