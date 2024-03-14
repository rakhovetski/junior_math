package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.config.security.keycloak.KeyCloakConstants;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;
import ru.rakhovetski.juniormath.domain.dto.auth.LoginUserRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.LogoutUserRequestDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.domain.enums.SuccessCode;
import ru.rakhovetski.juniormath.exception.IncorrectLogoutTokenException;
import ru.rakhovetski.juniormath.mapper.KeycloakAccessTokenMapper;
import ru.rakhovetski.juniormath.service.AuthenticationService;
import ru.rakhovetski.juniormath.util.ResponseEntityUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final KeycloakAccessTokenMapper keycloakAccessTokenMapper;
    private final String LOGOUT_URL = KeyCloakConstants.AUTH_SERVER_URL + "realms/"
            + "protocol/openid-connect/logout";
    private final String LOGOUT_CONTENT_TYPE_HEADER = "application/x-www-form-urlencoded";

    @Override
    public KeycloakAccessTokenResponse loginUser(LoginUserRequestDto loginUserDto) {
        try (
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(KeyCloakConstants.AUTH_SERVER_URL)
                    .realm(KeyCloakConstants.REALM)
                    .clientId(KeyCloakConstants.CLIENT_ID)
                    .clientSecret(KeyCloakConstants.CLIENT_SECRET)
                    .username(loginUserDto.getUsername())
                    .password(loginUserDto.getPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();
        ) {
            AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
            return keycloakAccessTokenMapper.map(accessToken);
        }
    }

    @Override
    public ResponseEntity<?> logoutUser(LogoutUserRequestDto requestDto) {
        try {
            HttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(LOGOUT_URL);
            httpPost.setHeader("Content-Type", LOGOUT_CONTENT_TYPE_HEADER);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("refresh_token", requestDto.getRefreshToken()));
            params.add(new BasicNameValuePair("client_secret", KeyCloakConstants.CLIENT_ID));
            params.add(new BasicNameValuePair("client_id", KeyCloakConstants.CLIENT_SECRET));

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpClient.execute(httpPost);

            HttpStatus status = HttpStatus.NO_CONTENT;

            return ResponseEntityUtil.responseResultGenerate(status, SuccessCode.USER_SUCCESS_LOGOUT.getMessage());
        } catch (Exception ex) {
            throw new IncorrectLogoutTokenException(ErrorCode.INCORRECT_LOGOUT_TOKEN.getMessage());
        }
    }


}
