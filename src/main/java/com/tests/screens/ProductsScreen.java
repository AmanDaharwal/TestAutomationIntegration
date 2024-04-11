package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.ProductsScreenWeb;

public abstract class ProductsScreen {

    private static final String SCREEN_NAME = ProductsScreen.class.getSimpleName();

    public static ProductsScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
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
