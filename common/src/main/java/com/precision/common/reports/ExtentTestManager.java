package com.precision.common.reports;

import com.aventstack.extentreports.ExtentTest;
import com.precision.common.logger.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentTestManager {

    private static final Logger log = LogManager.getLogger(ExtentTestManager.class);

    private static final ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();

    private ExtentTestManager() {}

    public static ExtentTest createTest(String testName) {
        ExtentTest test = ExtentReportManager.getInstance().createTest(testName);
        extentTestThread.set(test);
        log.info("ExtentTest created: {}", testName);
        return test;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = ExtentReportManager.getInstance().createTest(testName, description);
        extentTestThread.set(test);
        log.info("ExtentTest created: {} — {}", testName, description);
        return test;
    }

    public static ExtentTest getTest() {
        ExtentTest test = extentTestThread.get();
        if (test == null) {
            throw new RuntimeException("ExtentTest not initialized. Call createTest() first.");
        }
        return test;
    }


    public static void logInfo(String message) {
        log.info(message);
        getTest().info(message);
    }

    public static void logPass(String message) {
        log.info("PASS: {}", message);
        getTest().pass(message);
    }

    public static void logFail(String message) {
        log.error("FAIL: {}", message);
        getTest().fail(message);
    }

    public static void logSkip(String message) {
        log.warn("SKIP: {}", message);
        getTest().skip(message);
    }

    public static void logWarning(String message) {
        log.warn("WARNING: {}", message);
        getTest().warning(message);
    }

    public static void attachScreenshot(String screenshotPath) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath);
            log.info("Screenshot attached to report: {}", screenshotPath);
        } catch (Exception e) {
            log.error("Failed to attach screenshot: {}", e.getMessage());
        }
    }


    public static void removeTest() {
        extentTestThread.remove();
        log.info("ExtentTest removed for thread: {}", Thread.currentThread().getName());
    }
}