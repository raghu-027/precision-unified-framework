package com.precision.common.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.precision.common.config.ConfigReader;
import com.precision.common.logger.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;

    private ExtentReportManager() {}

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String reportPath = "target/extent-reports/AutomationReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Precision Automation Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Project", "Precision Automation");
        extent.setSystemInfo("Browser", ConfigReader.get("browser"));
        extent.setSystemInfo("Base URL", ConfigReader.get("base.url"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Executed By", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        log.info("ExtentReports instance created at: {}", reportPath);
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReports flushed successfully");
        } else {
            log.warn("ExtentReports instance is null — nothing to flush");
        }
    }
}