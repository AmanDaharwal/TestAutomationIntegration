package com.runner;

import com.entities.TEST_CONTEXT;
import com.exceptions.TestExecutionFailedException;
import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Point;
import com.entities.ScrollDirection;

import java.time.Duration;

public class Driver {
    private final static Logger LOGGER = LogManager.getLogger(Driver.class);
    private WebDriver driver;
    private final static double SCROLL_RATIO = 0.5;
    private final static Duration SCROLL_DURATION = Duration.ofMillis(500);

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

    private WebElement getElement(By by) {
        return this.driver.findElement(by);
    }

    public void click(By by) {
        getElement(by).click();
    }

    public void enterText(By by, String text) {
        getElement(by).sendKeys(text);
    }

    public String getText(By by) {
        return getElement(by).getText();
    }

    public WebElement waitForElementToBeVisible(By by) {
        return waitForElementToBeVisible(by, 10);
    }

    public WebElement waitForElementToBeVisible(By by, int waitInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(waitInSeconds));
        webDriverWait.until(ExpectedConditions.visibilityOf(getElement(by)));
        return getElement(by);
    }

    public WebElement waitForElementToBePresent(By by) {
        return waitForElementToBePresent(by, 10);
    }

    public WebElement waitForElementToBePresent(By by, int waitInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(waitInSeconds));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return getElement(by);
    }

    public WebElement scrollToElementInDevice(By by) {
        String prevPageSource = "";
        while (!isEndOfPage(prevPageSource)) {
            prevPageSource = this.driver.getPageSource();
            try {
                return this.driver.findElement(by);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                scroll(ScrollDirection.DOWN, SCROLL_RATIO);
            }
        }
        throw new TestExecutionFailedException(String.format("Unable to locate web element {%s} after scrolling", by));
    }

    public void scroll(ScrollDirection dir, double scrollRatio) {

        if (scrollRatio < 0 || scrollRatio > 1) {
            throw new Error("Scroll distance must be between 0 and 1");
        }
        Dimension size = this.driver.manage().window().getSize();
        System.out.println(size);
        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
        int top = midPoint.y - (int) (midPoint.y * scrollRatio);
        int left = midPoint.x - (int) (midPoint.x * scrollRatio);
        int right = midPoint.x + (int) (midPoint.x * scrollRatio);

        if (dir == ScrollDirection.UP) {
            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DURATION);
        } else if (dir == ScrollDirection.DOWN) {
            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DURATION);
        } else if (dir == ScrollDirection.LEFT) {
            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DURATION);
        } else {
            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DURATION);
        }
    }

    public void swipe(Point start, Point end, Duration duration) {

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) this.driver).perform(ImmutableList.of(swipe));
    }

    private boolean isEndOfPage(String pageSource) {
        return pageSource.equals(this.driver.getPageSource());
    }

}
