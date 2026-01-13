package demo.mowed.interfaces;

import demo.mowed.requests.AuthRequest;
import demo.mowed.responses.AuthResponse;

public interface IAuthorizationService {
    AuthResponse authorize(AuthRequest dto);
}
