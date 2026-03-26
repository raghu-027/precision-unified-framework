package com.precision.api.client;
import com.precision.common.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private static RequestSpecification requestSpec;
    private ApiClient() {}

    public static RequestSpecification getBaseSpec() {
        if (requestSpec == null) {
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigReader.get("base.api.url"))
                    .setContentType(ContentType.URLENC)
                    .setAccept(ContentType.JSON)
                    .build();
        }
        return requestSpec;
    }
}