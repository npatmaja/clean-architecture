package com.nauvalatmaja.x.cleanarchitecture.order.service.controller;

import com.nauvalatmaja.x.cleanarchitecture.order.service.DatabaseResource;
import com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder.PlaceOrderItemRequest;
import com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder.PlaceOrderRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
public class PlaceOrderControllerTest {
	
	@Test
	void givenRequest_whenPlaceOrder_shouldReturnOk() {
		PlaceOrderRequest request = PlaceOrderRequest.builder()
				.userId("3x1")
				.shippingAddress("JL Kuningan")
				.items(Arrays.asList(
						PlaceOrderItemRequest.builder().productCode("CODE_E").quantity(1).price(new BigDecimal(2000)).build(),
						PlaceOrderItemRequest.builder().productCode("CODE_F").quantity(1).price(new BigDecimal(1000)).build()
						))
				.build();
		
		given().contentType(MediaType.APPLICATION_JSON)
			.body(request)
			.when().post("/orders/create")
			.then()
				.statusCode(200)
				.body("orderNumber", notNullValue(String.class));
	}
	
	@Test
	void givenInvalidRequest_whenPlaceOrder_shouldReturnInvalidRequest() {
		PlaceOrderRequest request = PlaceOrderRequest.builder()
				.userId("3x1")
				.shippingAddress("JL Kuningan")
				.build();
		
		given().contentType(MediaType.APPLICATION_JSON)
			.body(request)
			.when().post("/orders/create")
			.then()
				.statusCode(400)
				.statusCode(400)
				.body("errorCode", equalTo("400x01"))
				.body("errorDescription", equalTo("Invalid Request"));
	}
}
