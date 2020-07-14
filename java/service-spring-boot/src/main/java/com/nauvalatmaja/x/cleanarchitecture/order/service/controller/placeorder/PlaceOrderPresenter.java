package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderOutputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderResponseModel;

public class PlaceOrderPresenter implements PlaceOrderOutputBoundary {

	private PlaceOrderResponseModel response;
	
	@Override
	public void present(PlaceOrderResponseModel response) {
		this.response = response;
	}

	public PlaceOrderResponse toResponse() {
		return PlaceOrderResponse.builder()
				.orderNumber(this.response.orderNumber.toString())
				.build();
	}

}
