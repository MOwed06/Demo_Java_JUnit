package demo.mowed.services;

import demo.mowed.interfaces.IAuthService;
import demo.mowed.messages.AuthRequest;
import demo.mowed.messages.GetMessage;
import demo.mowed.messages.MessageType;
import demo.mowed.messages.QueryParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private IAuthService authService;
    private BookService testObject;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        testObject = new BookService(authService);
    }

    @Test
    void testGetSimpleBook() {
        // arrange
        var bookRequest = new GetMessage(
                MessageType.GET_BOOK,
                new AuthRequest("Savannah.Tucker@demo.com", "N0tV3ryS3cret"),
                new QueryParameters(2)
        );
        // act
        var observed = testObject.GetBook(bookRequest);
        // assert
        assertEquals("Where the Wild Things Are", observed.title());
        assertEquals("Maurice Sendak", observed.author());
    }

    @Test
    void testHappy() {
        // TODO ~ fix this
        assertTrue(true);
    }
}