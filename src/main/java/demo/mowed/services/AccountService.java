package demo.mowed.services;

import demo.mowed.core.ApplicationConstants;
import demo.mowed.core.BookStoreException;
import demo.mowed.core.TransactionType;
import demo.mowed.database.Account;
import demo.mowed.database.AccountTransaction;
import demo.mowed.database.JpaUtil;
import demo.mowed.interfaces.IAccountService;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.requests.*;
import demo.mowed.responses.AccountDetailsRecord;
import demo.mowed.responses.TransactionOverviewRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;

import java.util.List;

/*
Methods named "get" and "add" have public scope.
Methods named "find", "create", have private scope and act directly on the db.
 */
public class AccountService implements IAccountService {
    private final IAuthorizationService authService;

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    public AccountService(IAuthorizationService authService) {
        this.authService = authService;
    }

    @Override
    public AccountDetailsRecord getAccount(GetMessage request) {
        var requestKey = request.getQueryParameters().getQueryInt();
        LOGGER.debug("Message: {}, RequestedKey: {}",
                request.getMessageType(),
                requestKey);

        var authResponse = this.authService.authorize(request.getAuthRequest());

        // only admin user has permission to view customer account details
        if (!authResponse.isAdmin()) {
            throw new BookStoreException("Admin privileges required to view accounts");
        }

        return findAccountRecord(requestKey);
    }

    @Override
    public AccountDetailsRecord addAccount(AccountAddMessage request) {
        var addUserDto = request.getBody();
        var addUserEmail = addUserDto.getUserEmail();
        LOGGER.debug("Message: {}, AddedUser: {}",
                request.getMessageType(),
                addUserEmail);

        var authResponse = this.authService.authorize(request.getAuthRequest());

        // only admin user has permission to add customer account details
        if (!authResponse.isAdmin()) {
            throw new BookStoreException("Admin privileges required to add accounts");
        }

        // confirm dto parameters valid before adding to database
        addUserDto.validate();

        var existingUser = checkUserExists(addUserEmail);
        if (existingUser) {
            throw new BookStoreException("Cannot add new user, existing user: " + addUserEmail);
        }

        var userRole = addUserDto.isAdmin()
                ? ApplicationConstants.ADMIN_ROLE
                : ApplicationConstants.CUSTOMER_ROLE;

        var addUserAccount = new Account(addUserDto.getUserEmail(),
                addUserDto.getUserPassword(),
                userRole,
                addUserDto.getWallet(),
                ApplicationConstants.ACTIVE_STATUS);

        int createdUserKey = createAccount(addUserAccount);
        LOGGER.info("Created Account: " + createdUserKey);

        return findAccountRecord(createdUserKey);
    }

    public boolean checkUserExists(String userEmail) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Account> query = em.createQuery(
                    "SELECT u FROM Account u WHERE u.userEmail = :email",
                    Account.class
            );
            query.setParameter("email", userEmail);
            List<Account> results = query.getResultList();
            return !results.isEmpty();
        }
    }

    /*
    Add entity to database, return new account key
     */
    private int createAccount(Account addUserDto) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(addUserDto);
            em.getTransaction().commit();
            return addUserDto.getKey();
        }
    }

    /*
    Find account and transform value into AccountDetailsRecord
     */
    private @Nullable AccountDetailsRecord findAccountRecord(Integer requestKey) {
        var matchedUser = findAccount(requestKey);
        if (matchedUser == null) {
            LOGGER.warn("No user for key: {}", requestKey);
            return null;
        }

        List<TransactionOverviewRecord> userTransactions = matchedUser
                .getTransactions()
                .stream()
                .map(this::buildTransactionOverview)
                .toList();

        var isAdmin = matchedUser.getRole() == ApplicationConstants.ADMIN_ROLE;
        var isActive = matchedUser.getUserStatus() == ApplicationConstants.ACTIVE_STATUS;

        return new AccountDetailsRecord(requestKey,
                matchedUser.getUserEmail(),
                isAdmin,
                isActive,
                matchedUser.getWallet(),
                userTransactions);
    }

    /*
    Transform from AccountTransaction database entity
    to TransactionOverviewRecord response object
     */
    private TransactionOverviewRecord buildTransactionOverview(AccountTransaction dao) {
        // if no associated book key, then transaction is a deposit
        var transactionType = (dao.getBookKey() == null)
                ? TransactionType.DEPOSIT
                : TransactionType.PURCHASE;
        return new TransactionOverviewRecord(dao.getKey(),
                dao.getTransactionDateTime(),
                transactionType,
                dao.getTransactionAmount(),
                dao.getBookKey(),
                dao.getPurchaseQuantity());
    }

    private Account findAccount(int key) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            var query = em.createQuery("SELECT DISTINCT u FROM Account u LEFT JOIN FETCH u.transactions WHERE u.id = :key",
                            Account.class);
            query.setParameter("key", key);
            // resultSet query to List
            // if no results, return null
            // else return first
            List<Account> results = query.getResultList();
            return results.isEmpty() ? null : results.getFirst();
        }
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var authService = new AuthorizationService();
            var accService = new AccountService(authService);

            var addUserMessage = new AccountAddMessage(
                    new AuthRequest("Bruce.Wayne@demo.com", "N0tV3ryS3cret"),
                    new AccountAddDto("John.Doe.101@demo.com", "s0meW0rds", false, 123.45f)
            );

            var observed = accService.addAccount(addUserMessage);

//            var accountRequest = new GetMessage(
//                    MessageType.GET_ACCOUNT_INFO,
//                    new AuthRequest("Bruce.Wayne@demo.com", "N0tV3ryS3cret"),
//                    new QueryParameters(22)
//            );
//
//            var observed = accService.getAccount(accountRequest);
            System.out.println(observed.toString());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
