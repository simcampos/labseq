package com.simao.labseq;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class CalculateNumberEndpointTest {

    @Test
    void testCalculateNumber_ValidInput() {
        RestAssured
            .given()
            .when()
            .get("/labseq/5")
            .then()
            .statusCode(200)
            .body("result", equalTo("1"));
    }

    @Test
    void testCalculateNumber_NegativeInput() {
        RestAssured
            .given()
            .when()
            .get("/labseq/-1")
            .then()
            .statusCode(400)
            .body(containsString("Input 'n' must not be negative"));
    }
}