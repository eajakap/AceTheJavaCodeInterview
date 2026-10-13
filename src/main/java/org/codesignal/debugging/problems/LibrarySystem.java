package org.codesignal.debugging.problems;

import java.util.Map;//public class LibrarySystem {
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class LibrarySystem {
    private Map<String, Book> books;
    private Set<String> availableBooks;
    private Set<String> reservedBooks;

    public LibrarySystem() {
        this.books = new HashMap<>();
        this.availableBooks = new HashSet<>();
        this.reservedBooks = new HashSet<>();
    }

    public boolean addBook(String bookId, String title) {
        if (books.containsKey(bookId)) {
            return false;
        }
        books.put(bookId, new Book(bookId, title));
        availableBooks.add(bookId);
        return true;
    }

    public List<String> listBooks() {
        List<String> titles = new ArrayList<>();
        for (String bookId : availableBooks) {
            titles.add(books.get(bookId).getTitle());
        }
        return titles;
    }

    public boolean borrowBook(String bookId) {
        if (!books.containsKey(bookId) || !availableBooks.contains(bookId)) {
            return false;
        }
        Book book = books.get(bookId);
        book.setStatus("borrowed");
        availableBooks.remove(bookId);
        book.addHistory("borrowed");
        return true;
    }

    public boolean returnBook(String bookId) {
        if (!books.containsKey(bookId) || "available".equals(books.get(bookId).getStatus())) {
            return false;
        }
        Book book = books.get(bookId);
        book.setStatus("available");
        availableBooks.add(bookId);
        book.addHistory("returned");
        return true;
    }

    public boolean reserveBook(String bookId) {
        if (!books.containsKey(bookId) || "reserved".equals(books.get(bookId).getStatus())) {
            return false;
        }
        Book book = books.get(bookId);
        book.setStatus("reserved");
        reservedBooks.add(bookId);
        book.addHistory("reserved");
        return true;
    }

    public List<String> getBookHistory(String bookId) {
        Book book = books.get(bookId);
        return book.getHistory();
    }

    static class Book {
        private String id;
        private String title;
        private String status;  // "available" or "borrowed"
        private List<String> history;

        public Book(String id, String title) {
            this.id = id;
            this.title = title;
            this.status = "available";
            this.history = new ArrayList<>();
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getHistory() {
            return new ArrayList<>(history);
        }

        public void addHistory(String action) {
            history.add(action);
        }

    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.addBook("1", "The Great Gatsby");
        library.addBook("2", "1984");
        library.addBook("3", "To Kill a Mockingbird");

        System.out.println("Available books: " + library.listBooks());

        library.borrowBook("1");
        System.out.println("Available books after borrowing 'The Great Gatsby': " + library.listBooks());

        library.returnBook("1");
        System.out.println("Available books after returning 'The Great Gatsby': " + library.listBooks());

        library.reserveBook("2");
        System.out.println("Available books after reserving '1984': " + library.listBooks());

        System.out.println("History of 'The Great Gatsby': " + library.getBookHistory("1"));
    }
}
