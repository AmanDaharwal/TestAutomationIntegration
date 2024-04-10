package com.tests.screens.web;

import com.tests.screens.CartScreen;
import com.tests.screens.ProductsScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsScreenWeb extends ProductsScreen {

    private By byProductTitleClass = By.className("product_label");
    private By byCartIconId = By.id("shopping_cart_container");
    private String inventoryItemsXpath = "(//div[@class=\"inventory_item\"]//button)[%s]";

    private WebDriver driver;

    public ProductsScreenWeb(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getPageTitle() {
        return driver.findElement(byProductTitleClass).getText();
    }

    @Override
    public ProductsScreen addProductToCart(int numberOfItems) {
        int count = 1;

        while( count <= numberOfItems) {
            driver.findElement(By.xpath(String.format(inventoryItemsXpath, count))).click();
            count++;
        }
        return this;
    }

    @Override
    public CartScreen goToCart() {
        driver.findElement(byCartIconId).click();
        return CartScreen.get();
    }
}
