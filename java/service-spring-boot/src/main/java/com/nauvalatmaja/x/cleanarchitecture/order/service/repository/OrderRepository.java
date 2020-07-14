package com.nauvalatmaja.x.cleanarchitecture.order.service.repository;

import java.util.List;
import java.util.UUID;

import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderPersistence, UUID> {
    List<OrderPersistence> findByUserId(String userId);
}
