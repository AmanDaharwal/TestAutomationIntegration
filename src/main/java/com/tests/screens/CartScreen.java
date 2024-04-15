package com.tests.screens;

import com.entities.Platform;
import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.android.CartScreenAndroid;
import com.tests.screens.web.CartScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class CartScreen {

    private static final String SCREEN_NAME = CartScreen.class.getSimpleName();

    public static CartScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        Platform platform = TestRunner.getPlatform();
        switch (platform) {
            case web:
                return new CartScreenWeb(driver);
            case android:
                return new CartScreenAndroid(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract int getNumberOfCartItems();

    public abstract CheckOutOverviewScreen clickCheckout();
}
