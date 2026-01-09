package demo.mowed.interfaces;

import demo.mowed.models.AuthRequestDto;
import demo.mowed.models.AuthResponse;

public interface IAuthService {
    AuthResponse Authorize(AuthRequestDto dto);
}
