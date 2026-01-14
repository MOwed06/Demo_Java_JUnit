package demo.mowed.requests;

import demo.mowed.core.ApplicationConstants;
import demo.mowed.core.BookStoreException;
import demo.mowed.utils.RandomHelper;

import java.util.ArrayList;
import java.util.List;

/*
Create new account entry
Account is always active at creation
 */
public class AccountAddDto {
    private final int EMAIL_MIN_SIZE = 5;
    private final int EMAIL_MAX_SIZE = 100;
    private final int PASSWORD_MIN_SIZE = 4;
    private final int PASSWORD_MAX_SIZE = 20;
    private final float WALLET_MIN_SIZE = 1.0f;
    private final float WALLET_MAX_SIZE = 5000f;

    private String userEmail;

    private String userPassword;

    private boolean isAdmin;

    private float wallet;

    public AccountAddDto() {
    }

    public AccountAddDto(String userEmail, String userPassword, boolean isAdmin, float wallet) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
        this.wallet = wallet;
    }

    /*
    Confirm field values valid before add/update operation
    This logic is temporary until I identify more suitable solution
     */
    public void validate() {
        List<String> validationErrors = new ArrayList<>();

        if (ApplicationConstants.RANDOM_REPLACE.equals(userEmail)){
            userEmail = RandomHelper.generateEmail();
        }

        if (userEmail == null) {
            validationErrors.add("userEmail is required");
        } else if (userEmail.length() < EMAIL_MIN_SIZE ) {
            validationErrors.add("userEmail length < " + EMAIL_MIN_SIZE);
        } else if (userEmail.length() > EMAIL_MAX_SIZE ) {
            validationErrors.add("userEmail length > " + EMAIL_MAX_SIZE);
        }

        if (userPassword == null) {
            validationErrors.add("userPassword is required");
        } else if (userPassword.length() < PASSWORD_MIN_SIZE ) {
            validationErrors.add("userPassword length < " + PASSWORD_MIN_SIZE);
        } else if (userPassword.length() > PASSWORD_MAX_SIZE ) {
            validationErrors.add("userPassword length > " + PASSWORD_MAX_SIZE);
        }

        if (wallet < WALLET_MIN_SIZE) {
            validationErrors.add("wallet < " + WALLET_MIN_SIZE);
        }
        if (wallet > WALLET_MAX_SIZE) {
            validationErrors.add("wallet > " + WALLET_MAX_SIZE);
        }

        if (!validationErrors.isEmpty()) {
            // if any validation errors, throw exception
            throw new BookStoreException(String.join(", ", validationErrors));
        }
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
}
