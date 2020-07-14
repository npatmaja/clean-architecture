package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder;

import static org.mockito.Mockito.*;
import static com.google.common.truth.Truth.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;

class ListOrderUseCaseTest {
	
	private ListOrderInputBoundary usecase;
	private ListOrderRequestModel request;
	private ListOrderOutputBoundary presenter;
	private OrderGateway orderGateway;
	private List<Order> orders = Arrays.asList(
			Order.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x1")
				.shippingAddress("address 1")
				.build(),
			Order.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x1")
				.shippingAddress("address 1")
				.build(),
			Order.builder()
				.orderNumber(UUID.randomUUID())
				.userId("1x2")
				.shippingAddress("address 1")
				.build()
			);

	@BeforeEach
	void beforeEach() {
		orderGateway = mock(OrderGateway.class);
		usecase = new ListOrderUseCase(orderGateway);
		request = new ListOrderRequestModel();
		presenter = mock(ListOrderOutputBoundary.class);
	}

	@Test
	void givenEmptyRequest_whenList_shouldCallPresenterPresent() {
		usecase.list(request, presenter);
		
		verify(presenter, times(1)).present(any(ListOrderResponseModel.class));
	}
	
	@Test
	void givenNullRequest_whenList_shouldCallPresenterPresent() {
		request = null;
		
		usecase.list(request, presenter);
		
		verify(presenter, times(1)).present(any(ListOrderResponseModel.class));
	}
	
	@Test
	void givenEmptyRequest_whenList_shouldCallOrderGatewayListWithEmptyFilter() {
		usecase.list(request, presenter);
		
		verify(orderGateway, times(1)).list(OrderListFilter.builder().build());
	}
	
	@Test
	void givenReturnedOrders_whenList_shouldCallPresenterWithCorrectResponseModel() {
		ArgumentCaptor<ListOrderResponseModel> captor = ArgumentCaptor.forClass(ListOrderResponseModel.class);
		
		when(orderGateway.list(OrderListFilter.builder().build())).thenReturn(orders);
		
		usecase.list(request, presenter);
		
		verify(presenter, times(1)).present(captor.capture());
		
		ListOrderResponseModel response = captor.getValue();
		
		assertThat(response.orders).containsExactlyElementsIn(orders.stream().map(i -> {
			OrderResponseModel m = new OrderResponseModel();
			m.orderNumber = i.getOrderNumber();
			m.userId = i.getUserId();
			return m;
		}).collect(Collectors.toList()));
	}

	@Test
	void givenFilterByUser_whenList_shouldCallOrderGatewayListWithCorrectFilter() {
		request.userId = "1x1";
		usecase.list(request, presenter);
		
		verify(orderGateway, times(1)).list(OrderListFilter.builder().userId(request.userId).build());
	}
}
