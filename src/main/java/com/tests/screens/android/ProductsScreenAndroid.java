package com.tests.screens.android;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.ProductsScreen;
import org.openqa.selenium.By;

public class ProductsScreenAndroid extends ProductsScreen {
    private By byProductTitleXpath = By.xpath("//android.widget.TextView[@text='PRODUCTS']");
    private By byCartIconXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']/android.view.ViewGroup");
    private String inventoryItemsXpath = "(//android.view.ViewGroup[@content-desc='test-ADD TO CART'])[%s]";
    private final Driver driver;

    public ProductsScreenAndroid(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String getPageTitle() {
        return driver.getText(byProductTitleXpath);
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
