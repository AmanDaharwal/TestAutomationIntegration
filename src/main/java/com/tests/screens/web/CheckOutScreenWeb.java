package com.tests.screens.web;

import com.tests.screens.CheckOutScreen;
import org.openqa.selenium.WebDriver;

public class CheckOutScreenWeb extends CheckOutScreen {
    private WebDriver driver;

    public CheckOutScreenWeb(WebDriver driver) {
        this.driver = driver;
    }
}
