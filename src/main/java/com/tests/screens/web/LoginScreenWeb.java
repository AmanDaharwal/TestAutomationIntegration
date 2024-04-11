package com.tests.screens.web;

import com.context.TestExecutionContext;
import com.reporters.ExtendTestLogger;
import com.runner.Driver;
import com.runner.TestRunner;
import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;

public class LoginScreenWeb extends LoginScreen {
    private final By byUsernameTxtBoxId = By.id("user-name");
    private final By byPasswordTxtBoxId = By.id("password");
    private final By byLoginBtnId = By.id("login-button");
    private final Driver driver;
    private TestExecutionContext context;

    public LoginScreenWeb(Driver driver) {
        this.driver = driver;
        long threadId = Thread.currentThread().getId();
        context = TestRunner.getTestExecutionContext(threadId);
    }

    @Override
    public LoginScreen loginToSwagLab(String username, String password) {
        driver.enterText(byUsernameTxtBoxId, username);
        driver.enterText(byPasswordTxtBoxId, password);
        ExtendTestLogger.logInfoMessage("Login for username " + username);
        driver.click(byLoginBtnId);
        return this;
    }
}
