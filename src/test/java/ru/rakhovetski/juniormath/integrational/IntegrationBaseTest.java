package ru.rakhovetski.juniormath.integrational;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.rakhovetski.juniormath.integrational.annotation.IT;

@IT
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
