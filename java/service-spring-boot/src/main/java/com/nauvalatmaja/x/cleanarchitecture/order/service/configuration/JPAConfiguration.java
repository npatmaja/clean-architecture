package com.nauvalatmaja.x.cleanarchitecture.order.service.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nauvalatmaja.x.cleanarchitecture.order.service.repository")
@EntityScan(basePackages = "com.nauvalatmaja.x.cleanarchitecture.persistence.order")
public class JPAConfiguration {
}
