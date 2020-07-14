package com.nauvalatmaja.x.cleanarchitecture.order.service.controller;

import com.nauvalatmaja.x.cleanarchitecture.order.service.repository.OrderRepository;
import com.nauvalatmaja.x.cleanarchitecture.persistence.order.OrderPersistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ListOrderControllerTest {
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.2-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MockMvc mvc;

    private UUID orderNumber1 = UUID.randomUUID();
    private UUID orderNumber2 = UUID.randomUUID();
    private UUID orderNumber3 = UUID.randomUUID();

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    @BeforeEach
    void setup() {
        List<OrderPersistence> orders = Arrays.asList(
                OrderPersistence.builder().orderNumber(orderNumber1).userId("1x1").shippingAddress("Jl 1").build(),
                OrderPersistence.builder().orderNumber(orderNumber2).userId("1x2").shippingAddress("Jl 2").build(),
                OrderPersistence.builder().orderNumber(orderNumber3).userId("1x1").shippingAddress("Jl 1").build()
        );
        repository.saveAll(orders);
    }

    @Test
    void givenOrderRecords_whenListOrder_shouldReturnCorrectResult() throws Exception {
        mvc.perform(
                get("/orders/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("userId", "1x1"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.orders[*].userId", everyItem(is("1x1"))))
                .andExpect(
                        jsonPath(
                                "$.orders[*].orderNumber",
                                hasItems(orderNumber1.toString(), orderNumber3.toString())));
    }
}