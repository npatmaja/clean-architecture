package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderResponse {
    List<OrderShortResponse> orders;
}
