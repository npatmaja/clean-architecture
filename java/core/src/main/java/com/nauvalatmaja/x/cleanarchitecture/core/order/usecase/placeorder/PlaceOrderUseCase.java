package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;
import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.OrderItem;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;

public class PlaceOrderUseCase implements PlaceOrderInputBoundary {
	
	private OrderGateway orderGateway;
	
	public PlaceOrderUseCase(OrderGateway orderGateway) {
		this.orderGateway = orderGateway;
	}

	@Override
	public void place(PlaceOrderRequestModel request, PlaceOrderOutputBoundary presenter) throws InvalidRequestException {
		if (request.items == null || request.items.isEmpty()) {
			throw new InvalidRequestException("", "");
		}
		Order order = Order.builder()
				.userId(request.userId)
				.shippingAddress(request.shippingAddress)
				.items(buildOrderItems(request.items))
				.build();
		UUID orderNumber = orderGateway.save(order);
		presenter.present(new PlaceOrderResponseModel(orderNumber));
	}

	private List<OrderItem> buildOrderItems(List<OrderItemRequestModel> items) {
		return items.stream().map(i -> OrderItem.builder()
				.productCode(i.productCode)
				.price(i.price)
				.quantity(i.quantity)
				.build())
				.collect(Collectors.toList());
	}
}
