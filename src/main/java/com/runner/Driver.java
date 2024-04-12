package com.runner;

import com.entities.TEST_CONTEXT;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class Driver {
    private final static Logger LOGGER = LogManager.getLogger(Driver.class);
    private WebDriver driver;

    Driver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getInnerDriver() {
        return this.driver;
    }

    void launchUrl() {
        this.driver.manage().window().fullscreen();
        this.driver.get(TestRunner.getURL());
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LOGGER.info("URL launched");
    }

    void closeBrowser() {
        this.driver.quit();
    }

    void captureBrowserLogs() {
        LogEntries logEntries = this.driver.manage().logs().get(LogType.BROWSER);
        String browserName = TestRunner.getConfig(TEST_CONTEXT.BROWSER);
        LOGGER.info(browserName + " browser logs start ::: ");
        for (LogEntry logEntry : logEntries.getAll()) {
            LOGGER.info(logEntry.toString());
        }
        LOGGER.info(browserName + " browser logs start ::: ");
    }

    private WebElement getElement(By by){
        return this.driver.findElement(by);
    }

    public void click(By by){
        getElement(by).click();
    }

    public void enterText(By by, String text){
        getElement(by).sendKeys(text);
    }

    public String getText(By by){
        return getElement(by).getText();
    }

    public WebElement waitForElementToBeVisible(By by){
        return waitForElementToBeVisible(by, 10);
    }

    public WebElement waitForElementToBeVisible(By by, int waitInSeconds){
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(waitInSeconds));
        webDriverWait.until(ExpectedConditions.visibilityOf(getElement(by)));
        return getElement(by);
    }

    public WebElement scrollToElementInDevice(By by) {
        WebElement element = getElement(by);
        PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence scrollSequence = new Sequence(touchInput, 0)
                .addAction(touchInput.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), 0, 0))
                .addAction(touchInput.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), 0, element.getLocation().getY()))
                .addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(touchInput, Duration.ofMillis(500)))
                .addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ((AppiumDriver)this.driver).perform(Arrays.asList(scrollSequence));
        return element;
    }

}
