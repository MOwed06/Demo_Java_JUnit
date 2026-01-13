package demo.mowed.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.mowed.interfaces.IAccountService;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.interfaces.IBookService;
import demo.mowed.requests.*;
import demo.mowed.services.AccountService;
import demo.mowed.services.AuthorizationService;
import demo.mowed.services.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ApplicationRunner {
    private final IBookService bookService;
    private final IAccountService accountService;

    private static final Logger LOGGER = LogManager.getLogger(ApplicationRunner.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /*
    Standard constructor instantiates "normal" service objects
     */
    public ApplicationRunner() {
        IAuthorizationService authService = new AuthorizationService();
        this.bookService = new BookService(authService);
        this.accountService = new AccountService(authService);
    }

    /*
    This constructor is for unit testing
     */
    public ApplicationRunner(IBookService bookService, IAccountService accountService){
        this.bookService = bookService;
        this.accountService = accountService;
    }

    public ApplicationResponse processRequest(String userEntry) {
        try {
            if (userEntry.equals("Q") || userEntry.equals("q")) {
                // user has selected quit
                return new ApplicationResponse(false, "Execution complete");
            }
            return processMessageFile(userEntry);
        } catch (BookStoreException be) {
            /* when general user-level application errors encountered,
            display error message to user
            but keep application in running state
            allow user to try again
            */
            LOGGER.warn(be.getMessage());
            return new ApplicationResponse(true, be.getMessage());
        } catch (Exception ex) {
            // terminate execution with more serious exceptions
            LOGGER.error(ex);
            return new ApplicationResponse(false, ex.getMessage());
        }
    }

    private ApplicationResponse processMessageFile(String userEntry) throws IOException {
        // look for file in data directory
        var fullName = String.format("data/%s", userEntry);
        var messageFile = new File(fullName);
        if (!messageFile.exists()) {
            throw new BookStoreException("File not found: " + fullName);
        }

        // first parse to base RequestMessage object to identify message type
        MessageType messageType = OBJECT_MAPPER.readValue(messageFile, RequestMessage.class).getMessageType();

        // parse message second time according to known message type
        // call appropriate service endpoint
        switch (messageType) {
            case MessageType.GET_BOOK:
                var getBookMsg = OBJECT_MAPPER.readValue(messageFile, GetMessage.class);
                var getBookRsp = this.bookService.getBook(getBookMsg);
                return processElement(getBookRsp);

            case MessageType.GET_BOOKS_BY_GENRE:
                var getBooksGenreMsg = OBJECT_MAPPER.readValue(messageFile, GetMessage.class);
                var getBooksGenreRsp = this.bookService.getBooksByGenre(getBooksGenreMsg);
                return processElements(getBooksGenreRsp);

            case MessageType.GET_BOOK_REVIEWS:
                var getReviewsMsg = OBJECT_MAPPER.readValue(messageFile, GetMessage.class);
                var getReviewsRsp = this.bookService.getBookReviews(getReviewsMsg);
                return processElements(getReviewsRsp);

            case MessageType.GET_ACCOUNT:
                var getAccountMsg = OBJECT_MAPPER.readValue(messageFile, GetMessage.class);
                var getAccountRsp = this.accountService.getAccount(getAccountMsg);
                return processElement(getAccountRsp);

            case MessageType.POST_ADD_ACCOUNT:
                var addAccountMsg = OBJECT_MAPPER.readValue(messageFile, AccountAddMessage.class);
                var addAccountRsp = this.accountService.addAccount(addAccountMsg);
                return  processElement(addAccountRsp);

            default:
                throw new BookStoreException("Unknown message type: " + messageType);
        }
    }

    private ApplicationResponse processElement(Object element) {
        return new ApplicationResponse(true, String.valueOf(element));
    }

    private ApplicationResponse processElements(List<?> elements) {
        var elementsStringList = elements
                .stream()
                .map(e -> e.toString())
                .toList();
        var statusMessage = String.join("\n", elementsStringList);
        return new ApplicationResponse(true, statusMessage);
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var appRunner = new ApplicationRunner();
            //var response = appRunner.processRequest("GetBook17.json");
            var response = appRunner.processRequest("GetBooksHistory.json");
            System.out.println(response.statusMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
