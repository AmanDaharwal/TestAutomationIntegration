package com.tests.screens.web;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.ProductsScreen;
import org.openqa.selenium.By;

public class ProductsScreenWeb extends ProductsScreen {

    private By byProductTitleClass = By.className("product_label");
    private By byCartIconId = By.id("shopping_cart_container");
    private String inventoryItemsXpath = "(//div[@class=\"inventory_item\"]//button)[%s]";
    private final Driver driver;

    public ProductsScreenWeb(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byProductTitleClass).getText();
    }

    @Override
    public ProductsScreen addProductToCart(int numberOfItems) {
        int count = 1;

        while (count <= numberOfItems) {
            driver.click(By.xpath(String.format(inventoryItemsXpath, count)));
            count++;
        }
        return this;
    }

    @Override
    public CartScreen goToCart() {
        driver.click(byCartIconId);
        return CartScreen.get();
    }
}
