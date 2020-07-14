package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;

public interface PlaceOrderInputBoundary {

	void place(PlaceOrderRequestModel request, PlaceOrderOutputBoundary presenter) throws InvalidRequestException;

}
