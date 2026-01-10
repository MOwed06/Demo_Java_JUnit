package demo.mowed.database;

import jakarta.persistence.*;

@Entity
@Table(name = "AppUsers")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Key")    
    private int key;

    @Column(name = "Role")    
    private int role;

    @Column(name = "UserEmail")
    private String userEmail;

    @Column(name = "UserName")
    private String userName;
;
    @Column(name = "Password")
    private String password;

    @Column(name = "Wallet")
    private float wallet;
    
    @Column(name = "IsActive")        
    private int userStatus;

    public AppUser() {
    }

    public AppUser(int key, int role, String userEmail, String userName, String password, float wallet, int userStatus) {
        this.key = key;
        this.role = role;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        this.wallet = wallet;
        this.userStatus = userStatus;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int status) {
        this.userStatus = status;
    }
}
