package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.CheckOutInformationScreenWeb;

public abstract class CheckOutInformationScreen {
    private static final String SCREEN_NAME = CheckOutInformationScreen.class.getSimpleName();

    public static CheckOutInformationScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        String platform = TestRunner.getPlatform();
        switch (platform) {
            case "web":
                return new CheckOutInformationScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract CheckOutInformationScreen enterFirstName(String firstName);

    public abstract CheckOutInformationScreen enterLastName(String lastName);

    public abstract CheckOutInformationScreen enterPinCode(String pinCode);

    public abstract CheckOutOverviewScreen clickContinue();
}
