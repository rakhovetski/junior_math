package ru.rakhovetski.juniormath.mapper;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Component;
import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;

@Component
public class KeycloakAccessTokenMapper {

    public KeycloakAccessTokenResponse map(AccessTokenResponse token) {
        return KeycloakAccessTokenResponse
                .builder()
                .expiresIn(token.getExpiresIn())
                .idToken(token.getIdToken())
                .notBeforePolicy(token.getNotBeforePolicy())
                .otherClaims(token.getOtherClaims())
                .refreshExpiresIn(token.getRefreshExpiresIn())
                .refreshToken(token.getRefreshToken())
                .scope(token.getScope())
                .sessionState(token.getSessionState())
                .token(token.getToken())
                .tokenType(token.getTokenType())
                .build();
    }
}
