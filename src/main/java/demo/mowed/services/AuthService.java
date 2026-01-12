package demo.mowed.services;

import demo.mowed.core.ApplicationConstants;
import demo.mowed.core.BookStoreException;
import demo.mowed.database.Account;
import demo.mowed.database.JpaUtil;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.requests.AuthRequest;
import demo.mowed.responses.AuthResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/*
Authorization Response
Authorization failures will be associated with an exception (refer to AuthService.java).
Users with isActive false can view books, but not purchase books.
Only user with isAdmin true may add/modify users and books.
 */
public class AuthService implements IAuthService {

    private static final Logger LOGGER = LogManager.getLogger(AuthService.class);


    /**
     * Confirm user email and password match existing user
     * @param dto authorization request
     * @return authorization response
     * @throws BookStoreException if user not found or wrong password
     */
    public AuthResponse Authorize(AuthRequest dto) {

        var userEmail = dto.getUserId();
        LOGGER.debug("AuthRequest, user: {}}", userEmail);

        // confirm user exists in database
        var appUser = getUser(userEmail);

        // check for null or empty userEmail
        if (userEmail == null || userEmail.isEmpty()) {
            throw new BookStoreException("Invalid user email");
        }

        if (appUser == null)
        {
            throw new BookStoreException("No existing user: " + userEmail);
        }

        // confirm user password in db matches auth request
        if (!appUser.getPassword().equals(dto.getPassword()))
        {
            throw  new BookStoreException("Incorrect user password");
        }

        // user exists, password matches
        boolean isActive = appUser.getUserStatus() == ApplicationConstants.ACTIVE_STATUS;
        boolean isAdmin = appUser.getRole() == ApplicationConstants.ADMIN_ROLE;
        return new AuthResponse(isActive, isAdmin);
    }

    private Account getUser(String userEmail) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Account> query = em.createQuery(
                    "SELECT u FROM Account u WHERE u.userEmail = :email",
                    Account.class
            );
            query.setParameter("email", userEmail);
            List<Account> results = query.getResultList();
            return results.isEmpty() ? null : results.getFirst();
        }
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var authService = new AuthService();
            var authRequest = new AuthRequest("Bruce.Wayne@demo.com", "N0tV3ryS3cret");
            var observed = authService.Authorize(authRequest);
            System.out.println(observed);


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
