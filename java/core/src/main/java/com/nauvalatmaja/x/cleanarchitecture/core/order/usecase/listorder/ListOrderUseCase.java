package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder;

import java.util.List;
import java.util.stream.Collectors;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;

public class ListOrderUseCase implements ListOrderInputBoundary {

	private OrderGateway orderGateway;
	
	public ListOrderUseCase(OrderGateway orderGateway) {
		this.orderGateway = orderGateway;
	}

	@Override
	public void list(ListOrderRequestModel request, ListOrderOutputBoundary presenter) {
		OrderListFilter filter = OrderListFilter.builder().build();
		
		if (request != null && request.userId != null && !request.userId.isEmpty()) {
			filter = OrderListFilter.builder().userId(request.userId).build();			
		}
		
		List<Order> orders = orderGateway.list(filter);
		
		ListOrderResponseModel response = new ListOrderResponseModel();
		response.orders = orders.stream().map(i -> {
			OrderResponseModel m = new OrderResponseModel();
			m.orderNumber = i.getOrderNumber();
			m.userId = i.getUserId();
			return m;
		}).collect(Collectors.toList());
		
		presenter.present(response);
	}

}
