package com.nauvalatmaja.x.cleanarchitecture.order.service;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {
	public static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>("postgres:12.2-alpine")
			.withDatabaseName("hello_test")
			.withUsername("hello_test")
			.withPassword("hello_test")
			.withExposedPorts(5432);

	@Override
	public Map<String, String> start() {
		DATABASE.start();
		System.out.println("jdbc: " + DATABASE.getJdbcUrl());
		Map<String, String> config = new HashMap<>();
		config.put("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
		config.put("quarkus.datasource.reactive.url", DATABASE.getJdbcUrl().replace("jdbc:", ""));
		return config;
	}

	@Override
	public void stop() {
		DATABASE.stop();
	}
}
