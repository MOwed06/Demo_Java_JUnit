package demo.mowed.services;

import demo.mowed.database.*;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.messages.BookAddUpdateMessage;
import demo.mowed.messages.GetMessage;
import demo.mowed.messages.MessageParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BookService {
    private final IAuthService authService;

    public BookService(IAuthService authService) {
        this.authService = authService;
    }

    public Book findBookByKey(int key) {
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

    public AppUser getUser(String userEmail) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<AppUser> query = em.createQuery(
                    "SELECT u FROM AppUser u WHERE u.userEmail = :email",
                    AppUser.class
            );
            query.setParameter("email", userEmail);
            List<AppUser> results = query.getResultList();
            return results.isEmpty() ? null : results.getFirst();
        }
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var bookService = new BookService(null);
            var observed = bookService.findBookByKey(6);
            System.out.println(observed.getTitle());

            System.out.println("Genre: " + observed.getGenre());
            System.out.println(observed.getReviews().size());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
