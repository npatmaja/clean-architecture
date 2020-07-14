package com.nauvalatmaja.x.cleanarchitecture.order.service.gateway;

import static org.junit.Assume.assumeTrue;
import static com.google.common.truth.Truth.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.OrderItem;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderItemPersistence;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;
import com.google.common.truth.Correspondence;

@SpringBootTest
@EnableAutoConfiguration
@Testcontainers
class OrderGatewayTest {
	
	@Container
	static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.2-alpine");
	
	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Autowired
	private OrderGateway gateway;
	@Autowired
	private OrderRepository repository;
	
	@Test
	@Transactional
	void givenOrder_whenSave_shouldPersistDataToDB() {
		Order order = Order.builder()
				.userId("1x1")
				.shippingAddress("Jl Kemuning")
				.items(Arrays.asList(
						OrderItem.builder()
							.productCode("A")
							.quantity(1)
							.price(new BigDecimal(1000))
							.build(),
						OrderItem.builder()
							.productCode("B")
							.quantity(2)
							.price(new BigDecimal(1500))
							.build()
						))
				.build();
		UUID oderNumber = gateway.save(order);
		
		Optional<OrderPersistence> result = repository.findById(oderNumber);
		
		assumeTrue(result.isPresent());
		assertThat(result.get().getUserId()).isEqualTo(order.getUserId());
		assertThat(result.get().getShippingAddress()).isEqualTo(order.getShippingAddress());
		assertThat(result.get().getItems())
				.comparingElementsUsing(
						Correspondence.from(this::compareOrderItem, "order items"))
				.containsExactlyElementsIn(order.getItems());
	}

	private boolean compareOrderItem(OrderItemPersistence actual, OrderItem expected) {
		return actual.getProductCode().equals(expected.getProductCode())
				&& actual.getPrice() == expected.getPrice()
				&& actual.getPrice().equals(expected.getPrice());
	}
}
