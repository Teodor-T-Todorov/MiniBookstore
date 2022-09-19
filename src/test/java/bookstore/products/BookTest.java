package bookstore.products;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    public void classInitializationWithNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Product book = new Book("LOTR", -2, 5, "Tolkien", 1000);
                });
    }

    @Test
    public void classInitializationWithNegativeCopies() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Product book = new Book("LOTR", 5, -3, "Tolkien", 1000);
                });
    }

    @Test
    public void testGetMoreCopiesLogging() {
        LogCaptor logCaptor = LogCaptor.forClass(Product.class);
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        int copiesToOrder = 2;

        book.getMoreCopies(copiesToOrder);

        final String expectedLogMessage =
                String.format("%d copies has been added for a total of %d for product: %s",
                        copiesToOrder, book.getCopies(), book.getName());

        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testRemovingLessThanTotalCopiesLogging() {
        LogCaptor logCaptor = LogCaptor.forClass(Product.class);
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);

        int copiesToRemove = 2;
        String expectedLogMessage = String.format("Sold %d copies of %s", copiesToRemove, book.getName());
        book.removeCopies(copiesToRemove);
        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testRemovingMoreThanTotalCopiesLogging() {
        LogCaptor logCaptor = LogCaptor.forClass(Product.class);
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);

        int copiesToRemove = 6;
        final String expectedLogMessage = String.format("""
                    Not enough copies in stock.\s
                    Current copies %d\s
                    Copies wanted to be sold %d""",
                book.getCopies(), copiesToRemove);

        book.removeCopies(copiesToRemove);
        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));;
    }

    @Test
    public void getMoreCopies() {
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        book.getMoreCopies(5);
        assertEquals(10, book.getCopies());
    }

    @Test
    public void gettingNegativeCopiesShouldThrowException() {
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    book.getMoreCopies(-2);
                });
    }

    @Test
    public void removingCopies() {
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        book.removeCopies(3);
        assertEquals(2, book.getCopies());
    }

    @Test
    public void removingNegativeCopiesShouldThrowException() {
        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    book.removeCopies(-2);
                });
    }
}
