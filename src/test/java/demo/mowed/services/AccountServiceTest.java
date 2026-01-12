package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.core.MessageType;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.requests.*;
import demo.mowed.responses.AuthResponse;
import demo.mowed.responses.BookOverviewRecord;
import demo.mowed.utils.RandomHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private static IAuthorizationService authService;
    private AccountService testObject;

    @BeforeAll
    static void setupOnce(){
        // set authService mock to always respond with valid user
        // when any authRequest received
        authService = mock(IAuthorizationService.class);
        when(authService.Authorize(any(AuthRequest.class)))
                .thenReturn(new AuthResponse(true, true));
    }

    @BeforeEach
    void setup() {
        testObject = new AccountService(authService);
    }

    @Test
    void getAccountBasicInfo() {
        // arrange
        final int BELLA_BARNES_KEY = 5;
        var requestMessage = new GetMessage(
                MessageType.GET_ACCOUNT,
                new AuthRequest("someuser", "password"),
                new QueryParameters(BELLA_BARNES_KEY)
        );
        // act
        var observed = testObject.getAccount(requestMessage);
        // assert
        assertAll("account basic info",
                () -> assertEquals(BELLA_BARNES_KEY, observed.key()),
                () -> assertEquals("Bella.Barnes@demo.com", observed.userEmail()),
                () -> assertEquals(50.0f, observed.wallet()),
                () -> assertFalse(observed.isAdmin()),
                () -> assertTrue(observed.isActive()));
    }

    /*
    This test will generate a randomized account
    Add that account to the database
    and confirm the created object matches expected
     */
    @Test
    void addAccount() {
        // arrange
        var addDto = new AccountAddDto(RandomHelper.generateEmail(),
                "s0meV@lue",
                false,
                RandomHelper.getFloat(34.5f, 231.2f));
        var requestMessage = new AccountAddMessage(
                new AuthRequest("someuser", "password"),
                addDto);
        // act
        var observed = testObject.addAccount(requestMessage);
        // assert
        assertAll("add account info",
                () -> assertTrue(observed.key() > 0),
                () -> assertEquals(addDto.getUserEmail(), observed.userEmail()),
                () -> assertEquals(addDto.getWallet(), observed.wallet()),
                () -> assertFalse(observed.isAdmin()),
                () -> assertTrue(observed.isActive()),
                () -> assertTrue(observed.transactions().isEmpty())
        );
    }
}