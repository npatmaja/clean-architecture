package com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderListFilter {
	private String userId;
}
