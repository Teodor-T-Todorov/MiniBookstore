package bookstore.products;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
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
        Logger logger = (Logger) LoggerFactory.getLogger(Product.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        book.getMoreCopies(2);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("2 copies has been added for a total of 7 for product: LOTR", logsList.get(0).getFormattedMessage());
        assertEquals(Level.INFO, logsList.get(0).getLevel());

        listAppender.stop();
    }

    @Test
    public void testRemoveCopiesLogging() {
        Logger logger = (Logger) LoggerFactory.getLogger(Product.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
        book.removeCopies(2);
        book.removeCopies(4);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("Sold 2 copies of LOTR", logsList.get(0).getFormattedMessage());
        assertEquals("""
                    Not enough copies in stock.\s
                    Current copies 3\s
                    Copies wanted to be sold 4""", logsList.get(1).getFormattedMessage());

        listAppender.stop();
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
