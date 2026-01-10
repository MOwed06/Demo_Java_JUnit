package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.database.*;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.messages.*;
import demo.mowed.models.BookDetailsRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookService {
    private final IAuthService authService;

    private static final Logger LOGGER = LogManager.getLogger(BookService.class);

    public BookService(IAuthService authService) {
        this.authService = authService;
    }

    public BookDetailsRecord GetBook(GetMessage request) {
        // authorization failure will throw exception and force failure
        // note that inActive user may view (but not purchase) books
        this.authService.Authorize(request.getAuthRequestDto());

        var requestKey = request.getQueryParameters().getQueryInt();
        LOGGER.debug("RequestedKey: {}", requestKey);

        var matchedBook = findBookByKey(requestKey);
        if (matchedBook == null) {
            LOGGER.warn("No book for key: {}", requestKey);
            return null;
        }

        Float rating = calculateRating(matchedBook.getReviews());
        boolean isAvailable = matchedBook.getStockQuantity() > 0;
        Genre bookGenre = Genre.fromCode(matchedBook.getGenre());
        int reviewCount = matchedBook.getReviews().size();

        return new BookDetailsRecord(requestKey,
                matchedBook.getTitle(),
                matchedBook.getAuthor(),
                matchedBook.getIsbn(),
                matchedBook.getDescription(),
                bookGenre,
                matchedBook.getPrice(),
                isAvailable,
                rating,
                reviewCount);
    }

    private Float calculateRating(List<BookReview> reviews) {
        if (reviews.isEmpty()) {
            return null;
        }

        var average = reviews.stream()
                .mapToInt(BookReview::getScore)
                .average()
                .orElse(0.0);

        return (float)average;
    }


    /*
    Retrieve individual book and child book reviews
    */
    private Book findBookByKey(int key) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT b FROM Book b LEFT JOIN FETCH b.reviews WHERE b.id = :key",
                    Book.class
            );
            query.setParameter("key", key);
            List<Book> results = query.getResultList();
            return results.isEmpty() ? null : results.getFirst();
        }
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var authService = new AuthService();
            var bookService = new BookService(authService);

            var bookRequest = new GetMessage(
                    MessageType.GET_BOOK,
                    new AuthRequest("Savannah.Tucker@demo.com", "N0tV3ryS3cret"),
                    new QueryParameters(6)
            );

            var observed = bookService.GetBook(bookRequest);
            System.out.println(observed);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
