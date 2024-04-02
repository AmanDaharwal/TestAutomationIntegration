package com.runner;

import com.exceptions.InvalidTestDataException;
import io.cucumber.core.cli.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRunner {

    public static final Logger logger = LogManager.getLogger(TestRunner.class);
    private static String platform = "";

    public TestRunner(){
        throw new InvalidTestDataException("Required args not provided to Runner");
    }
    public TestRunner(String[] args){
        String configFilePath = args[0], tags = args[1];
        platform = args[2];
        logger.info("Running tests for platform "+platform+" with configuration in "+configFilePath);
        Setup.load(configFilePath);
        run(tags);
    }

    private void run(String tags) {

        String[] array = { "--tags", tags,
                            "--plugin", "pretty",
                            "--plugin", "html:target/cucumber.html",
                            "--threads", "1",
                            "--plugin", "com.listeners.CucumberPlatformScenarioListener",
                            "--glue", "com.tests.steps",
                            "src/main/resources/features"};
        try {
            byte status = Main.run(array);
            System.exit(status);
        }
        catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }

    static String getURL(){
        return Setup.getURL();
    }

    public static String getPlatform(){
        return platform;
    }

    public static String getConfig(String property){
        return Setup.getConfig(property);
    }
}
