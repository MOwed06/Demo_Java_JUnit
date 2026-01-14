package demo.mowed.core;

import demo.mowed.interfaces.IAccountService;
import demo.mowed.interfaces.IBookService;
import demo.mowed.requests.AccountAddMessage;
import demo.mowed.requests.BookAddMessage;
import demo.mowed.requests.GetMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ApplicationRunnerTest {

    private ApplicationRunner testObject;
    private IBookService mockBookService;
    private IAccountService mockAccountService;

    @BeforeEach
    void setup() {
        mockBookService = mock(IBookService.class);
        mockAccountService = mock(IAccountService.class);
        testObject = new ApplicationRunner(mockBookService, mockAccountService);
    }

    /*
    Confirm that when input file is type GET_BOOKS
    then BookService.getBook is called
     */
    @Test
    void testGetBookProcess() {
        // arrange
        String userEntry = "GetBook17.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockBookService).getBook(any(GetMessage.class));
    }

    @Test
    void testGetBooksGenreProcess() {
        // arrange
        String userEntry = "GetBooksHistory.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockBookService).getBooksByGenre(any(GetMessage.class));
    }

    @Test
    void testGetBooksReviewsProcess() {
        // arrange
        String userEntry = "GetBookReviews06.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockBookService).getBookReviews(any(GetMessage.class));
    }

    @Test
    void testGetAccountProcess() {
        // arrange
        String userEntry = "GetUser22.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockAccountService).getAccount(any(GetMessage.class));
    }

    @Test
    void testAddAccountProcess() {
        // arrange
        String userEntry = "AddAccountJohnDoe.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockAccountService).addAccount(any(AccountAddMessage.class));
    }

    @Test
    void testAddBookProcess() {
        // arrange
        String userEntry = "AddBookGreatContradiction.json";
        // act
        testObject.processRequest(userEntry);
        // assert
        verify(mockBookService).addBook(any(BookAddMessage.class));
    }

    @Test
    void testUserQuit() {
        // arrange
        String userEntry = "q";
        // act
        var observed = testObject.processRequest(userEntry);
        // assert
        assertAll("quit response",
            () -> assertFalse(observed.remainActive()),
            () -> assertEquals("Execution complete", observed.statusMessage())
        );
    }
}