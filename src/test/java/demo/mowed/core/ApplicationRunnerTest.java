package demo.mowed.core;

import demo.mowed.interfaces.IBookService;
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

    @BeforeEach
    void setup() {
        mockBookService = mock(IBookService.class);
        testObject = new ApplicationRunner(mockBookService);
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