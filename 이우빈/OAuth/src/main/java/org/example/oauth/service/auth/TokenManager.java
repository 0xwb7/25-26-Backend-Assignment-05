package org.example.oauth.service.auth;

import org.example.oauth.dto.TokenDto;

public interface TokenManager {
    TokenDto saveAndReturnToken(Long userId, String role);
    TokenDto validateAndRotate(String refreshToken, long rotateBeforeMs);
}
