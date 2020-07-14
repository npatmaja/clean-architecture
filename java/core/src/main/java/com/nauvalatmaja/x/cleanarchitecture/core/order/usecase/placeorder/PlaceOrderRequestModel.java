package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import java.util.List;

public class PlaceOrderRequestModel {

	public List<OrderItemRequestModel> items;
	public String userId;
	public String shippingAddress;

}
