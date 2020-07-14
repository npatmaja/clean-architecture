package com.nauvalatmaja.x.cleanarchitecture.core.order.entity;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
	private String productCode;
	private int quantity;
	private BigDecimal price;
}
