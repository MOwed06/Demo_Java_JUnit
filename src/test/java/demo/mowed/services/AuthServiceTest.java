package demo.mowed.services;

import demo.mowed.messages.AuthRequest;
import demo.mowed.models.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService testObject;

    @BeforeEach
    void setUp() {
        testObject = new AuthService();
    }

    @ParameterizedTest
    @CsvSource({
            "Bruce.Wayne@demo.com, N0tV3ryS3cret, true, true"
    })
    void testAuthorizeValid(String userEmail, String password, boolean expectActive, boolean expectAdmin) {
        // arrange
        var authRequest = new AuthRequest(userEmail, password);
        // act
        var observed = testObject.Authorize(authRequest);
        // assert
        assertEquals(expectActive, observed.isActive());
        assertEquals(expectAdmin, observed.isAdmin());
    }

}