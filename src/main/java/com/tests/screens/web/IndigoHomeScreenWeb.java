package com.tests.screens.web;

import com.tests.screens.IndigoHomeScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndigoHomeScreenWeb extends IndigoHomeScreen {
    private final WebDriver driver;
    public IndigoHomeScreenWeb(WebDriver driver){
        this.driver = driver;
    }
    private final String closePopUpBtnId = "salePopupCloseBtn";
    private final String hamburgerIconClass = "icon-hamburger";
    private final String bookFlightXpath = "//li[contains(@class,'hamburger')]//a[contains(@href,'flight-booking')]";

    @Override
    public IndigoHomeScreen closeIntroPopup(){

        driver.findElement(By.id(closePopUpBtnId)).click();
        return this;
    }

    @Override
    public IndigoHomeScreen selectTrip(String trip) {
        driver.findElement(By.className(hamburgerIconClass)).click();
        driver.findElement(By.xpath(bookFlightXpath)).click();
        return this;
    }

    @Override
    public IndigoHomeScreen enterFromDestination(String from) {

        return this;
    }

    @Override
    public IndigoHomeScreen enterToDestination(String to) {

        return this;
    }

    @Override
    public IndigoHomeScreen searchFlight() {

        return this;
    }

    @Override
    public String getSearchedDestinations() {
        return "";
    }
}
