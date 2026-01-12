package demo.mowed.requests;

import demo.mowed.core.BookStoreException;
import lombok.*;

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

    @Getter
    @Setter
    private String userEmail;

    @Getter
    @Setter
    private String userPassword;

    @Getter
    @Setter
    private boolean isAdmin;

    @Getter
    @Setter
    private float wallet;

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
}
