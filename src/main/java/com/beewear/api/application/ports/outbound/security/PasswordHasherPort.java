package com.beewear.api.application.ports.outbound.security;

public interface PasswordHasherPort {
    String hashPassword(String password);
    boolean verifyPassword(String password, String hashedPassword);
}
