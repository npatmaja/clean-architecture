package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;
import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.OrderItem;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class PlaceOrderUseCaseTest {

	private PlaceOrderInputBoundary usecase;
	private PlaceOrderOutputBoundary presenter;
	private PlaceOrderRequestModel request;
	private OrderGateway orderGateway;

	@BeforeEach
	void setup() {
		orderGateway = Mockito.mock(OrderGateway.class);
		usecase = new PlaceOrderUseCase(orderGateway);
		presenter = Mockito.mock(PlaceOrderOutputBoundary.class);
		request = new PlaceOrderRequestModel();
		request.userId = "1";
		request.shippingAddress = "Shipping address 1";
		request.items = Arrays.asList(
				new OrderItemRequestModel("prd-1", 1, new BigDecimal(13000)),
				new OrderItemRequestModel("prd-2", 1, new BigDecimal(15000))
				);
	}
	
	@Test
	void givenInvalidNullItemOrder_whenPlaceOrder_shouldThrowInvalidRequestException() {
		request.items = null;
		Assertions.assertThrows(InvalidRequestException.class, () -> usecase.place(request, presenter));
	}
	
	@Test
	void givenInvalidEmptyItemOrder_whenPlaceOrder_shouldThrowInvalidRequestException() {
		request.items = new ArrayList<>();
		Assertions.assertThrows(InvalidRequestException.class, () -> usecase.place(request, presenter));
	}

	@Test
	void givenValidRequest_whenPlaceOrder_shouldCallOrderGatewaySave() throws InvalidRequestException {
		usecase.place(request, presenter);
		
		Mockito.verify(orderGateway, Mockito.times(1)).save(ArgumentMatchers.any());
	}
	
	@Test
	void givenValidRequest_whenPlaceOrder_shouldCallOrderGatewayWithCorrectParameter() throws InvalidRequestException {
		ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
		
		usecase.place(request, presenter);
		
		Mockito.verify(orderGateway).save(captor.capture());
		
		Order order = captor.getValue();
		
		Assertions.assertEquals(order.getUserId(), request.userId);
		Assertions.assertEquals(order.getShippingAddress(), request.shippingAddress);
		Assertions.assertIterableEquals(order.getItems(), request.items
				.stream()
				.map(i -> OrderItem.builder()
						.productCode(i.productCode)
						.price(i.price)
						.quantity(i.quantity)
						.build())
				.collect(Collectors.toList()));
	}
	
	@Test
	void givenValidRequest_whenPlaceOrder_shouldCallPresenterPresent() throws InvalidRequestException {
		usecase.place(request, presenter);
		
		Mockito.verify(presenter).present(ArgumentMatchers.any(PlaceOrderResponseModel.class));
	}
	
	@Test
	void givenSuccessSavingOrder_whenPlaceOrder_shouldCallPresenterPresentWithCorrectArg() throws InvalidRequestException {
		ArgumentCaptor<PlaceOrderResponseModel> captor = ArgumentCaptor.forClass(PlaceOrderResponseModel.class);
		UUID uuid = UUID.randomUUID();
		
		Mockito.when(orderGateway.save(ArgumentMatchers.any(Order.class))).thenReturn(uuid);
		usecase.place(request, presenter);
		
		Mockito.verify(presenter, Mockito.times(1)).present(captor.capture());
		PlaceOrderResponseModel response = captor.getValue();
		
		Assertions.assertEquals(response.orderNumber, uuid);
	}
}
