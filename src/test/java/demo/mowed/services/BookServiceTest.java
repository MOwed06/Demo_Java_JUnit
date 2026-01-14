package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.core.MessageType;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.requests.*;
import demo.mowed.responses.AuthResponse;
import demo.mowed.responses.BookOverviewRecord;
import demo.mowed.responses.BookReviewRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private static IAuthorizationService authService;
    private BookService testObject;

    // collection of pre-existing books in BigBooks.db
    private static List<BookOverviewRecord> historyBooks;
    private static List<BookReviewRecord> gregorBookReviews;

    @BeforeAll
    static void setupOnce(){
        // set authService mock to always respond with valid user
        // when any authRequest received
        authService = mock(IAuthorizationService.class);
        when(authService.authorize(any(AuthRequest.class)))
                .thenReturn(new AuthResponse(true, true));

        historyBooks = new ArrayList<>();
        historyBooks.add(new BookOverviewRecord(4, "Citizen Soldiers", "Stephen Ambrose", Genre.HISTORY));
        historyBooks.add(new BookOverviewRecord(13, "The American Revolution: An Intimate History", "Geoffrey C. Ward", Genre.HISTORY));
        historyBooks.add(new BookOverviewRecord(32, "A Prayer In Spring", "Dylan Vickers", Genre.HISTORY));

        gregorBookReviews = new ArrayList<>();
        gregorBookReviews.add(new BookReviewRecord(11, "Gregor and the Prophecy of Bane", 3, LocalDateTime.parse("2024-05-16T00:00:00"), "This book was way too dark for kids."));
        gregorBookReviews.add(new BookReviewRecord(12, "Gregor and the Prophecy of Bane", 10, LocalDateTime.parse("2024-05-17T00:00:00"), "Every child should read this book."));
    }

    @BeforeEach
    void setup() {
        testObject = new BookService(authService);
    }

    @Test
    void testGetBookDetails() {
        // arrange
        final int WILD_THINGS_BOOK_KEY = 2;
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("someuser", "password"),
                new QueryParameters(WILD_THINGS_BOOK_KEY)
        );
        // act
        var observed = testObject.getBook(bookRequest);
        // assert
        assertAll("book properties",
                () -> assertEquals("Where the Wild Things Are", observed.title()),
                () -> assertEquals("Maurice Sendak", observed.author()),
                () -> assertEquals("31AB208D-EA2D-458B-B708-744E16BBDE5A", observed.isbn()),
                () -> assertEquals(Genre.CHILDRENS, observed.genre()),
                () -> assertTrue(observed.isAvailable()));
    }

    @ParameterizedTest
    @CsvSource({
            "6, 7.5",   // book #6 associated with 2 reviews
            "15, 8"     // book #15 associated with 1 review only
    })
    void testGetBookRating(int bookKey, Float expectedRating) {
        // arrange
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("someuser", "password"),
                new QueryParameters(bookKey)
        );
        // act
        var observed = testObject.getBook(bookRequest);
        // assert
        assertEquals(expectedRating, observed.rating());
    }

    @Test
    void testGetBookRatingNull() {
        // arrange
        final int STINKY_CHEESE_BOOK_KEY = 2;
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("someuser", "password"),
                new QueryParameters(STINKY_CHEESE_BOOK_KEY)
        );
        // act
        var observed = testObject.getBook(bookRequest);
        // assert
        assertNull(observed.rating());
    }

    @Test
    void testGetBooksByGenre() {
        // arrange
        var bookRequest = new GetMessage(
                MessageType.GET_BOOKS_BY_GENRE,
                new AuthRequest("someuser", "password"),
                new QueryParameters("history")
        );
        // act
        var observed = testObject.getBooksByGenre(bookRequest);
        // assert
        // the returned set of books may be larger than the pre-condition historyBooks
        // confirm expected historyBooks as a subset
        assertTrue(observed.containsAll(historyBooks));
        // confirm all return books are History genre
        assertTrue(observed
                .stream()
                .allMatch(b -> b.genre() == Genre.HISTORY));
    }

    @Test
    void testGetReviewsEmpty() {
        // arrange
        int WILD_THINGS_BOOK_KEY = 2;
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK_REVIEWS,
                new AuthRequest("someuser", "password"),
                new QueryParameters(WILD_THINGS_BOOK_KEY)
        );
        // act
        var observed = testObject.getBookReviews(bookRequest);
        // assert
        assertTrue(observed.isEmpty());
    }

    @Test
    void testGetReviews() {
        // arrange
        int GREGOR_BANE_BOOK_KEY = 9;
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK_REVIEWS,
                new AuthRequest("someuser", "password"),
                new QueryParameters(GREGOR_BANE_BOOK_KEY)
        );
        // act
        var observed = testObject.getBookReviews(bookRequest);
        // assert
        assertTrue(observed.containsAll(gregorBookReviews));
    }
}