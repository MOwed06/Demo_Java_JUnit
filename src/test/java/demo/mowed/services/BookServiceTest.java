package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.messages.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org. mockito.MockitoAnnotations;

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
    void testGetSimpleBook() {
        // arrange
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("someuser", "password"),
                new QueryParameters(2)
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
}