package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse {
	private String orderNumber;
}
