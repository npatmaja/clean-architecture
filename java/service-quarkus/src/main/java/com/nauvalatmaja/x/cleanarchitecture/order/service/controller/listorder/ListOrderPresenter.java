package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderOutputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderResponseModel;

import java.util.stream.Collectors;

public class ListOrderPresenter implements ListOrderOutputBoundary {
    private ListOrderResponseModel response;

    @Override
    public void present(ListOrderResponseModel response) {
        this.response = response;
    }

    public ListOrderResponse toResponse() {
        return ListOrderResponse.builder()
                .orders(response.orders.stream()
                        .map(i -> OrderShortResponse.builder()
                                .userId(i.userId)
                                .orderNumber(i.orderNumber.toString())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
