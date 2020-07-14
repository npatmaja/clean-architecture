package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderShortResponse {
    private String orderNumber;
    private String userId;
}
