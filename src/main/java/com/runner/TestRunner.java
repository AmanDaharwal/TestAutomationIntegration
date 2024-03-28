package com.runner;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.exceptions.InvalidTestDataException;
import io.cucumber.core.cli.Main;

import java.util.Set;

public class TestRunner {

    private static String platform = "";

    public TestRunner(){
        throw new InvalidTestDataException("Required args not provided to Runner");
    }
    public TestRunner(String[] args){
        String configFilePath = args[0], tags = args[1];
        platform = args[2];
        Setup.load(configFilePath);
        Setup.setConfigProperties();
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
        System.out.println("Inside Run -- ");
        byte status = Main.run(array);
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
