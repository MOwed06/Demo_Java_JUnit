package demo.mowed.requests;

import lombok.*;

public class AuthRequest {
    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}
