package org.educative.modules.module2.maps;

import java.util.TreeMap;
import java.util.Objects;
import java.util.Map;

class Book implements Comparable<Book> {
    private String title;
    private int publicationYear;

    public Book(String title, int publicationYear) {
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public int compareTo(Book other) {
        int publicationYear = Integer.compare(this.publicationYear, other.publicationYear);
        if (publicationYear != 0) {
            return publicationYear;
        }
        return this.title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publicationYear == book.publicationYear && title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationYear, title );
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', publicationYear=" + publicationYear + "}";
    }

}

public class BookSolution {
    public static void main(String[] args) {
        TreeMap<Integer, Book> library = new TreeMap<>();
        library.put(2021, new Book("Java 101", 2021));
        library.put(2022, new Book("Java 102", 2022));
        library.put(2023, new Book("Java 103", 2023));
        // TODO: Add another book to the library with the publication year as the key, remember the book's uniqueness comes from its title and year.
        // TODO: Iterate through the library and print the book title and its publication year.
        for (Map.Entry<Integer, Book> entry : library.entrySet()) {
            Book book = entry.getValue();
            System.out.println(book.getTitle() + " - " + book.getPublicationYear());
        }
    }
}