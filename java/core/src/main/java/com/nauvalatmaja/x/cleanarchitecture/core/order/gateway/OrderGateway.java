package com.nauvalatmaja.x.cleanarchitecture.core.order.gateway;

import java.util.List;
import java.util.UUID;

import com.nauvalatmaja.x.cleanarchitecture.core.order.entity.Order;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.OrderListFilter;

public interface OrderGateway {

	public UUID save(Order order);

	public List<Order> list(OrderListFilter build);

}
