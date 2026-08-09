package org.educative.modules.module2.queues.puzzles;

import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserHistorySolution {
    private Deque<String> history;
    private Deque<String> future;

    public BrowserHistorySolution() {
        // TODO: Initialize two deques, one for history and one for future navigations
        history = new ArrayDeque<>();
        future = new ArrayDeque<>();
    }

    public void visit(String url) {
        // TODO: Add the visited URL to the history and clear the future
        history.addLast(url);
        future.clear();
        }

    public String back(int steps) {
        // TODO: Move the specified number of steps back in the history, if possible, and update the future accordingly
        // Return the current page after moving back
        while (steps > 0 && history.size() > 1) {
            future.addFirst(history.removeLast());
            steps--;
        }
        return history.peekLast();
    }

    public String forward(int steps) {
        // TODO: Move the specified number of steps forward in the history, if possible, and update the history accordingly
        // Return the current page after moving forward
        while (steps > 0 && !future.isEmpty()) {
            history.addLast(future.removeFirst());
            steps--;
        }
        return history.peekLast();
    }

    public static void main(String[] args) {
        BrowserHistorySolution browser = new BrowserHistorySolution();
        browser.visit("example.com");
        browser.visit("wikipedia.org");
        browser.visit("twitter.com");

        System.out.println("Went back, current page: " + browser.back(2));
        System.out.println("Went forward, current page: " + browser.forward(1));
    }

}
