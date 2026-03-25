package com.precision.api.validators;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.equalTo;

public class UserValidator {

    private static final Logger log = LogManager.getLogger(UserValidator.class);

    private UserValidator() {}

    public static void validateUserCreated(Response response) {
        log.info("Validating user creation response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(201))
                .body("message", equalTo("User created!"));
    }

    public static void validateUserDeleted(Response response) {
        log.info("Validating user deletion response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(200))
                .body("message", equalTo("Account deleted!"));
    }

    public static void validateUserUpdated(Response response) {
        log.info("Validating user update response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(200))
                .body("message", equalTo("User updated!"));
    }
}
