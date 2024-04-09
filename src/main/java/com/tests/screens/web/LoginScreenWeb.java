package com.tests.screens.web;

import com.context.TestExecutionContext;
import com.reporters.ExtendTestLogger;
import com.runner.TestRunner;
import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginScreenWeb extends LoginScreen {
    private final By byUsernameTxtBoxId = By.id("user-name");
    private final By byPasswordTxtBoxId = By.id("password");
    private final By byLoginBtnId = By.id("login-button");
    private WebDriver driver;
    private TestExecutionContext context;

    public LoginScreenWeb(WebDriver driver) {
        this.driver = driver;
        long threadId = Thread.currentThread().getId();
        context = TestRunner.getTestExecutionContext(threadId);
    }

    @Override
    public LoginScreen loginToSwagLab(String username, String password) {
        driver.findElement(byUsernameTxtBoxId).sendKeys(username);
        driver.findElement(byPasswordTxtBoxId).sendKeys(password);
        ExtendTestLogger.logInfoMessage("Login for username " + username);
        driver.findElement(byLoginBtnId).click();
        return this;
    }
}
