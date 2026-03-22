package com.precision.common.utils;

import com.precision.common.driver.DriverManager;
import com.precision.common.logger.LogManager;
import com.precision.common.reports.ExtentTestManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {}

    public static String captureScreenshot(String scenarioName) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";

        String filePath = "target/screenshots/" + fileName;

        try {
            File screenshotDir = new File("target/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            log.info("Screenshot saved: {}", filePath);

            String base64 = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.attachScreenshotBase64(base64);

        } catch (IOException e) {
            log.error("Screenshot failed: {}", e.getMessage());
        }

        return filePath;
    }
}