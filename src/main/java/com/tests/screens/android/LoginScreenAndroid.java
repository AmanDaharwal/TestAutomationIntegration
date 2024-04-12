package com.tests.screens.android;

import com.reporters.ExtendTestLogger;
import com.runner.Driver;
import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;

public class LoginScreenAndroid extends LoginScreen {
    private final Driver driver;
    private final By byUsernameTxtBoxXpath = By.xpath("//android.widget.EditText[@content-desc='test-Username']");
    private final By byPasswordTxtBoxXpath = By.xpath("//android.widget.EditText[@content-desc='test-Password']");
    private final By byLoginBtnXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-LOGIN']");

    public LoginScreenAndroid(Driver driver) {
        this.driver = driver;
    }

    @Override
    public LoginScreen loginToSwagLab(String username, String password) {
        driver.enterText(byUsernameTxtBoxXpath, username);
        driver.enterText(byPasswordTxtBoxXpath, password);
        ExtendTestLogger.logInfoMessage("Login for username " + username);
        driver.click(byLoginBtnXpath);
        return this;
    }
}
