package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderInputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/orders")
public class ListOrderController {
    @Autowired
    private OrderGateway gateway;

    private ListOrderInputBoundary usecase;

    @PostConstruct
    void postConstruct() {
        usecase = new ListOrderUseCase(gateway);
    }

    @GetMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ListOrderResponse listOrder(String userId) {
        ListOrderPresenter presenter = new ListOrderPresenter();
        ListOrderRequestModel request = new ListOrderRequestModel();
        request.userId = userId;
        usecase.list(request, presenter);
        return presenter.toResponse();
    }
}
