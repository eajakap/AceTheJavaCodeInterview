package org.codesignal.debugging.problems;

/*
Imagine you have a large mailbox that receives emails from various sources and you need to organize these emails. Your task involves implementing a Java method named organizeInbox. This method will accept a string of emails as input and output a list of strings. Each string contains two elements: the sender's email address and the total count of emails received from this sender, separated by a space.

Each email is represented by various metadata separated by commas, such as "Sender Email Address, Subject, Timestamp". The total string comprises these entries, separated by semicolons. Emails originate from distinct senders and can occur at any timestamp in the "HH:MM" format within a 24-hour range.

Here is the format of the string: "Sender Email Address1, Subject1, 09:00; Sender Email Address2, Subject2, 10:00; Sender Email Address1, Subject3, 12:00"

The method should return: ["Sender Email Address1 2", "Sender Email Address2 1"].

For each input entry, the sender's email is a string containing up to $20$ characters. The timestamp follows the "HH:MM" format. The total number of email entries varies from $1$ to $500$, inclusive.

Your method must:

Extract the sender's email address.
Count the number of emails received from each sender.
Output a list of strings, where each string contains the sender's email address followed by the count of emails received from them, separated by a space.
Sort the list in descending order of the counts. If two senders have sent the same number of emails, list them in ascending order based on the senders' email addresses.
The sender's email address is always followed by a comma, a space, and the start of the subject line. The subject line is always followed by a comma, a space, and the timestamp. All emails are unique, meaning there will be no emails with the same subject and timestamp from the same sender.
 */

import java.util.*;

public class EmailOrganizer {
    public List<String> organizeInbox(String inboxString) {
        // TODO: Implement this method
        String[] senderEmails = inboxString.split("\\; ");
        Map<String, Integer> senderEmailCount = new HashMap<>();
        for (String senderEmail : senderEmails) {
            // split each senderEmail
            String [] parts = senderEmail.split("\\, ");
            String emailAddress = parts[0];
            String subject = parts[1];
            String timestamp = parts[2];
            senderEmailCount.put(emailAddress, senderEmailCount.getOrDefault(emailAddress, 0) + 1);
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(senderEmailCount.entrySet());
        entries.sort((a, b) -> {
            int countCompare = Integer.compare(b.getValue(), a.getValue()); // Descending count
            if (countCompare != 0) {
                return countCompare;
            }
            return a.getKey().compareTo(b.getKey()); // Ascending email tie-breaker
        });
        List<String> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : entries ) {
            String rv = String.format("%s %d", entry.getKey(), entry.getValue());
            result.add(rv);
        }
        return result;
    }

    public static void main(String[] args) {
        EmailOrganizer app = new EmailOrganizer();
        String input = "Sender Email Address1, Subject1, 09:00; Sender Email Address2, Subject2, 10:00; Sender Email Address1, Subject3, 12:00";
        List<String> out = app.organizeInbox(input);
        System.out.println(input);
        System.out.println(out);

    }
}
