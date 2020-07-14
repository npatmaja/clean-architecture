package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder;

import java.util.UUID;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class OrderResponseModel {
	public UUID orderNumber;
	public String userId;
}
