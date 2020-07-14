package com.nauvalatmaja.x.cleanarchitecture.order.service.gateway;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.OrderListFilter;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderItemPersistence;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class OrderPGSQLGateway implements OrderGateway {
	
	@Inject
	OrderRepository repository;

	@Override
	public UUID save(Order order) {
		UUID orderNumber = UUID.randomUUID();
		OrderPersistence o = OrderPersistence.builder()
				.orderNumber(orderNumber)
				.shippingAddress(order.getShippingAddress())
				.userId(order.getUserId())
				.build();
		List<OrderItemPersistence> items = order.getItems().stream()
			.map(i -> OrderItemPersistence.builder()
					.order(o)
					.productCode(i.getProductCode())
					.quantity(i.getQuantity())
					.price(i.getPrice()).build())
			.collect(Collectors.toList());
		o.setItems(items);
		repository.persist(o);
		return orderNumber;
	}

	@Override
	public List<Order> list(OrderListFilter filter) {
		String query = "";
		Parameters parameters = new Parameters();
		if (filter != null && filter.getUserId() != null && !filter.getUserId().isEmpty()) {
			query += "userId = :userId";
			parameters = Parameters.with("userId", filter.getUserId());
		}
		PanacheQuery<OrderPersistence> result;
		if (query.isEmpty()) {
			result = repository.findAll();
		} else {
			result = repository.find(query, parameters.map());
		}
		return result.page(Page.of(0, 10)).list().stream().map(i -> Order.builder()
				.orderNumber(i.getOrderNumber())
				.userId(i.getUserId())
				.shippingAddress(i.getShippingAddress())
				.build())
				.collect(Collectors.toList());
	}

}
