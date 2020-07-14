package com.nauvalatmaja.x.cleanarchitecture.order.service.repository;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import static com.google.common.truth.Truth.*;
import static org.junit.Assume.assumeTrue;

import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderItemPersistence;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.google.common.truth.Correspondence;

@SpringBootTest
@EnableAutoConfiguration
@Testcontainers
public class OrderRepositoryTest {
	
	@Container
	static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.2-alpine");
	
	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Autowired
	private OrderRepository repository;
	
	@AfterEach
	void afterEach() {
		repository.deleteAll();
	}
	
	@Test
	@Transactional
	void givenListOrders_whenSave_shouldSaveTheOrders() {
		List<OrderPersistence> orders = Arrays.asList(
				OrderPersistence.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x1")
				.shippingAddress("JL Kemuning")
				.build(),
				OrderPersistence.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x2")
				.shippingAddress("JL Kemuning")
				.build(),
				OrderPersistence.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x3")
				.shippingAddress("JL Kemuning")
				.build()
				);
		saveOrders(orders);
		assertThat(repository.count()).isEqualTo(3);
		assertThat(repository.findAll()).containsExactlyElementsIn(orders);
	}

	@Transactional
	private void saveOrders(List<OrderPersistence> orders) {
		repository.saveAll(orders);
	}
	
	@Test
	@Transactional
	void givenOrderWithItems_whenSave_shouldPersistToDB() {
		OrderPersistence order = OrderPersistence.builder()
		.orderNumber(UUID.randomUUID())
		.userId("1x1")
		.shippingAddress("JL Kemuning")
		.build();
		order.setItems(Arrays.asList(
				OrderItemPersistence.builder()
				.order(order)
				.productCode("CODE_A")
				.quantity(2)
				.price(new BigDecimal(12000))
				.build(),
				OrderItemPersistence.builder()
				.order(order)
				.productCode("CODE_B")
				.quantity(1)
				.price(new BigDecimal(10000))
				.build()
				));
		saveOrders(Arrays.asList(order));
		
		Optional<OrderPersistence> result = repository.findById(order.getOrderNumber());
		
		assumeTrue(result.isPresent());
		OrderPersistence o = result.get();
		
		assertThat(o.getOrderNumber()).isEqualTo(order.getOrderNumber());
		assertThat(o.getShippingAddress()).isEqualTo(order.getShippingAddress());
		assertThat(o.getUserId()).isEqualTo(order.getUserId());
		
		Correspondence<OrderItemPersistence, OrderItemPersistence> c = Correspondence
				.from(this::compareOrderItem , "equals");
		
		assumeTrue(o.getItems().size() == 2);
		assertThat(o.getItems()).comparingElementsUsing(c)
			.containsExactlyElementsIn(order.getItems());;
	}
	
	private boolean compareOrderItem(OrderItemPersistence actual, OrderItemPersistence expected) {
		return actual.getProductCode().equals(expected.getProductCode())
				&& actual.getPrice() == expected.getPrice()
				&& actual.getPrice().equals(expected.getPrice());
	}

}
