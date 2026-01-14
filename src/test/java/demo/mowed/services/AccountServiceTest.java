package demo.mowed.services;

import demo.mowed.core.BookStoreException;
import demo.mowed.core.MessageType;
import demo.mowed.core.TransactionType;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.requests.*;
import demo.mowed.responses.AuthResponse;
import demo.mowed.responses.TransactionOverviewRecord;
import demo.mowed.utils.RandomHelper;
import demo.mowed.utils.TimeHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private static final String SOME_ADMIN = "BobTheAdministrator";
    private static IAuthorizationService authService;
    private AccountService testObject;
    private final int BELLA_BARNES_KEY = 5;
    private static List<TransactionOverviewRecord> user5Transations;

    @BeforeAll
    static void setupOnce(){
        // set authService mock to respond based on specific credentials
        authService = mock(IAuthorizationService.class);

        // when authorizing with Bob, return valid admin user
        when(authService.authorize(argThat(p -> p != null && 
                p.getUserId().equals(SOME_ADMIN))))
                .thenReturn(new AuthResponse(true, true));
        
        // for any other auth request, return active but not admin
        when(authService.authorize(argThat(p -> p == null || 
                !p.getUserId().equals(SOME_ADMIN))))
                .thenReturn(new AuthResponse(true, false));

        // transactions for user 5 (Bella Barnes) are predefined
        user5Transations = new ArrayList<>();
        user5Transations.add(new TransactionOverviewRecord(4, TimeHelper.parse("2025-04-01"), TransactionType.PURCHASE, -33.21f, 2, 3));
        user5Transations.add(new TransactionOverviewRecord(5, TimeHelper.parse("2025-04-02"), TransactionType.PURCHASE, -16.71f, 8, 2));
        user5Transations.add(new TransactionOverviewRecord(6, TimeHelper.parse("2025-04-03"), TransactionType.PURCHASE, -9.07f, 9, 1));
        user5Transations.add(new TransactionOverviewRecord(7, TimeHelper.parse("2025-06-03"), TransactionType.PURCHASE, -9.17f, 9, 1));
        user5Transations.add(new TransactionOverviewRecord(8, TimeHelper.parse("2025-06-04"), TransactionType.DEPOSIT, 75.00f, null, null));
    }

    @BeforeEach
    void setup() {
        testObject = new AccountService(authService);
    }

    @Test
    void getAccountBasicInfo() {
        // arrange
        var requestMessage = new GetMessage(
                MessageType.GET_ACCOUNT,
                new AuthRequest(SOME_ADMIN, "password"),
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

    @Test
    void getAccountTransactions() {
        // arrange
        var requestMessage = new GetMessage(
                MessageType.GET_ACCOUNT,
                new AuthRequest(SOME_ADMIN, "password"),
                new QueryParameters(BELLA_BARNES_KEY)
        );
        // act
        var observed = testObject.getAccount(requestMessage);
        // assert
        assertTrue(observed.transactions().containsAll(user5Transations));
    }

    @Test
    void getAccountNotAdminRejected() {
        // arrange
        final int BELLA_BARNES_KEY = 5;
        var requestMessage = new GetMessage(
                MessageType.GET_ACCOUNT,
                new AuthRequest("someNotAdminPerson", "password"),
                new QueryParameters(BELLA_BARNES_KEY)
        );
        // act
        Exception ex = assertThrows(BookStoreException.class, () -> {
            testObject.getAccount(requestMessage);
        });
        // assert
        assertTrue(ex.getMessage().contains("Admin privileges required"));
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
                new AuthRequest(SOME_ADMIN, "password"),
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

    @Test
    void addAccountExistingUserRejected() {
        // arrange
        final String EXISTING_USER = "Savannah.Tucker@demo.com";
        var addDto = new AccountAddDto(EXISTING_USER,
                "s0meV@lue",
                false,
                RandomHelper.getFloat(34.5f, 231.2f));
        var requestMessage = new AccountAddMessage(
                new AuthRequest(SOME_ADMIN, "password"),
                addDto);
        // act
        Exception ex = assertThrows(BookStoreException.class, () -> {
            testObject.addAccount(requestMessage);
        });
        // assert
        assertTrue(ex.getMessage().contains("Cannot add new user, existing user"));
    }
}