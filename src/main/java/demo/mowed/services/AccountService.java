package demo.mowed.services;

import demo.mowed.core.ApplicationConstants;
import demo.mowed.core.BookStoreException;
import demo.mowed.core.TransactionType;
import demo.mowed.database.Account;
import demo.mowed.database.AccountTransaction;
import demo.mowed.database.JpaUtil;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.requests.*;
import demo.mowed.responses.AccountDetailsRecord;
import demo.mowed.responses.TransactionOverviewRecord;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class AccountService {
    private IAuthService authService;

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    public AccountService(IAuthService authService) {
        this.authService = authService;
    }

    public AccountDetailsRecord addAccount(AccountAddMessage request) {
        var addUserDto = request.getAddDto();
        var addUserEmail = addUserDto.getUserEmail();
        LOGGER.debug("Message: {}, RequestedKey: {}",
                request.getMessageType(),
                addUserEmail);

        var authResponse = this.authService.Authorize(request.getAuthRequestDto());

        // only admin user has permission to add customer account details
        if (!authResponse.isAdmin()) {
            throw new BookStoreException("Admin privileges required to add accounts");
        }

        // confirm dto parameters valid before adding to database
        addUserDto.validate();

        var userRole = addUserDto.isAdmin()
                ? ApplicationConstants.ADMIN_ROLE
                : ApplicationConstants.CUSTOMER_ROLE;

        var displayedWallet = addUserDto.getWallet();

        var addUserAccount = new Account(addUserDto.getUserEmail(),
                "some user name",
                addUserDto.getUserPassword(),
                userRole,
                addUserDto.getWallet(),
                ApplicationConstants.ACTIVE_STATUS);

        int createdUserKey = addUserAccount(addUserAccount);

        return getDetailsRecord(createdUserKey);
    }

    private int addUserAccount(Account addUserDto) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(addUserDto);
            em.getTransaction().commit();
            return addUserDto.getKey();
        }
    }

    public AccountDetailsRecord getAccount(GetMessage request) {
        var requestKey = request.getQueryParameters().getQueryInt();
        LOGGER.debug("Message: {}, RequestedKey: {}",
                request.getMessageType(),
                requestKey);

        var authResponse = this.authService.Authorize(request.getAuthRequestDto());

        // only admin user has permission to view customer account details
        if (!authResponse.isAdmin()) {
            throw new BookStoreException("Admin privileges required to view accounts");
        }

        return getDetailsRecord(requestKey);
    }

    private @Nullable AccountDetailsRecord getDetailsRecord(Integer requestKey) {
        var matchedUser = findUser(requestKey);
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

    private TransactionOverviewRecord buildTransactionOverview(AccountTransaction dao) {
        // if no associated book key, then transaction is deposit
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

    private Account findUser(int key) {
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
            var authService = new AuthService();
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
