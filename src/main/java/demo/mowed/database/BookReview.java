package demo.mowed.database;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BookReviews")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")
    @Getter
    private int key;

    @Column(name = "Score")
    @Getter
    @Setter
    private int score;

    @Column(name = "ReviewDate")
    @Getter
    @Setter
    private LocalDate reviewDate;

    @Column(name = "Description")
    @Getter
    @Setter
    private String description;

    @Column(name = "UserKey")
    @Getter
    @Setter
    private Integer userKey;

    @Column(name = "BookKey")
    @Getter
    @Setter
    private int bookKey;
}
