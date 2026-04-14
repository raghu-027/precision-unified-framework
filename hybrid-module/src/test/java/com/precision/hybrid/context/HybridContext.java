package com.precision.hybrid.context;

import io.restassured.response.Response;

public class HybridContext {

    private String email;
    private String password;
    private Response createUserResponse;
    private Response deleteUserResponse;
    private Response productsResponse;

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

    public Response getDeleteUserResponse() {
        return deleteUserResponse;
    }

    public void setDeleteUserResponse(Response deleteUserResponse) {
        this.deleteUserResponse = deleteUserResponse;
    }

    public Response getProductsResponse() {
        return productsResponse;
    }

    public void setProductsResponse(Response productsResponse) {
        this.productsResponse = productsResponse;
    }
}

