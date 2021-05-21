package com.lob.app.url;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = TestIntegrationContext.ContainerInitialize.class)
public class TestIntegrationContext {

	@Container
	static GenericContainer redisContainer = new GenericContainer("redis")
			.withExposedPorts(6379);
	@Container
	static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres")
			.withDatabaseName("postgres")
			.withUsername("username")
			.withPassword("password");

	static class ContainerInitialize implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues.of(
					"spring.redis.port="          + redisContainer.getMappedPort(6379),
					"spring.datasource.url="      + postgresContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgresContainer.getUsername(),
					"spring.datasource.password=" + postgresContainer.getPassword()
			).applyTo(applicationContext.getEnvironment());
		}
	}

}
