package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.OrderItemRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderInputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaceOrderController {
	
	@Inject
	OrderGateway orderGateway;
	
	private PlaceOrderInputBoundary usecase;
	private PlaceOrderPresenter presenter;
	
	@PostConstruct
	void postConstruct() {
		usecase = new PlaceOrderUseCase(orderGateway);
	}
	
	@POST
	@Path("/create")
	@Transactional
	public PlaceOrderResponse placeOrder(PlaceOrderRequest request) throws InvalidRequestException {
		presenter = new PlaceOrderPresenter();
		PlaceOrderRequestModel r = new PlaceOrderRequestModel();
		List<OrderItemRequestModel> items = request.getItems() == null
				? new ArrayList<>()
				: request.getItems().stream()
				.map(i -> new OrderItemRequestModel(i.getProductCode(), i.getQuantity(), i.getPrice()))
				.collect(Collectors.toList());
		r.userId = request.getUserId();
		r.shippingAddress = request.getShippingAddress();
		r.items = items;
		usecase.place(r, presenter);
		return presenter.toResponse();
	}
}
