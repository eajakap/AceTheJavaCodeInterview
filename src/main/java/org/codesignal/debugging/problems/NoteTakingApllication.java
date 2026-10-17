package org.codesignal.debugging.problems;

import java.util.*;

public class NoteTakingApllication {
    private Map<String, Note> notes; // current
    private Map<String, List<String>> tags;
    private Map<String, Map<String, String>> shares;
    private Map<String, List<Note>> versions;
    private int versionNumber = 1;

    public NoteTakingApllication() {
        notes = new HashMap<>();
        tags = new HashMap<>();
        shares = new HashMap<>();
        versions = new HashMap<>();
    }

    public String addNote(String title, String content) {
        // TODO: implement solution
        String noteId = UUID.randomUUID().toString();
        Note note = new Note(noteId, title , content, new ArrayList<>());
        note.setVersionId(String.valueOf(versionNumber++));
        notes.put(noteId, note);

        List<Note> versionList = new ArrayList();
        versionList.add(note);
        versions.put(noteId, versionList);

        return noteId;
    }

    public Map<String, String> getNote(String noteId) {
        // TODO: implement solution
        Note note = notes.get(noteId);
        if (note == null) {
            return null;
        }
        return  Map.of("title", note.title, "content", note.content);
    }

    public boolean addTag(String noteId, String tag) {
        // TODO: implement solution
        Note note = notes.get(noteId);
        if (note == null) {
            return false;
        }

        List<String> note_tags = note.tags;
        note_tags.add(tag);
        tags.computeIfAbsent(tag, t-> new ArrayList<>()).add(noteId);
        return true;
    }

    public List<Map<String, String>> getNotesByTag(String tag) {
        // TODO: implement solution
        List<Map<String, String>> result = new ArrayList<>();
        if (!tags.containsKey(tag))
            return result;

        List<String> noteIds = tags.get(tag);
        for (String noteId: noteIds) {
            Note note = notes.get(noteId);
            result.add( Map.of("title", note.title, "content", note.content));
        }
        return result;
    }

    public boolean shareNote(String noteId, String userId, String accessLevel) {
        // TODO: implement solution
        Note note = notes.get(noteId);
        if (note == null) {
            return false;
        }
        Map<String, String> value = Map.of("noteId", noteId, "title", note.title, "content", note.content, "accessLevel", accessLevel);
        shares.put(userId, value);
        return true;
    }

    public List<Map<String, String>> getSharedNotes(String userId) {
        // TODO: implement solution
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> sharedNote = shares.get(userId);
        if (sharedNote != null) {
            result.add(sharedNote);
        }
        return result;
    }

    public boolean addVersion(String noteId, String title, String content, List<String> newTags) {
        // TODO: implement solution
        if ( !notes.containsKey(noteId))
            return false;

        Note curNote = notes.get(noteId);
        if ( curNote.title.equals(title) && curNote.content.equals(content))
            return false;

        List<Note> versionNotes = versions.get(noteId);

        String newVersionId = String.valueOf(versionNumber++);
        Note newNote = new Note(noteId, title, content, new ArrayList<>(newTags));
        newNote.setVersionId(newVersionId);
        versionNotes.add(newNote);
//        versions.put(noteId, versionNotes);
        notes.put(noteId, newNote);

        // fix the tags
        for (String ctag : curNote.tags){
            tags.remove(ctag);
        }

        for (String ntag : newTags){
            addTag(noteId, ntag);
        }

        return true;
    }

    public boolean undoVersion(String noteId) {
        // TODO: implement solution
        if (!notes.containsKey(noteId))
            return false;
        List<Note> list = versions.get(noteId);

        if (list.size() ==1 )
            return false;

        Note p_note = list.get(list.size() - 2);
        Note delNote = list.get(list.size() - 1);
        list.remove(list.get(list.size() - 1));

        // fix the tags
        for (String tag : delNote.tags){
            tags.remove(tag);
        }

        for (String tag : p_note.tags){
            addTag(noteId, tag);
        }
        notes.put(noteId, p_note);

        return true;
    }

    private static class Note {
        String noteId;
        String title;
        String content;
        List<String> tags;
        String versionId;

        Note(String noteId, String title, String content, List<String> tags) {
            this.noteId = noteId;
            this.title = title;
            this.content = content;
            this.tags = tags;
        }

        void setVersionId(String versionId) {
            this.versionId = versionId;
        }
    }

    public static void main(String[] args) {
        NoteTakingApllication solution = new NoteTakingApllication();
        String noteId1 = solution.addNote("Note 1", "Content 1");
        String noteId2 = solution.addNote("Note 2", "Content 2");

        solution.addTag(noteId1, "tag1");
        solution.addTag(noteId1, "tag2");
        solution.addTag(noteId2, "tag1");

        System.out.println(solution.getNotesByTag("tag1")); // Should return both notes
        System.out.println(solution.getNotesByTag("tag2")); // Should return only Note 1

        solution.shareNote(noteId1, "user1", "read");
        System.out.println(solution.getSharedNotes("user1")); // Should return Note 1 details

        solution.addVersion(noteId1, "Note 1 Updated", "Content 1 Updated", Arrays.asList("tag3"));
        System.out.println(solution.getNote(noteId1)); // Should return updated Note 1 details

        solution.undoVersion(noteId1);
        System.out.println(solution.getNote(noteId1)); // Should return original Note 1 details
    }
}