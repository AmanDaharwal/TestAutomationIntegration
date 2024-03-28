package com.tests.screens.web;

import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FinishScreenWeb extends FinishScreen {
    private WebDriver driver;
    private By byCompleteOrderTxtClass = By.className("complete-header");

    public FinishScreenWeb(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean isOrderSuccessful() {
        String orderSuccessfulText = "THANK YOU FOR YOUR ORDER";
        return driver.findElement(byCompleteOrderTxtClass).equals(orderSuccessfulText);
    }
}
