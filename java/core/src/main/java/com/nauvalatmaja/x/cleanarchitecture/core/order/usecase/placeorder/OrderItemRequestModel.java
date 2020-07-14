package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import java.math.BigDecimal;

public class OrderItemRequestModel {
	public String productCode;
	public int quantity;
	public BigDecimal price;
	public OrderItemRequestModel(String productCode, int quantity, BigDecimal price) {
		this.productCode = productCode;
		this.quantity = quantity;
		this.price = price;
	}
}
