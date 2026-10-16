package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailSystemTest {
    @Test
    void testQueryInboxOfNonExistentUser() {
        EmailSystem emailSystem = new EmailSystem();

        assertTrue(emailSystem.queryInbox("unknown").isEmpty());
    }

    @Test
    void testSendEmailAndQueryInbox() {
        EmailSystem emailSystem = new EmailSystem();

        assertTrue(emailSystem.sendEmail("alice", "bob", "Hello", "Hi Bob!"));

        List<EmailSystem.Email> inbox = emailSystem.queryInbox("bob");
        assertEquals(1, inbox.size());
        assertEquals("alice", inbox.get(0).fromUser);
        assertEquals("Hello", inbox.get(0).subject);
        assertEquals("Hi Bob!", inbox.get(0).body);
        assertFalse(inbox.get(0).flagged);
    }

    @Test
    void testFlagNonExistentEmail() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("alice", "bob", "Hello", "Hi Bob!");

        assertFalse(emailSystem.flagEmail("bob", "Missing"));
        assertTrue(emailSystem.flagEmail("bob", "Hello"));
        assertTrue(emailSystem.queryInbox("bob").get(0).flagged);
    }

    @Test
    void testUndoNonExistentEmail() {
        EmailSystem emailSystem = new EmailSystem();

        assertFalse(emailSystem.undoSend("alice", "Missing"));
        assertTrue(emailSystem.sentEmailsCount().isEmpty());
    }

    @Test
    void testUndoSendUpdatesSentCountsCorrectly() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("alice", "bob", "Undo", "Remove this");
        emailSystem.sendEmail("alice", "charlie", "Keep", "Keep this");

        assertTrue(emailSystem.undoSend("alice", "Undo"));

        assertTrue(emailSystem.queryInbox("bob").isEmpty());
        assertEquals(1, emailSystem.queryInbox("charlie").size());
        assertEquals(Map.of("alice", 1), emailSystem.sentEmailsCount());
    }

    @Test
    void testMultipleSendAndThenUndoAffectsCountCorrectly() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("alice", "bob", "First", "First message");
        emailSystem.sendEmail("alice", "bob", "Second", "Second message");

        assertEquals(2, emailSystem.sentEmailsCount().get("alice"));
        assertTrue(emailSystem.undoSend("alice", "First"));
        assertEquals(1, emailSystem.sentEmailsCount().get("alice"));
        assertTrue(emailSystem.undoSend("alice", "Second"));

        assertTrue(emailSystem.queryInbox("bob").isEmpty());
        assertFalse(emailSystem.sentEmailsCount().containsKey("alice"));
    }

    @Test
    void testAttemptSendAfterLogout() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("bob", "alice", "Welcome", "Welcome to the inbox");

        assertTrue(emailSystem.logoutUser("alice"));
        assertFalse(emailSystem.sendEmail("alice", "bob", "After logout", "Should fail"));
        assertFalse(emailSystem.sendEmail("bob", "alice", "After logout", "Should fail"));
        assertTrue(emailSystem.queryInbox("alice").isEmpty());
    }

    @Test
    void testUndoSendEmail() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("alice", "bob", "Hello", "Hi Bob!");

        assertTrue(emailSystem.undoSend("alice", "Hello"));

        assertTrue(emailSystem.queryInbox("bob").isEmpty());
        assertFalse(emailSystem.sentEmailsCount().containsKey("alice"));
        assertFalse(emailSystem.undoSend("alice", "Hello"));
    }

    @Test
    void testSentEmailsCount() {
        EmailSystem emailSystem = new EmailSystem();
        emailSystem.sendEmail("alice", "bob", "One", "First");
        emailSystem.sendEmail("alice", "charlie", "Two", "Second");
        emailSystem.sendEmail("bob", "alice", "Reply", "Reply");

        assertEquals(Map.of("alice", 2, "bob", 1), emailSystem.sentEmailsCount());
    }
}
