package ru.rakhovetski.juniormath.config.security.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(KeyCloakConstants.AUTH_SERVER_URL)
                .realm(KeyCloakConstants.ADMIN_REALM)
                .clientId(KeyCloakConstants.ADMIN_CLIENT_ID)
                .username(KeyCloakConstants.ADMIN_USERNAME)
                .password(KeyCloakConstants.ADMIN_PASSWORD)
                .build();
    }
}
