package demo.mowed.database;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "bookReviews")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")    
    private int key;

    @Column(name = "Score")   
    private int score;

    @Column(name = "ReviewDate")
    private LocalDate reviewDate;

    @Column(name = "Description")
    private String description;

    @Column(name = "UserKey")
    private Integer userKey;

    @Column(name = "BookKey")
    private int bookKey;

    public BookReview() {
    }

    public BookReview(int key, int score, LocalDate reviewDate, String description, Integer userKey, int bookKey) {
        this.key = key;
        this.score = score;
        this.reviewDate = reviewDate;
        this.description = description;
        this.userKey = userKey;
        this.bookKey = bookKey;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserKey() {
        return userKey;
    }

    public void setUserKey(Integer userKey) {
        this.userKey = userKey;
    }

    public int getBookKey() {
        return bookKey;
    }

    public void setBookKey(int bookKey) {
        this.bookKey = bookKey;
    }
}
