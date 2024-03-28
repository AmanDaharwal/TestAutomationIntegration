package com.tests.screens.web;

import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginScreenWeb extends LoginScreen {

    private final By byUsernameTxtBoxId = By.id("user-name");
    private final By byPasswordTxtBoxId = By.id("password");
    private final By byLoginBtnId = By.id("login-button");
    private WebDriver driver;
    public LoginScreenWeb(WebDriver driver){
        this.driver = driver;
    }

    @Override
    public LoginScreen loginToSwagLab(String username, String password) {
        driver.findElement(byUsernameTxtBoxId).sendKeys(username);
        driver.findElement(byPasswordTxtBoxId).sendKeys(password);
        driver.findElement(byLoginBtnId).click();
        return this;
    }
}
