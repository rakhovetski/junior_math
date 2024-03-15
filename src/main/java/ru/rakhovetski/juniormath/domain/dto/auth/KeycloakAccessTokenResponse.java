package ru.rakhovetski.juniormath.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("expires_id")
    private Long expiresIn;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("not_before_policy")
    private Integer notBeforePolicy;

    @JsonProperty("other_claims")
    private Map<String, Object> otherClaims;

    @JsonProperty("refresh_expires_id")
    private Long refreshExpiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("session_state")
    private String sessionState;

    @JsonProperty("token")
    private String token;

    @JsonProperty("token_type")
    private String tokenType;
}
