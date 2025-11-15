package org.example.oauth.util;

import org.example.oauth.exception.BadRequestException;
import org.example.oauth.exception.ErrorMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserValidator {

    public static Long requireLogin() {
        Long id = currentUserIDorNull();

        if (id == null) {
            throw new BadRequestException(ErrorMessage.NEED_TO_LOGIN);
        }

        return id;
    }

    public static Long currentUserIDorNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!isEffective(authentication)) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }

        try {
            return Long.parseLong(principal.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isEffective(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
