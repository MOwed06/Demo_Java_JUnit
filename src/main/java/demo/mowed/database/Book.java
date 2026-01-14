package demo.mowed.database;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Books")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")
    @Getter
    private int key;

    @Column(name = "Title")
    @Getter
    @Setter
    private String title;

    @Column(name = "Author")
    @Getter
    @Setter
    private String author;

    @Column(name = "Description")
    @Getter
    @Setter
    private String description;

    @Column(name = "Genre")
    @Getter
    @Setter
    private int genre;

    @Column(name = "Price")
    @Getter
    @Setter
    private float price;

    @Column(name = "StockQuantity")
    @Getter
    @Setter
    private int stockQuantity;

    @Column(name = "Isbn")
    @Getter
    @Setter
    private String isbn;

    @Getter
    @Setter
    @OneToMany(mappedBy = "bookKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReview> reviews = new ArrayList<>();

    public Book(String title, String author, String description, int genre, float price, int stockQuantity, String isbn) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isbn = isbn;
    }
}
