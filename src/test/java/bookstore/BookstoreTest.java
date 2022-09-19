package bookstore;

import bookstore.products.Album;
import bookstore.products.Book;
import bookstore.products.Movie;
import bookstore.products.Product;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreTest {
    Product book = new Book("LOTR", 10, 5, "Tolkien", 1000);
    List<Product> productList = new ArrayList<>();
    Bookstore bookstore = new Bookstore("testStore", productList);

    @Test
    public void testAddNewProductToBookstore() {
        bookstore.addProductToStore(book);
        assertTrue(bookstore.contains(book));
    }

    @Test
    public void testLogWhenAddingNewProduct() {
        LogCaptor logCaptor = LogCaptor.forClass(Bookstore.class);
        bookstore.addProductToStore(book);

        final String expectedLogMessage =
                String.format("%d copies of [%s, %s] got added to the bookstore",
                book.getCopies(), book.getName(), book.getClass().getSimpleName());

        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testLogWhenAddingExistingProduct() {
        LogCaptor logCaptor = LogCaptor.forClass(Bookstore.class);

        bookstore.addProductToStore(book);
        bookstore.addProductToStore(book);

        final String expectedLogMessage =
                String.format("[%s, %s] is already available in the store",
                book.getName(), book.getClass().getSimpleName());

        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testRemoveProductFromBookstore() {
        bookstore.addProductToStore(book);
        bookstore.removeProductFromStore(book);
        assertFalse(bookstore.contains(book));
    }

    @Test
    public void testLogWhenRemovingExistingProduct() {
        LogCaptor logCaptor = LogCaptor.forClass(Bookstore.class);

        bookstore.addProductToStore(book);
        bookstore.removeProductFromStore(book);

        final String expectedLogMessage =
                String.format("[%s, %s] got removed to the bookstore",
                        book.getName(), book.getClass().getSimpleName());

        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testLogWhenRemovingNonexistentProduct() {
        LogCaptor logCaptor = LogCaptor.forClass(Bookstore.class);

        bookstore.removeProductFromStore(book);

        final String expectedLogMessage =
                String.format("Can't remove, [%s, %s] is not included in the store",
                        book.getName(), book.getClass().getSimpleName());

        assertTrue(logCaptor.getInfoLogs().contains(expectedLogMessage));
    }

    @Test
    public void testSellingLessCopiesThanTotalAmount() {
        bookstore.addProductToStore(book);
        bookstore.sellProduct(book, 3);
        assertEquals(2, book.getCopies());
    }

    @Test
    public void testSellingNegativeAmountOfCopies() {
        bookstore.addProductToStore(book);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    bookstore.sellProduct(book,-2);
                });
    }

    @Test
    public void testResupplyingCopiesOfProduct() {
        bookstore.addProductToStore(book);
        bookstore.resupplyProduct(book, 5);
        assertEquals(10, book.getCopies());
    }

    @Test
    public void testResupplyingNegativeAmountOfCopies() {
        bookstore.addProductToStore(book);
        assertThrows(IllegalArgumentException.class,
                () -> {
                    bookstore.resupplyProduct(book, -2);
                });
    }
}