package demo.mowed.database;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AppUsers")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")
    @Getter
    private int key;

    @Column(name = "Role")
    @Getter
    @Setter
    private int role;

    @Column(name = "UserEmail")
    @Getter
    @Setter
    private String userEmail;

    @Column(name = "UserName")
    @Getter
    @Setter
    private String userName = "Not Specified"; // user name exists in db table, but is unsed by this application
;
    @Column(name = "Password")
    @Getter
    @Setter
    private String password;

    @Column(name = "Wallet")
    @Getter
    @Setter
    private float wallet;
    
    @Column(name = "IsActive")
    @Getter
    @Setter
    private int userStatus;

    @Getter
    @Setter
    @OneToMany(mappedBy = "userKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransaction> transactions = new ArrayList<>();

    public Account(String userEmail, String password, int role, float wallet, int userStatus) {
        this.userEmail = userEmail;
        this.password = password;
        this.role = role;
        this.wallet = wallet;
        this.userStatus = userStatus;
        this.transactions = new ArrayList<>();
    }
}
