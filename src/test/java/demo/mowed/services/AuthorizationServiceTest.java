package demo.mowed.services;

import demo.mowed.core.BookStoreException;
import demo.mowed.requests.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class AuthorizationServiceTest {

    private AuthorizationService testObject;

    @BeforeEach
    void setUp() {
        testObject = new AuthorizationService();
    }

    @ParameterizedTest
    @CsvSource({
            "Bruce.Wayne@demo.com, N0tV3ryS3cret, true, true",
            "Savannah.Tucker@demo.com, N0tV3ryS3cret, true, false",
            "Jacob.Gordon@demo.com, N0tV3ryS3cret, false, false",
    })
    void testAuthorizeValid(String userEmail, String password, boolean expectActive, boolean expectAdmin) {
        // arrange
        var authRequest = new AuthRequest(userEmail, password);
        // act
        var observed = testObject.authorize(authRequest);
        // assert
        assertEquals(expectActive, observed.isActive());
        assertEquals(expectAdmin, observed.isAdmin());
    }

    @ParameterizedTest
    @CsvSource({
            "Some.Guy@test.com, N0tV3ryS3cret, 'No existing user'",
            "Savannah.Tucker@demo.com, somePassword, 'Incorrect user password'"
    })
    void testAuthorizeInvalid(String userEmail, String password, String errorMessage) {
        // arrange
        var authRequest = new AuthRequest(userEmail, password);
        // act
        Exception ex = assertThrows(BookStoreException.class, () -> {
            testObject.authorize(authRequest);
        });
        // assert
        assertTrue(ex.getMessage().contains(errorMessage));
    }

    @Test
    void testAuthorizeInvalidEmptyEmail() {
        // arrange
        var authRequest = new AuthRequest(null, "N0tV3ryS3cret");
        Exception ex = assertThrows(BookStoreException.class, () -> {
            testObject.authorize(authRequest);
        });
        // assert
        assertTrue(ex.getMessage().contains("Invalid user email"));
    }
}