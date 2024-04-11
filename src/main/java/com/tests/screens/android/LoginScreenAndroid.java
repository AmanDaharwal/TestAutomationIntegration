package com.tests.screens.android;

import com.reporters.ExtendTestLogger;
import com.runner.Driver;
import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;

public class LoginScreenAndroid extends LoginScreen {
    private final Driver driver;
    private final By byUsernameTxtBoxId = By.id("test-Username");
    private final By byPasswordTxtBoxId = By.id("test-Password");
    private final By byLoginBtnId = By.id("test-LOGIN");

    public LoginScreenAndroid(Driver driver) {
        this.driver = driver;
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
