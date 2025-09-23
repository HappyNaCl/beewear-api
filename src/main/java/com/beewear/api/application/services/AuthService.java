package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.auth.LoginUseCase;
import com.beewear.api.application.ports.inbound.auth.RegisterUseCase;
import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import com.beewear.api.application.ports.outbound.security.TokenProviderPort;
import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.exceptions.AlphanumericalPasswordException;
import com.beewear.api.domain.exceptions.ConfirmPasswordMismatchException;
import com.beewear.api.domain.exceptions.DuplicateEmailException;
import com.beewear.api.domain.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class AuthService implements LoginUseCase, RegisterUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenProviderPort tokenProvider;

    @Override
    public AuthResult login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);

        if(!passwordHasher.verifyPassword(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        return new AuthResult(user, accessToken, refreshToken);
    }

    @Override
    public AuthResult register(String email, String username, String rawPassword, String confirmPassword) {
        if(!isValidPassword(rawPassword)) {
            throw new AlphanumericalPasswordException();
        }

        if(!rawPassword.equals(confirmPassword)) {
            throw new ConfirmPasswordMismatchException();
        }

        if(userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }

        String hashedPassword = passwordHasher.hashPassword(rawPassword);

        User newUser = User.builder()
                .email(email)
                .username(username)
                .password(hashedPassword)
                .build();

        User savedUser = userRepository.save(newUser);

        String accessToken = tokenProvider.createAccessToken(savedUser);
        String refreshToken = tokenProvider.createRefreshToken(savedUser);

        return new AuthResult(savedUser, accessToken, refreshToken);
    }


    private boolean isValidPassword(String password) {
        Pattern ALPHANUMERIC_PATTERN =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");

        return ALPHANUMERIC_PATTERN.matcher(password).matches();
    }
}
