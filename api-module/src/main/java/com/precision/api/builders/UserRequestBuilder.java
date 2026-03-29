package com.precision.api.builders;

import com.precision.api.client.ApiClient;
import com.precision.common.config.ConfigReader;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class UserRequestBuilder {

    private static final Logger log = LogManager.getLogger(UserRequestBuilder.class);

    private UserRequestBuilder() {}

    public static Response createUser(String email, String password) {
        log.info("REQUEST : POST /api/createAccount");
        log.info("PARAMS  :");
        log.info("email  = {}", email);
        log.info("password  = {}", password);
        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .formParam("name",          ConfigReader.get("user.name"))
                .formParam("email",         email)
                .formParam("password",      password)
                .formParam("title",         ConfigReader.get("user.title"))
                .formParam("birth_date",    ConfigReader.get("user.birth_date"))
                .formParam("birth_month",   ConfigReader.get("user.birth_month"))
                .formParam("birth_year",    ConfigReader.get("user.birth_year"))
                .formParam("firstname",     ConfigReader.get("user.firstname"))
                .formParam("lastname",      ConfigReader.get("user.lastname"))
                .formParam("company",       ConfigReader.get("user.company"))
                .formParam("address1",      ConfigReader.get("user.address1"))
                .formParam("address2",      ConfigReader.get("user.address2"))
                .formParam("country",       ConfigReader.get("user.country"))
                .formParam("zipcode",       ConfigReader.get("user.zipcode"))
                .formParam("state",         ConfigReader.get("user.state"))
                .formParam("city",          ConfigReader.get("user.city"))
                .formParam("mobile_number", ConfigReader.get("user.mobile_number"))
                .when().post("/api/createAccount");
        return response;
    }

    public static Response updateUser(String email, String password) {
        log.info("REQUEST : PUT /api/updateAccount");
        log.info("PARAMS  :");
        log.info("email = {}", email);
        log.info("password  = {}", password);
        log.info("updated name  = {}", ConfigReader.get("updated.name"));
        log.info("updated fname = {}", ConfigReader.get("updated.firstname"));
        log.info("updated lname = {}", ConfigReader.get("updated.lastname"));

        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .formParam("name",      ConfigReader.get("updated.name"))
                .formParam("email",     email)
                .formParam("password",  password)
                .formParam("firstname", ConfigReader.get("updated.firstname"))
                .formParam("lastname",  ConfigReader.get("updated.lastname"))
                .when().put("/api/updateAccount");

        return response;
    }

    public static Response deleteUser(String email, String password) {

        log.info("REQUEST : DELETE /api/deleteAccount");
        log.info("PARAMS  :");
        log.info("email    = {}", email);
        log.info("password = {}", password);

        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .formParam("email",    email)
                .formParam("password", password)
                .when().delete("/api/deleteAccount");

        return response;
    }

}