package demo.mowed.services;

import demo.mowed.core.Genre;
import demo.mowed.core.MessageType;
import demo.mowed.database.*;
import demo.mowed.interfaces.IAuthorizationService;
import demo.mowed.interfaces.IBookService;
import demo.mowed.requests.*;
import demo.mowed.responses.BookDetailsRecord;
import demo.mowed.responses.BookOverviewRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/*
authorization failure will throw exception and force failure
note that inActive user may view (but not purchase) books
 */
public class BookService implements IBookService {
    private final IAuthorizationService authService;

    private static final Logger LOGGER = LogManager.getLogger(BookService.class);

    public BookService(IAuthorizationService authService) {
        this.authService = authService;
    }

    /*
    Retrieve book details for specific book
    Parallel to BooksController.cs, GetBook()
     */
    public BookDetailsRecord getBook(GetMessage request) {
        var requestKey = request.getQueryParameters().getQueryInt();
        LOGGER.debug("Message: {}, RequestedKey: {}",
                request.getMessageType(),
                requestKey);

        this.authService.Authorize(request.getAuthRequest());

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

    /*
    Retrieve overview of books by genre
    Parallel to BooksController.cs, GetBooksByGenre()
     */
    public List<BookOverviewRecord> getBooksByGenre(GetMessage request) {
        var genreName = request.getQueryParameters().getQueryString();
        LOGGER.debug("Message: {}, RequestedGenre: {}",
                request.getMessageType(),
                genreName);

        this.authService.Authorize(request.getAuthRequest());

        Genre searchGenre = Genre.fromString(genreName);

        // filter book collection by genre
        List<Book> matchedBooks = findAllBooks()
                .stream()
                .filter(b -> b.getGenre() == searchGenre.getCode())
                .toList();

        // transform from Book entity to BookOverviewRecord
        return matchedBooks
                .stream()
                .map(b -> new BookOverviewRecord(
                        b.getKey(),
                        b.getTitle(),
                        b.getAuthor(),
                        searchGenre
                ))
                .toList();
    }

    /*
    Calculate average book review rating
    Return null if no book review exists
     */
    private Float calculateRating(List<BookReview> reviews) {
        if (reviews.isEmpty()) {
            // no reviews exist
            return null;
        }

        var averageScore = reviews.stream()
                .mapToInt(BookReview::getScore)
                .average()
                .orElse(0.0);
        return (float)averageScore;
    }

    /*
    Retrieve individual book and associated child book reviews
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

    private List<Book> findAllBooks() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        }
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var authService = new AuthorizationService();
            var bookService = new BookService(authService);

            var bookRequest = new GetMessage(
                    MessageType.GET_BOOKS_BY_GENRE,
                    new AuthRequest("Savannah.Tucker@demo.com", "N0tV3ryS3cret"),
                    new QueryParameters("history")
            );

            var observed = bookService.getBooksByGenre(bookRequest);
            for (var item : observed) {
                System.out.println(item.toString());
            }

//            var bookRequest = new GetMessage(
//                    MessageType.GET_BOOK,
//                    new AuthRequest("Savannah.Tucker@demo.com", "N0tV3ryS3cret"),
//                    new QueryParameters(6)
//            );
//
//            var observed = bookService.getBook(bookRequest);
//            System.out.println(observed);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
