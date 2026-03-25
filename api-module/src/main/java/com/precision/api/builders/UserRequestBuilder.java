package com.precision.api.builders;

import com.precision.api.client.ApiClient;
import com.precision.common.config.ConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequestBuilder {

    private UserRequestBuilder() {}

    public static Response createUser(String email, String password) {
        return given()
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
    }

    public static Response deleteUser(String email, String password) {
        return given()
                .spec(ApiClient.getBaseSpec())
                .formParam("email",    email)
                .formParam("password", password)
                .when().delete("/api/deleteAccount");
    }

    public static Response updateUser(String email, String password) {
        return given()
                .spec(ApiClient.getBaseSpec())
                .formParam("name",          ConfigReader.get("updated.name"))
                .formParam("email",         email)
                .formParam("password",      password)
                .formParam("firstname",     ConfigReader.get("updated.firstname"))
                .formParam("lastname",      ConfigReader.get("updated.lastname"))
                .when().put("/api/updateAccount");
    }
}