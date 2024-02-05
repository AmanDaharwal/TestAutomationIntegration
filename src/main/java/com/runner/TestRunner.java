package com.runner;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import io.cucumber.core.cli.Main;

public class TestRunner {

    public TestRunner(){
        run();
    }

    private void run() {

        String[] array = { "--tags", "@indigo",
                            "--plugin", "pretty",
                            "--plugin", "html:target/cucumber.html",
                            "--threads", "1",
                            "--plugin", "com.listeners.CucumberPlatformScenarioListener",
                            "--glue", "com.steps",
                            "src/main/resources/features"};
        System.out.println("Inside Run -- ");
        byte status = Main.run(array);
    }
}
