package demo.mowed.interfaces;

import demo.mowed.messages.AuthRequest;
import demo.mowed.models.AuthResponse;

public interface IAuthService {
    AuthResponse Authorize(AuthRequest dto);
}
