package com.precision.hybrid.context;

import io.restassured.response.Response;

public class HybridContext {

    private String email;
    private String password;
    private Response createUserResponse;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Response getCreateUserResponse() {
        return createUserResponse;
    }

    public void setCreateUserResponse(Response createUserResponse) {
        this.createUserResponse = createUserResponse;
    }
}