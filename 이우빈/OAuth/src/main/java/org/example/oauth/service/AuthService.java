package org.example.oauth.service;

import lombok.RequiredArgsConstructor;
import org.example.oauth.domain.user.Provider;
import org.example.oauth.domain.user.Role;
import org.example.oauth.domain.user.User;
import org.example.oauth.dto.user.request.SignUpRequest;
import org.example.oauth.exception.BadRequestException;
import org.example.oauth.exception.ErrorMessage;
import org.example.oauth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException(ErrorMessage.ALREADY_EXIST_EMAIL);
                });

        userRepository.save(User.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .role(Role.ROLE_USER)
                .provider(Provider.LOCAL)
                .providerId(null)
                .build());
    }
}
