package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.android.CheckOutOverviewScreenAndroid;
import com.tests.screens.web.CheckOutOverviewScreenWeb;

public abstract class CheckOutOverviewScreen {

    private static final String SCREEN_NAME = CheckOutOverviewScreen.class.getSimpleName();

    public static CheckOutOverviewScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        String platform = TestRunner.getPlatform();
        switch (platform) {
            case "web":
                return new CheckOutOverviewScreenWeb(driver);
            case "android":
                return new CheckOutOverviewScreenAndroid(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract int getNumberOfCartItems();

    public abstract FinishScreen clickFinish();
}
