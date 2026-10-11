package org.educative.modules.module2.maps;

import org.educative.modules.module2.maps.BookSolution.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookSolutionTest {
    @Test
    void bookComparesByPublicationYearThenTitle() {
        assertTrue(new Book("Z", 2020).compareTo(new Book("A", 2021)) < 0);
        assertTrue(new Book("A", 2021).compareTo(new Book("B", 2021)) < 0);
        assertEquals(0, new Book("A", 2021).compareTo(new Book("A", 2021)));
    }

    @Test
    void bookEqualsHashCodeAndToStringUseTitleAndYear() {
        Book book = new Book("Java 101", 2021);

        assertEquals(book, new Book("Java 101", 2021));
        assertEquals(book.hashCode(), new Book("Java 101", 2021).hashCode());
        assertNotEquals(book, new Book("Java 101", 2022));
        assertNotEquals(book, new Book("Java 102", 2021));
        assertEquals("Java 101", book.getTitle());
        assertEquals(2021, book.getPublicationYear());
        assertEquals("Book{title='Java 101', publicationYear=2021}", book.toString());
    }

    @Test
    void mainPrintsBooksInPublicationYearOrder() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> BookSolution.main(new String[0]));

        assertEquals(List.of("Java 101 - 2021", "Java 102 - 2022", "Java 103 - 2023"), lines);
    }
}
