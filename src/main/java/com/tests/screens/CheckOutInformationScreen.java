package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.CheckOutInformationScreenWeb;
import com.tests.screens.web.CheckOutOverviewScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class CheckOutInformationScreen {
    private static final String SCREEN_NAME = CheckOutInformationScreen.class.getSimpleName();

    public static CheckOutInformationScreen get() {

        WebDriver driver = Drivers.getInnerDriver();
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
