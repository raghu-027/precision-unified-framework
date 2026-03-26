package com.precision.api.validators;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserValidator {

    private static final Logger log = LogManager.getLogger(UserValidator.class);

    private UserValidator() {}

    private static JsonPath parseResponse(Response response) {
        return JsonPath.from(response.asString());
    }

    public static void validateUserCreated(Response response) {
        log.info("Validating user creation response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode = jsonPath.getInt("responseCode");
        String message = jsonPath.getString("message");

        assert responseCode == 201 : "Expected 201 but got " + responseCode;
        assert message.equals("User created!") : "Unexpected message: " + message;
    }

    public static void validateUserDeleted(Response response) {
        log.info("Validating user deletion response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode = jsonPath.getInt("responseCode");
        String message = jsonPath.getString("message");

        assert responseCode == 200 : "Expected 200 but got " + responseCode;
        assert message.equals("Account deleted!") : "Unexpected message: " + message;
    }

    public static void validateUserUpdated(Response response) {
        log.info("Validating user update response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode = jsonPath.getInt("responseCode");
        String message = jsonPath.getString("message");

        assert responseCode == 200 : "Expected 200 but got " + responseCode;
        assert message.equals("User updated!") : "Unexpected message: " + message;
    }

}
