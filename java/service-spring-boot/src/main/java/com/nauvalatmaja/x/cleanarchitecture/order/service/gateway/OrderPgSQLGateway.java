package com.nauvalatmaja.x.cleanarchitecture.order.service.gateway;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.OrderListFilter;
import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderItemPersistence;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;

public class OrderPgSQLGateway implements OrderGateway {
	private OrderRepository repository;

	public OrderPgSQLGateway(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public UUID save(Order order) {
		UUID orderNumber = UUID.randomUUID();
		OrderPersistence o = OrderPersistence.builder()
			.orderNumber(orderNumber)
			.userId(order.getUserId())
			.shippingAddress(order.getShippingAddress())
			.build();
		o.setItems(order.getItems().stream()
				.map(i -> OrderItemPersistence.builder()
						.order(o)
						.productCode(i.getProductCode())
						.quantity(i.getQuantity())
						.price(i.getPrice())
						.build())
				.collect(Collectors.toList()));
		repository.save(o);
		return orderNumber;
	}

	@Override
	public List<Order> list(OrderListFilter filter) {
		return repository.findByUserId(filter.getUserId()).stream()
				.map(i -> Order.builder()
						.orderNumber(i.getOrderNumber())
						.userId(i.getUserId())
						.shippingAddress(i.getShippingAddress())
						.build()).collect(Collectors.toList());
	}

}
