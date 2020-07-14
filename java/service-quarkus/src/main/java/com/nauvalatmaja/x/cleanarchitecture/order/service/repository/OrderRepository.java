package com.nauvalatmaja.x.cleanarchitecture.order.service.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class OrderRepository implements PanacheRepositoryBase<OrderPersistence, UUID> {

}
