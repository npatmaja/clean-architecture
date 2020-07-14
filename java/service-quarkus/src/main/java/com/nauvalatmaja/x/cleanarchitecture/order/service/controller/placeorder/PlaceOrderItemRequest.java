package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.placeorder;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlaceOrderItemRequest {
	private String productCode;
	private int quantity;
	private BigDecimal price;
}
