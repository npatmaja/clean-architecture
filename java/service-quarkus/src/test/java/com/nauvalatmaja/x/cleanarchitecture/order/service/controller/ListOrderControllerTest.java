package com.nauvalatmaja.x.cleanarchitecture.order.service.controller;

import static io.restassured.RestAssured.given;

import com.nauvalatmaja.x.cleanarchitecture.order.service.DatabaseResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
public class ListOrderControllerTest {

    @Test
    void givenOrders_whenList_shouldReturnListOfOrders() {
        given().queryParam("userId", "1x1")
                .accept(MediaType.APPLICATION_JSON)
                .when().get("/orders/list")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("orders", Matchers.hasSize(2))
                .body(
                        "orders.orderNumber",
                        CoreMatchers.hasItems(
                                "3dd143ef-5ddf-4808-995c-c68a544911d8",
                                "79a8ffa7-0d59-4c3d-8f15-4427dd9cedba")
                )
                .body("orders.userId", CoreMatchers.everyItem(CoreMatchers.is("1x1")));
    }
}
