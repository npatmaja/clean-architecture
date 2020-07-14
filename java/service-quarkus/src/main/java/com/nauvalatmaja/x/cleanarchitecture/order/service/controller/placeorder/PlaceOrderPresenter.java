package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderOutputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderResponseModel;

public class PlaceOrderPresenter implements PlaceOrderOutputBoundary {

	private PlaceOrderResponse orderResponse;
	
	@Override
	public void present(PlaceOrderResponseModel response) {
		orderResponse = PlaceOrderResponse.builder().orderNumber(response.orderNumber.toString()).build();
	}
	
	public PlaceOrderResponse toResponse() {
		return this.orderResponse;
	}

}
