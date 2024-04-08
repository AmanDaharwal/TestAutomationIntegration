package com.runner;

import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.exceptions.InvalidTestDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Drivers {
    private final static Logger LOGGER = LogManager.getLogger(Drivers.class);

    private Drivers(){

    }

    public static WebDriver createDriverFor(TestExecutionContext context){
        return createDriverFor(TestRunner.getPlatform(), context);
    }

    private static WebDriver createDriverFor(String platform, TestExecutionContext context){
        switch (platform){
            case "web":
                return createWebDriver(context);
        }
        throw new InvalidTestDataException(platform+ " does not exist");
    }

    private static WebDriver createWebDriver(TestExecutionContext context) {
        WebDriver driver = null;
        switch (TestRunner.getConfig("browser")){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
        }
        context.addTestState(TEST_CONTEXT.CURRENT_DRIVER, driver);
        LOGGER.info("Driver created for test - "+context.getTestName());
        return driver;
    }

    public static void launchUrl(TestExecutionContext context){
        WebDriver driver = (WebDriver) context.getTestState(TEST_CONTEXT.CURRENT_DRIVER);
        driver.manage().window().fullscreen();
        driver.get(TestRunner.getURL());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LOGGER.info("URL launched");
    }

    public static void closeBrowser(TestExecutionContext context){
        WebDriver driver = (WebDriver) context.getTestState(TEST_CONTEXT.CURRENT_DRIVER);
        driver.quit();
    }

    public static WebDriver getInnerDriver(long threadId){
        TestExecutionContext context = TestRunner.getTestExecutionContext(threadId);
        return  (WebDriver) context.getTestState(TEST_CONTEXT.CURRENT_DRIVER);
    }
}
