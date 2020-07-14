package com.nauvalatmaja.x.cleanarchitecture.order.service.configuration;

import com.nauvalatmaja.x.cleanarchitecture.core.order.gateway.OrderGateway;
import com.nauvalatmaja.x.cleanarchitecture.order.service.gateway.OrderPgSQLGateway;
import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayConfiguration {

	@Bean
	public OrderGateway orderGateway(OrderRepository repository) {
		return new OrderPgSQLGateway(repository);
	}
}
