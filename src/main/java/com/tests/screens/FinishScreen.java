package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.android.FinishScreenAndroid;
import com.tests.screens.web.FinishScreenWeb;

public abstract class FinishScreen {

    private static final String SCREEN_NAME = FinishScreen.class.getSimpleName();

    public static FinishScreen get() {
        String platform = TestRunner.getPlatform();
        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());

        switch (platform) {
            case "web":
                return new FinishScreenWeb(driver);
            case "android":
                return new FinishScreenAndroid(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract boolean isOrderSuccessful();
}
