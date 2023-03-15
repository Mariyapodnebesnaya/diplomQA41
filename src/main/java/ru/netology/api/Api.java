package ru.netology.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.api.responses.TransactionResponse;
import ru.netology.data.CardDataHelper;

import static io.restassured.RestAssured.given;

public class Api {
    private static RequestSpecification SPEC = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static TransactionResponse postPay(CardDataHelper request) {
        return given()
                .spec(SPEC)
                .body(request)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract().response().as(TransactionResponse.class);
    }

    public static TransactionResponse postCredit(CardDataHelper request) {
        return given()
                .spec(SPEC)
                .body(request)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(200)
                .extract().response().as(TransactionResponse.class);
    }

    public static String postPayAsError(CardDataHelper request) {
        return given()
                .spec(SPEC)
                .body(request)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(500)
                .extract().response().asString();
    }

    public static String postCreditAsError(CardDataHelper request) {
        return given()
                .spec(SPEC)
                .body(request)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(500)
                .extract().response().asString();
    }
}
