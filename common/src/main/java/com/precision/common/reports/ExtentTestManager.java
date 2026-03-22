package com.precision.common.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void createTest(String testName) {
        ExtentTest test = ExtentReportManager.getInstance().createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void removeTest() {
        extentTest.remove();
    }

    public static void attachScreenshot(String filePath) {
        try {
            extentTest.get().addScreenCaptureFromPath(filePath);
        } catch (Exception e) {

        }
    }

    public static void attachScreenshotBase64(String base64) {
        try {
            extentTest.get().fail(
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build()
            );
        } catch (Exception e) {
        }
    }
}