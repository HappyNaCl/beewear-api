package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.auth.LoginUseCase;
import com.beewear.api.application.ports.inbound.auth.RefreshTokenUseCase;
import com.beewear.api.application.ports.inbound.auth.RegisterUseCase;
import com.beewear.api.application.ports.outbound.cache.OtpSessionCachePort;
import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import com.beewear.api.application.ports.outbound.security.TokenProviderPort;
import com.beewear.api.application.ports.outbound.security.TokenValidatorPort;
import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.application.services.dto.RefreshTokenResult;
import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthService implements LoginUseCase, RegisterUseCase, RefreshTokenUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenProviderPort tokenProvider;
    private final TokenValidatorPort tokenValidator;
    private final OtpSessionCachePort  otpSessionCachePort;

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
    public AuthResult register(String email, String username, String rawPassword, String confirmPassword, String otpSessionId) {
        String cachedOtpSession = otpSessionCachePort.getOtpSession(email);
        if(cachedOtpSession == null) {
            throw new OtpSessionExpiredException();
        }

        if(!otpSessionId.equals(cachedOtpSession)) {
            throw new InvalidOtpSessionException();
        }

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

        otpSessionCachePort.deactivateOtpSession(email);

        return new AuthResult(savedUser, accessToken, refreshToken);
    }

    private boolean isValidPassword(String password) {
        Pattern ALPHANUMERIC_PATTERN =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");

        return ALPHANUMERIC_PATTERN.matcher(password).matches();
    }

    @Override
    public RefreshTokenResult refreshAccessToken(String refreshToken) {
        if(!tokenValidator.validateRefreshToken(refreshToken)) {
            throw new UnauthorizedException();
        }

        UUID userId = tokenValidator.getRefreshTokenSubject(refreshToken);
        User user = userRepository.findById(userId);

        String newAccessToken = tokenProvider.createAccessToken(user);
        String newRefreshToken = tokenProvider.createRefreshToken(user);

        return RefreshTokenResult.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
