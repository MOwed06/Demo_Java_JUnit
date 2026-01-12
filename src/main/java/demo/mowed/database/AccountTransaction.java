package demo.mowed.database;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")
    @Getter
    private int key;

    @Column(name = "TransactionDate")
    @Getter
    @Setter
    private LocalDateTime transactionDateTime;

    @Column(name = "TransactionAmount")
    @Getter
    @Setter
    private Float transactionAmount;

    @Column(name = "TransactionConfirmation")
    @Getter
    @Setter
    private String transactionConfirmation;

    @Column(name = "UserKey")
    @Getter
    @Setter
    private int userKey;

    @Column(name = "BookKey")
    @Getter
    @Setter
    private Integer bookKey;

    @Column(name = "PurchaseQuantity")
    @Getter
    @Setter
    private Integer purchaseQuantity;
}
