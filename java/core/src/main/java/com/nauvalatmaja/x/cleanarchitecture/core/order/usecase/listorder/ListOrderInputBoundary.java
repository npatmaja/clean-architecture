package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder;

public interface ListOrderInputBoundary {

	public void list(ListOrderRequestModel request, ListOrderOutputBoundary presenter);

}
