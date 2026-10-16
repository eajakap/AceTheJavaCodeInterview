package org.codesignal.debugging.problems;

import java.util.*;

public class EmailSystem {
    private Map<String, List<Email>> inboxes;
    private Map<String, Integer> sentCounts;
    private Map<String, List<Email>> sentEmails;
    private Set<String> loggedOutUsers;

    public EmailSystem() {
        this.inboxes = new HashMap<>();
        this.sentCounts = new HashMap<>();
        this.sentEmails = new HashMap<>();
        this.loggedOutUsers = new HashSet<>();
    }

    public boolean sendEmail(String fromUser, String toUser, String subject, String body) {

        if (loggedOutUsers.contains(fromUser) || loggedOutUsers.contains(toUser)) {
            return false;
        }
        if (toUser != null) {
            inboxes.computeIfAbsent(toUser, k -> new ArrayList<>()).add(new Email(fromUser, subject, body));
            sentCounts.put(fromUser, sentCounts.getOrDefault(fromUser, 0) + 1);
            sentEmails.computeIfAbsent(fromUser, k -> new ArrayList<>()).add(new Email(toUser, subject, body));
            return true;
        } else {
            return false;
        }
    }

    public List<Email> queryInbox(String user) {
        return inboxes.getOrDefault(user, new ArrayList<>());
    }

    public Map<String, Integer> sentEmailsCount() {
        return sentCounts;
    }

    public boolean flagEmail(String user, String subject) {
        if (inboxes.containsKey(user)) {
            for (Email email : inboxes.get(user)) {
                if (email.subject.equals(subject)) {
                    email.flagged = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean undoSend(String fromUser, String subject) {
        // search for the email in the sent emails list of the fromUser
        if (sentEmails.containsKey(fromUser)) {
            List<Email> sentList = sentEmails.get(fromUser);
            for (Email email : sentList) {
                if (email.subject.equals(subject)) {
                    // remove the email from the sent list
                    sentList.remove(email);
                    // also remove it from the recipient's inbox
                    if (inboxes.containsKey(email.fromUser)) {
                        List<Email> inboxList = inboxes.get(email.fromUser);
                        inboxList.removeIf(e -> e.subject.equals(subject) && e.fromUser.equals(fromUser));
                    }
                    // decrement the sent count
                    sentCounts.put(fromUser, sentCounts.getOrDefault(fromUser, 1) - 1);
                    if (sentCounts.get(fromUser) == 0)  {
                        sentCounts.remove(fromUser);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean logoutUser(String user) {
        // For simplicity, we can just clear the user's inbox and sent emails
        loggedOutUsers.add(user);
        if (inboxes.containsKey(user)) {
            inboxes.remove(user);
            sentEmails.remove(user);
            sentCounts.remove(user);
            return true;
        }

        return false;
    }

    public static class Email {
        String fromUser;
        String subject;
        String body;
        boolean flagged;

        Email(String fromUser, String subject, String body) {
            this.fromUser = fromUser;
            this.subject = subject;
            this.body = body;
            this.flagged = false;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Email email = (Email) o;
            return Objects.equals(fromUser, email.fromUser) &&
                    Objects.equals(subject, email.subject) &&
                    Objects.equals(body, email.body);
        }

        public int hashCode() {
            return Objects.hash(fromUser, subject, body);
        }

    }

    public static void main(String[] args) {
        EmailSystem emailSystem = new EmailSystem();

        // Test sending emails
        emailSystem.sendEmail("alice", "bob", "Hello", "Hi Bob!");
        emailSystem.sendEmail("alice", "charlie", "Meeting", "Let's meet tomorrow.");
        emailSystem.sendEmail("bob", "alice", "Re: Hello", "Hi Alice!");

        // Test querying inbox
        System.out.println("Bob's Inbox: " + emailSystem.queryInbox("bob").size()); // Should print 1
        System.out.println("Charlie's Inbox: " + emailSystem.queryInbox("charlie").size()); // Should print 1
        System.out.println("Alice's Inbox: " + emailSystem.queryInbox("alice").size()); // Should print 1

        // Test sent emails count
        System.out.println("Alice sent emails count: " + emailSystem.sentEmailsCount().get("alice")); // Should print 2
        System.out.println("Bob sent emails count: " + emailSystem.sentEmailsCount().get("bob")); // Should print 1

        // Test flagging an email
        boolean flagged = emailSystem.flagEmail("bob", "Hello");
        System.out.println("Flagged Bob's email: " + flagged); // Should print true

        // Test undo send (not implemented)
        boolean undo = emailSystem.undoSend("alice", "Meeting");
        System.out.println("Undo send for Alice's 'Meeting': " + undo); // Should print false

        // Test logout user (not implemented)
        boolean logout = emailSystem.logoutUser("alice");
        System.out.println("Logout Alice: " + logout); // Should print false
    }
}