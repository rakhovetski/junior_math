package ru.rakhovetski.juniormath.integrational;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.rakhovetski.juniormath.integrational.annotation.IT;

@IT
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "TEACHER", "USER"})
@ActiveProfiles("test")
public class IntegrationBaseTest {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withUsername("admin")
            .withPassword("admin");

    static {
        postgresContainer.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
    }

}
