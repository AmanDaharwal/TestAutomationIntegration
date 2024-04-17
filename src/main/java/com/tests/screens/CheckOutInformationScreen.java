package com.tests.screens;

import com.entities.Platform;
import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.android.CheckOutInformationScreenAndroid;
import com.tests.screens.ios.CheckOutInformationScreenIos;
import com.tests.screens.web.CheckOutInformationScreenWeb;

public abstract class CheckOutInformationScreen {
    private static final String SCREEN_NAME = CheckOutInformationScreen.class.getSimpleName();

    public static CheckOutInformationScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        Platform platform = TestRunner.getPlatform();
        switch (platform) {
            case web:
                return new CheckOutInformationScreenWeb(driver);
            case android:
                return new CheckOutInformationScreenAndroid(driver);
            case ios:
                return new CheckOutInformationScreenIos(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract CheckOutInformationScreen enterFirstName(String firstName);

    public abstract CheckOutInformationScreen enterLastName(String lastName);

    public abstract CheckOutInformationScreen enterPinCode(String pinCode);

    public abstract String getPageTitle();

    public abstract CheckOutOverviewScreen clickContinue();
}
