package demo.mowed.services;

import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.responses.AuthResponse;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommonTest {
    public static final double CURRENCY_TOLERANCE = 0.001;
    public static final String SOME_ADMIN = "BobTheAdministrator";
    public static final String SOME_CUSTOMER = "Jane.Doe@test.com";
    public static final String ANY_PASSWORD = "1234567";

    /*
    Mock Service will
    - respond with active and admin authorization for "SOME_ADMIN" (defined above)
    - respond with active (but not admin) authorization for any other auth request
     */
    public static IAuthorizationService mockAuthBuilder() {
        IAuthorizationService authService = mock(IAuthorizationService.class);
        // when authorizing with Bob, return valid admin user
        when(authService.authorize(argThat(p -> p != null &&
                p.getUserId().equals(SOME_ADMIN))))
                .thenReturn(new AuthResponse(true, true));

        // for any other auth request, return active but not admin
        when(authService.authorize(argThat(p -> p == null ||
                !p.getUserId().equals(SOME_ADMIN))))
                .thenReturn(new AuthResponse(true, false));

        return authService;
    }
}
