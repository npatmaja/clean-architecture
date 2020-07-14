package com.nauvalatmaja.x.cleanarchitecture.core.order.entity;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
	private UUID orderNumber;
	private String userId;
	private String shippingAddress;
	private List<OrderItem> items;
}
