package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.exceptions.TestExecutionFailedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.ProductsScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class ProductsScreen {

    private static final String SCREEN_NAME = ProductsScreen.class.getSimpleName();

    public static ProductsScreen get() {

        WebDriver driver = Drivers.getInnerDriver(Thread.currentThread().getId());
        String platform = TestRunner.getPlatform();
        switch (platform) {
            case "web":
                return new ProductsScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract String getPageTitle();

    public abstract ProductsScreen addProductToCart(int numberOfItems);

    public abstract CartScreen goToCart();


}
