package bookstore;

import bookstore.products.Album;
import bookstore.products.Book;
import bookstore.products.Movie;
import bookstore.products.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreTest {
    Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
    Product movie = new Movie("Interstellar", 20, 10, "Christoper Nolan");
    Product album = new Album("In Rainbows", 15, 20, "Radiohead");
    List<Product> productList = new ArrayList<>();
    Bookstore bookstore = new Bookstore("testStore", productList);

    @Test
    public void addNewProductToBookstore() {
        bookstore.addProductToStore(book);
        assertTrue(bookstore.contains(book));
    }

    @Test
    public void testLogWhenAddingNewProduct() {
        Logger logger = (Logger) LoggerFactory.getLogger(Bookstore.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        bookstore.addProductToStore(book);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("5 copies of [LOTR, Book] got added to the bookstore", logsList.get(0).getFormattedMessage());

        listAppender.stop();
    }

    @Test
    public void testLogWhenAddingExistingProduct() {
        Logger logger = (Logger) LoggerFactory.getLogger(Bookstore.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        bookstore.addProductToStore(book);
        bookstore.addProductToStore(book);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("[LOTR, Book] is already available in the store", logsList.get(1).getFormattedMessage());

        listAppender.stop();
    }

    @Test
    public void removeProductFromBookstore() {
        bookstore.addProductToStore(book);
        bookstore.removeProductFromStore(book);
        assertFalse(bookstore.contains(book));
    }

    @Test
    public void testLogWhenRemovingExistingProduct() {
        Logger logger = (Logger) LoggerFactory.getLogger(Bookstore.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        bookstore.addProductToStore(book);
        bookstore.removeProductFromStore(book);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("[LOTR, Book] got removed to the bookstore", logsList.get(1).getFormattedMessage());

        listAppender.stop();
    }

    @Test
    public void testLogWhenRemovingNonexistentProduct() {
        Logger logger = (Logger) LoggerFactory.getLogger(Bookstore.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        bookstore.removeProductFromStore(book);

        List<ILoggingEvent> logsList = listAppender.list;

        assertEquals("Can't remove, [LOTR, Book] is not included in the store", logsList.get(0).getFormattedMessage());

        listAppender.stop();
    }

    @Test
    public void sellingLessCopiesThanTotalAmount() {
        bookstore.addProductToStore(book);
        bookstore.sellProduct(book, 3);
        assertEquals(2, book.getCopies());
    }

    @Test
    public void sellingNegativeAmountOfCopies() {
        bookstore.addProductToStore(book);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    bookstore.sellProduct(book,-2);
                });
    }

    @Test
    public void resupplyingCopiesOfProduct() {
        bookstore.addProductToStore(book);
        bookstore.resupplyProduct(book, 5);
        assertEquals(10, book.getCopies());
    }

    @Test
    public void resupplyingNegativeAmountOfCopies() {
        bookstore.addProductToStore(book);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    bookstore.resupplyProduct(book, -2);
                });
    }
}