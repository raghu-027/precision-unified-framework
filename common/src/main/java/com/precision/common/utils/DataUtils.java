package com.precision.common.utils;

import java.util.Map;

public class DataUtils {

    private static final String LOGIN_SHEET    = "LoginData";
    private static final String REGISTER_SHEET = "RegisterData";
    private static final String CART_SHEET     = "CartData";

    private DataUtils() {}

    public static Map<String, String> getRegisterData() {
        return ExcelUtils.getTestData(REGISTER_SHEET, "TC1_RegisterUser");
    }

    public static Map<String, String> getValidLoginData() {
        return ExcelUtils.getTestData(LOGIN_SHEET, "TC2_ValidLogin");
    }

    public static Map<String, String> getInvalidLoginData() {
        return ExcelUtils.getTestData(LOGIN_SHEET, "TC3_InvalidLogin");
    }

    public static Map<String, String> getAddToCartData() {
        return ExcelUtils.getTestData(CART_SHEET, "TC4_AddToCart");
    }

    public static Map<String, String> getRemoveFromCartData() {
        return ExcelUtils.getTestData(CART_SHEET, "TC5_RemoveFromCart");
    }
}