package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.messages.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private static IAuthService authService;
    private BookService testObject;

    @BeforeAll
    static void setupOnce(){
        // set authService mock to always respond with valid user
        // when any authRequest received
        authService = mock(IAuthService.class);
        when(authService.Authorize(any(AuthRequest.class)))
                .thenReturn(new AuthResponse(true, true));
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
        var observed = testObject.GetBook(bookRequest);
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
        var observed = testObject.GetBook(bookRequest);
        // assert
        assertEquals(expectedRating, observed.rating());
    }

    @Test
    void testGetBookRatingNull() {
        // arrange
        final int STINKY_CHEESE_BOOk_KEY = 2;
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("someuser", "password"),
                new QueryParameters(STINKY_CHEESE_BOOk_KEY)
        );
        // act
        var observed = testObject.GetBook(bookRequest);
        // assert
        assertNull(observed.rating());
    }
}