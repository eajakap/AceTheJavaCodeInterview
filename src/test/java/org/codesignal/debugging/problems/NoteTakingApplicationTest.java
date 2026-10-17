package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoteTakingApplicationTest {
    @Test
    void testAddAndGetNote() {
        NoteTakingApllication solution = new NoteTakingApllication();

        String noteId = solution.addNote("Title", "Content");

        assertNotNull(noteId);
        assertEquals(Map.of("title", "Title", "content", "Content"), solution.getNote(noteId));
        assertNotEquals(noteId, solution.addNote("Another title", "Another content"));
    }

    @Test
    void testGetNonExistentNote() {
        assertNull(new NoteTakingApllication().getNote("missing"));
    }

    @Test
    void testAddTagAndGetByTag() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String firstNote = solution.addNote("First", "First content");
        String secondNote = solution.addNote("Second", "Second content");

        assertTrue(solution.addTag(firstNote, "work"));
        assertTrue(solution.addTag(secondNote, "work"));
        assertEquals(List.of(
                Map.of("title", "First", "content", "First content"),
                Map.of("title", "Second", "content", "Second content")
        ), solution.getNotesByTag("work"));
    }

    @Test
    void testAddTagToMissingNote() {
        assertFalse(new NoteTakingApllication().addTag("missing", "work"));
    }

    @Test
    void testGetNotesByUnknownTag() {
        assertTrue(new NoteTakingApllication().getNotesByTag("unknown").isEmpty());
    }

    @Test
    void testShareAndGetSharedNotes() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId = solution.addNote("Shared", "Shared content");

        assertTrue(solution.shareNote(noteId, "user-1", "read"));
        assertEquals(List.of(Map.of(
                "noteId", noteId,
                "title", "Shared",
                "content", "Shared content",
                "accessLevel", "read"
        )), solution.getSharedNotes("user-1"));
    }

    @Test
    void testShareMissingNote() {
        assertFalse(new NoteTakingApllication().shareNote("missing", "user-1", "read"));
    }

    @Test
    void testGetSharedNotesForUserWithoutShares() {
        assertTrue(new NoteTakingApllication().getSharedNotes("user-1").isEmpty());
    }

    @Test
    void testAddVersionWithTags() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId = solution.addNote("Original", "Original content");
        solution.addTag(noteId, "original-tag");

        assertTrue(solution.addVersion(noteId, "Updated", "Updated content", List.of("updated-tag")));

        assertEquals(Map.of("title", "Updated", "content", "Updated content"), solution.getNote(noteId));
        assertTrue(solution.getNotesByTag("original-tag").isEmpty());
        assertEquals(List.of(Map.of("title", "Updated", "content", "Updated content")),
                solution.getNotesByTag("updated-tag"));
    }

    @Test
    void testAddAndUndoVersionWithTags() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId = solution.addNote("Original", "Original content");
        solution.addTag(noteId, "original-tag");

        assertTrue(solution.addVersion(noteId, "Updated", "Updated content", List.of("updated-tag")));
        assertTrue(solution.undoVersion(noteId));

        assertEquals(Map.of("title", "Original", "content", "Original content"), solution.getNote(noteId));
        assertTrue(solution.getNotesByTag("updated-tag").isEmpty());
        assertEquals(List.of(Map.of("title", "Original", "content", "Original content")),
                solution.getNotesByTag("original-tag"));
    }

    @Test
    void testMultipleVersionsAndUndoToInitialWithTags() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId = solution.addNote("Initial", "Initial content");
        solution.addTag(noteId, "initial-tag");

        assertTrue(solution.addVersion(noteId, "Second", "Second content", List.of("second-tag")));
        assertTrue(solution.addVersion(noteId, "Third", "Third content", List.of("third-tag")));
        assertEquals(Map.of("title", "Third", "content", "Third content"), solution.getNote(noteId));

        assertTrue(solution.undoVersion(noteId));
        assertEquals(Map.of("title", "Second", "content", "Second content"), solution.getNote(noteId));
        assertTrue(solution.undoVersion(noteId));
        assertEquals(Map.of("title", "Initial", "content", "Initial content"), solution.getNote(noteId));
        assertFalse(solution.undoVersion(noteId));

        assertEquals(List.of(Map.of("title", "Initial", "content", "Initial content")),
                solution.getNotesByTag("initial-tag"));
        assertTrue(solution.getNotesByTag("second-tag").isEmpty());
        assertTrue(solution.getNotesByTag("third-tag").isEmpty());
    }

    @Test
    void testAddVersionToMissingNote() {
        assertFalse(new NoteTakingApllication().addVersion("missing", "Title", "Content", List.of()));
    }

    @Test
    void testAddUnchangedVersionIsRejected() {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId = solution.addNote("Title", "Content");

        assertFalse(solution.addVersion(noteId, "Title", "Content", List.of("tag")));
        assertFalse(solution.undoVersion(noteId));
    }

    @Test
    void testUndoVersionForMissingNote() {
        assertFalse(new NoteTakingApllication().undoVersion("missing"));
    }
}
