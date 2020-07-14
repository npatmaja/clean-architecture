package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOrderResponse {
    private List<OrderShortResponse> orders;
}
