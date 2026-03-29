package com.precision.api.client;

import com.precision.common.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient {

    private static final Logger log = LogManager.getLogger(ApiClient.class);
    private static RequestSpecification requestSpec;

    private ApiClient() {}

    public static RequestSpecification getBaseSpec() {
        if (requestSpec == null) {
            String baseUrl = ConfigReader.get("base.api.url");
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .setContentType(ContentType.URLENC)
                    .setAccept(ContentType.JSON)
                    .build();
        }
        return requestSpec;
    }
}