package com.nauvalatmaja.x.cleanarchitecture.order.service.controller.listorder;

import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderInputBoundary;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderRequestModel;
import com.nauvalatmaja.x.cleanarchitecture.core.order.usecase.listorder.ListOrderUseCase;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListOrderController {
    @Inject
    OrderGateway orderGateway;

    private ListOrderInputBoundary usecase;

    @PostConstruct
    void postConstruct() {
        usecase = new ListOrderUseCase(orderGateway);
    }

    @GET
    @Path("/list")
    @Transactional
    public ListOrderResponse listOrder(@QueryParam("userId") String userId) {
        ListOrderPresenter presenter = new ListOrderPresenter();
        ListOrderRequestModel r = new ListOrderRequestModel();
        r.userId = userId;
        usecase.list(r, presenter);
        return presenter.toResponse();
    }
}
