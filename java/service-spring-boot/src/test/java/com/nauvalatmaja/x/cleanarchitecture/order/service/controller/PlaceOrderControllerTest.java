package com.nauvalatmaja.x.cleanarchitecture.order.service.controller;

import java.math.BigDecimal;
import java.util.Arrays;

import com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder.OrderItemRequest;
import com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder.PlaceOrderRequest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class PlaceOrderControllerTest {

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
	
	@Autowired
    private MockMvc mvc;
	
	@AfterEach
	void cleanUp() {
		repository.deleteAll();
	}
	
	@Test
	void givenRequest_whenPlaceOrder_shouldReturnOk() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PlaceOrderRequest request = PlaceOrderRequest.builder()
				.userId("3x1")
				.shippingAddress("JL Kuningan")
				.items(Arrays.asList(
						OrderItemRequest.builder().productCode("CODE_E").quantity(1).price(new BigDecimal(2000)).build(),
						OrderItemRequest.builder().productCode("CODE_F").quantity(1).price(new BigDecimal(1000)).build()
						))
				.build();
		String content = mapper.writeValueAsString(request);
		System.out.println(content);
		mvc.perform(
				MockMvcRequestBuilders.post("/orders/create")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("orderNumber", Is.isA(String.class)));
	}

}
