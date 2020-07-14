package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;
import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.OrderItemRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderInputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.placeorder.PlaceOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
public class PlaceOrderController {
	
	@Autowired
	private OrderGateway gateway;
	
	private PlaceOrderInputBoundary usecase;
	
	@PostConstruct
	void postConstruct() {
		usecase = new PlaceOrderUseCase(gateway);
	}
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlaceOrderResponse placeOrder(@RequestBody PlaceOrderRequest request) throws InvalidRequestException {
		PlaceOrderPresenter presenter = new PlaceOrderPresenter();
		PlaceOrderRequestModel r = new PlaceOrderRequestModel();
		r.userId = request.getUserId();
		r.shippingAddress = request.getShippingAddress();
		r.items = request.getItems() == null
				? Collections.emptyList()
				: request.getItems().stream()
					.map(i ->
						new OrderItemRequestModel(
								i.getProductCode(),
								i.getQuantity(),
								i.getPrice()))
					.collect(Collectors.toList());
		usecase.place(r, presenter);
		return presenter.toResponse();
	}
}