package ru.rakhovetski.juniormath.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakAccessTokenResponse {

    private Long expiresIn;

    private String idToken;

    private Integer notBeforePolicy;

    private Map<String, Object> otherClaims;

    private Long refreshExpiresIn;

    private String refreshToken;

    private String scope;

    private String sessionState;

    private String token;

    private String tokenType;
}
