package ru.rakhovetski.juniormath.util;

import org.springframework.security.oauth2.jwt.Jwt;

public class DataByJwtUtil {

    private static final String USERNAME_JWT_CLAIM = "preferred_username";

    public static String getUsernameJwtClaim(Jwt jwtToken) {
        return jwtToken.getClaim(USERNAME_JWT_CLAIM);
    }
}
