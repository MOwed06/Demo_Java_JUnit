package demo.mowed.services;

import demo.mowed.core.BookException;
import demo.mowed.interfaces.IAuthService;
import demo.mowed.models.AuthRequestDto;
import demo.mowed.models.AuthResponse;

public class AuthService implements IAuthService {
    public AuthResponse Authorize(AuthRequestDto dto) {
        if (dto.getUserId().equals("Fred"))
        {
            throw new BookException("this is fred");
        }
        return new AuthResponse(true, true);
    }
}
