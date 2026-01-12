package demo.mowed.database;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AppUsers")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class AppUser {
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
    private String userName;
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
}
