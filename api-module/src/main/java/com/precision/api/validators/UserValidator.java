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
        log.info("[VALIDATE] Create User Response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode  = jsonPath.getInt("responseCode");
        String message    = jsonPath.getString("message");

        log.info("  [CHECK] RESPONSE CODE -> Expected: 201 | Actual: {}", responseCode);
        assert responseCode == 201 : "Expected 201 but got " + responseCode;

        log.info("  [CHECK] MESSAGE       -> Expected: 'User created!' | Actual: '{}'", message);
        assert message.equals("User created!") : "Unexpected message: " + message;

        log.info("  [PASS]  All validations PASSED for Create User");
    }

    public static void validateUserUpdated(Response response) {
        log.info("[VALIDATE] Update User Response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode  = jsonPath.getInt("responseCode");
        String message    = jsonPath.getString("message");

        log.info("  [CHECK] RESPONSE CODE -> Expected: 200 | Actual: {}", responseCode);
        assert responseCode == 200 : "Expected 200 but got " + responseCode;

        log.info("  [CHECK] MESSAGE       -> Expected: 'User updated!' | Actual: '{}'", message);
        assert message.equals("User updated!") : "Unexpected message: " + message;

        log.info("  [PASS]  All validations PASSED for Update User");
    }

    public static void validateUserDeleted(Response response) {
        log.info("[VALIDATE] Delete User Response");
        JsonPath jsonPath = parseResponse(response);
        int responseCode  = jsonPath.getInt("responseCode");
        String message    = jsonPath.getString("message");

        log.info("  [CHECK] RESPONSE CODE -> Expected: 200 | Actual: {}", responseCode);
        assert responseCode == 200 : "Expected 200 but got " + responseCode;

        log.info("  [CHECK] MESSAGE       -> Expected: 'Account deleted!' | Actual: '{}'", message);
        assert message.equals("Account deleted!") : "Unexpected message: " + message;

        log.info("  [PASS]  All validations PASSED for Delete User");
    }
}