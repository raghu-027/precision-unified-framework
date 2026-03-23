package com.precision.api.builders;
import com.precision.api.client.ApiClient;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserRequestBuilder {

    private UserRequestBuilder() {}

    public static Response createUser(String name, String email, String password, String phone, String title) {
        return given().spec(ApiClient.getBaseSpec()).formParam("name", name).formParam("email", email).formParam("password", password).formParam("phone", phone).formParam("title", title).when()
                .post("/api/createAccount");
    }

    public static Response deleteUser(String email, String password) {
        return given().spec(ApiClient.getBaseSpec()).formParam("email", email).formParam("password", password).when().delete("/api/deleteAccount");
    }

    public static Response updateUser(String name, String email, String password, String phone, String title) {
        return given().spec(ApiClient.getBaseSpec()).formParam("name", name).formParam("email", email).formParam("password", password).formParam("phone", phone).formParam("title", title).when()
                .put("/api/updateAccount");
    }
}
