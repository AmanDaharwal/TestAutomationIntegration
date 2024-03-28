package com.runner;

import com.context.TestExecutionContext;
import com.exceptions.InvalidTestDataException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Drivers {

    public static WebDriver driver;

    private Drivers(){

    }

    public static WebDriver createDriverFor(){
        return createDriverFor(TestRunner.getPlatform());
    }

    private static WebDriver createDriverFor(String platform){
        switch (platform){
            case "web":
                return createWebDriver();
        }
        throw new InvalidTestDataException(platform+ " does not exist");
    }

    private static WebDriver createWebDriver() {

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
        return driver;
    }

    public static void launchUrl(){
        driver.manage().window().fullscreen();
        driver.get(TestRunner.getURL());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void closeBrowser(){
        driver.quit();
    }

    public static WebDriver getInnerDriver(){
        return driver;
    }
}
