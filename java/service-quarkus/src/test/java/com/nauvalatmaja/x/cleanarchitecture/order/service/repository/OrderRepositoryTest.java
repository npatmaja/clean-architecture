package com.nauvalatmaja.x.cleanarchitecture.order.service.repository;

import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.nauvalatmaja.x.cleanarchitecture.order.service.DatabaseResource;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderItemPersistence;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
class OrderRepositoryTest {
	
	@Inject
	OrderRepository repository;

	@Test
	void givenRecords_whenCount_shouldCountGreaterThan0() {
		assertTrue(repository.count() > 0);
	}
	
	@Test
	void givenRecords_whenGetById_shouldReturnCorrectOrder() {
		OrderPersistence order = repository.findById(UUID.fromString("3dd143ef-5ddf-4808-995c-c68a544911d8"));
		
		assumeNotNull(order);
		assumeNotNull(order.getItems());
		assertEquals(2, order.getItems().size());
	}

	@Test
	void givenRecords_whenFindWithQuery_shouldReturnCorrectOrder() {
		PanacheQuery<OrderPersistence> query = repository.find("userId = :userId", Parameters.with("userId", "1x1").map());
		List<OrderPersistence> orders = query.list();
		assumeNotNull(orders);
		assertEquals(2,orders.size());
	}
	
	@Test
	@Transactional
	void givenOrderss_whenPersist_shouldPersistToDB() {
		UUID orderNumber = UUID.randomUUID();
		
		OrderPersistence order = OrderPersistence.builder()
				.orderNumber(orderNumber)
				.userId("2x1")
				.shippingAddress("JL Tembang kenangan")
				.build();
		List<OrderItemPersistence> items = Arrays.asList(
				OrderItemPersistence.builder().order(order).productCode("CODE_X").quantity(2).price(new BigDecimal(13000)).build(),
				OrderItemPersistence.builder().order(order).productCode("CODE_Y").quantity(1).price(new BigDecimal(10000)).build()
				);
		order.setItems(items);
		
		repository.persist(order);
		
		OrderPersistence actual = repository.findById(orderNumber);
		
		assumeNotNull(actual);
		assumeNotNull(actual.getItems());
		assertIterableEquals(items, actual.getItems());
	}
}
