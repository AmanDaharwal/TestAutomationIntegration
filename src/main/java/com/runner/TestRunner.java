package com.runner;

import com.context.TestExecutionContext;
import com.entities.Platform;
import com.entities.TEST_CONTEXT;
import com.exceptions.InvalidTestDataException;
import io.cucumber.core.cli.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRunner {

    public static final Logger LOGGER = LogManager.getLogger(TestRunner.class);
    private static Platform platform;

    TestRunner() {
        throw new InvalidTestDataException("Required args not provided to Runner");
    }

    TestRunner(String[] args) {
        String configFilePath = args[0], tags = args[1];
        platform = Platform.valueOf(args[2].toLowerCase());
        LOGGER.info("Running tests for platform " + platform + " with configuration in " + configFilePath);
        Setup.load(configFilePath);
        run(tags);
    }

    private void run(String tags) {

        String cucumberReportDirectory = System.getProperty("OUTPUT_DIRECTORY") + "/CucumberReports";

        String[] array = {"--tags", tags,
                "--plugin", "pretty",
                "--plugin", "html:" + cucumberReportDirectory + "/cucumber-html-report.html",
                "--plugin", "json:" + cucumberReportDirectory + "/cucumber.json",
                "--plugin", "junit:" + cucumberReportDirectory + "/cucumber.xml",
                "--threads", Setup.getConfig(TEST_CONTEXT.PARALLEL),
                "--plugin", "com.listeners.CucumberPlatformScenarioListener",
                "--plugin", "com.listeners.ReportPortalScenarioReporterListener",
                "--glue", "com.tests.steps",
                "src/main/resources/features"};
        try {
            byte status = Main.run(array);
            System.exit(status);
        } catch (Exception e) {
            LOGGER.error(e);
            System.exit(1);
        }
    }

    static String getURL() {
        return Setup.getURL();
    }

    public static Platform getPlatform() {
        return platform;
    }

    public static String getConfig(String property) {
        return Setup.getConfig(property);
    }

    public static TestExecutionContext getTestExecutionContext(long threadId) {
        return com.runner.Main.getTestExecutionContext(threadId);
    }
}
