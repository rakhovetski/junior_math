package ru.rakhovetski.juniormath.integrational.util;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtGenerator {

    private static final String PREFERRED_USERNAME = "preferred_username";

    public static Jwt generateJwt(String username) {
        return Jwt.withTokenValue("somevalue")
                .header("some", "value")
                .claim(PREFERRED_USERNAME, username)
                .build();
    }

}
