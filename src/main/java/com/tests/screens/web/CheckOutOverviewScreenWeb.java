package com.tests.screens.web;

import com.tests.screens.CheckOutOverviewScreen;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutOverviewScreenWeb extends CheckOutOverviewScreen {
    private WebDriver driver;
    private By byListOfCartItemClass = By.className("cart_item");
    private By byFinishBtnXpath = By.xpath("//a[contains(@class,\"cart_button\")]");


    public CheckOutOverviewScreenWeb(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.findElements(byListOfCartItemClass).size();
    }

    @Override
    public FinishScreen clickFinish() {
        driver.findElement(byFinishBtnXpath).click();
        return FinishScreen.get();
    }
}
