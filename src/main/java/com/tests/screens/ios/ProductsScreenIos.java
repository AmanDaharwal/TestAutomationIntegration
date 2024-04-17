package com.tests.screens.ios;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.ProductsScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class ProductsScreenIos extends ProductsScreen {
    private By byProductTitleXpath = By.xpath("//XCUIElementTypeStaticText[@name='PRODUCTS']");
    private By byCartIconXpath = By.xpath("//XCUIElementTypeOther[@name='test-Cart']/XCUIElementTypeOther");
    private String inventoryItemsXpath = "(//XCUIElementTypeOther[@name='test-ADD TO CART'])[%s]";
    private final Driver driver;

    public ProductsScreenIos(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byProductTitleXpath).getText();
    }

    @Override
    public ProductsScreen addProductToCart(int numberOfItems) {
        int count = numberOfItems;

        while (count > 0) {
            driver.waitForElementToBeVisible(By.xpath(String.format(inventoryItemsXpath, count))).click();
            count--;
        }
        return this;
    }

    @Override
    public CartScreen goToCart() {
        driver.click(byCartIconXpath);
        return CartScreen.get();
    }
}
