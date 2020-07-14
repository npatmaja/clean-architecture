package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import java.util.UUID;

public class PlaceOrderResponseModel {

	public UUID orderNumber;
	

	public PlaceOrderResponseModel(UUID orderNumber) {
		this.orderNumber = orderNumber;
	}

}
